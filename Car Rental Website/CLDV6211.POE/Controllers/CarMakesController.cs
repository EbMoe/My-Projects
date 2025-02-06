using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using CLDV6211.POE.Areas.Identity.Data;
using CLDV6211.POE.Models;
using Microsoft.AspNetCore.Authorization;

namespace CLDV6211.POE.Controllers
{
    [Authorize]
    public class CarMakesController : Controller
    {
        private readonly ApplicationDBContext _context;

        public CarMakesController(ApplicationDBContext context)
        {
            _context = context;
        }

        // GET: CarMakes
        public async Task<IActionResult> Index()
        {
              return _context.CarMakes != null ? 
                          View(await _context.CarMakes.ToListAsync()) :
                          Problem("Entity set 'ApplicationDBContext.CarMakes'  is null.");
        }

        // GET: CarMakes/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null || _context.CarMakes == null)
            {
                return NotFound();
            }

            var carMake = await _context.CarMakes
                .FirstOrDefaultAsync(m => m.CrMake == id);
            if (carMake == null)
            {
                return NotFound();
            }

            return View(carMake);
        }

        // GET: CarMakes/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: CarMakes/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("CrMake,MakeDescription")] CarMake carMake)
        {
            if (ModelState.IsValid)
            {
                _context.Add(carMake);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(carMake);
        }

        // GET: CarMakes/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null || _context.CarMakes == null)
            {
                return NotFound();
            }

            var carMake = await _context.CarMakes.FindAsync(id);
            if (carMake == null)
            {
                return NotFound();
            }
            return View(carMake);
        }

        // POST: CarMakes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("CrMake,MakeDescription")] CarMake carMake)
        {
            if (id != carMake.CrMake)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(carMake);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CarMakeExists(carMake.CrMake))
                    {
                        return NotFound();
                    }
                    else
                    {
                        throw;
                    }
                }
                return RedirectToAction(nameof(Index));
            }
            return View(carMake);
        }

        // GET: CarMakes/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null || _context.CarMakes == null)
            {
                return NotFound();
            }

            var carMake = await _context.CarMakes
                .FirstOrDefaultAsync(m => m.CrMake == id);
            if (carMake == null)
            {
                return NotFound();
            }

            return View(carMake);
        }

        // POST: CarMakes/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            if (_context.CarMakes == null)
            {
                return Problem("Entity set 'ApplicationDBContext.CarMakes'  is null.");
            }
            var carMake = await _context.CarMakes.FindAsync(id);
            if (carMake != null)
            {
                _context.CarMakes.Remove(carMake);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CarMakeExists(string id)
        {
          return (_context.CarMakes?.Any(e => e.CrMake == id)).GetValueOrDefault();
        }
    }
}
