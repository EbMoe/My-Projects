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
    public class CarBodyTypesController : Controller
    {
        private readonly ApplicationDBContext _context;

        public CarBodyTypesController(ApplicationDBContext context)
        {
            _context = context;
        }

        // GET: CarBodyTypes
        public async Task<IActionResult> Index()
        {
              return _context.CarBodyTypes != null ? 
                          View(await _context.CarBodyTypes.ToListAsync()) :
                          Problem("Entity set 'ApplicationDBContext.CarBodyTypes'  is null.");
        }

        // GET: CarBodyTypes/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null || _context.CarBodyTypes == null)
            {
                return NotFound();
            }

            var carBodyType = await _context.CarBodyTypes
                .FirstOrDefaultAsync(m => m.TypeID == id);
            if (carBodyType == null)
            {
                return NotFound();
            }

            return View(carBodyType);
        }

        // GET: CarBodyTypes/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: CarBodyTypes/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("TypeID,TypeDescription")] CarBodyType carBodyType)
        {
            if (ModelState.IsValid)
            {
                _context.Add(carBodyType);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(carBodyType);
        }

        // GET: CarBodyTypes/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null || _context.CarBodyTypes == null)
            {
                return NotFound();
            }

            var carBodyType = await _context.CarBodyTypes.FindAsync(id);
            if (carBodyType == null)
            {
                return NotFound();
            }
            return View(carBodyType);
        }

        // POST: CarBodyTypes/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("TypeID,TypeDescription")] CarBodyType carBodyType)
        {
            if (id != carBodyType.TypeID)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(carBodyType);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CarBodyTypeExists(carBodyType.TypeID))
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
            return View(carBodyType);
        }

        // GET: CarBodyTypes/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null || _context.CarBodyTypes == null)
            {
                return NotFound();
            }

            var carBodyType = await _context.CarBodyTypes
                .FirstOrDefaultAsync(m => m.TypeID == id);
            if (carBodyType == null)
            {
                return NotFound();
            }

            return View(carBodyType);
        }

        // POST: CarBodyTypes/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            if (_context.CarBodyTypes == null)
            {
                return Problem("Entity set 'ApplicationDBContext.CarBodyTypes'  is null.");
            }
            var carBodyType = await _context.CarBodyTypes.FindAsync(id);
            if (carBodyType != null)
            {
                _context.CarBodyTypes.Remove(carBodyType);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CarBodyTypeExists(string id)
        {
          return (_context.CarBodyTypes?.Any(e => e.TypeID == id)).GetValueOrDefault();
        }
    }
}
