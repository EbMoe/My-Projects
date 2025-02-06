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
    public class CarsController : Controller
    {
        private readonly ApplicationDBContext _context;

        public CarsController(ApplicationDBContext context)
        {
            _context = context;
        }

        // GET: Cars
        public async Task<IActionResult> Index()
        {
            var applicationDBContext = _context.Cars.Include(c => c.CarMakes);
            return View(await applicationDBContext.ToListAsync());
        }

        // GET: Cars/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null || _context.Cars == null)
            {
                return NotFound();
            }

            var car = await _context.Cars
                .Include(c => c.CarMakes)
                .FirstOrDefaultAsync(m => m.CarNo == id);
            if (car == null)
            {
                return NotFound();
            }

            return View(car);
        }

        // GET: Cars/Create
        public IActionResult Create()
        {
            ViewData["CarMake"] = new SelectList(_context.CarMakes, "CrMake", "CrMake");
            return View();
        }

        // POST: Cars/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("CarNo,CarModel,KMTravelled,ServiceKM,Available,CarMake,TypeID")] Car car)
        {
            if (ModelState.IsValid)
            {
                _context.Add(car);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["CarMake"] = new SelectList(_context.CarMakes, "CrMake", "CrMake", car.CarMake);
            return View(car);
        }

        // GET: Cars/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null || _context.Cars == null)
            {
                return NotFound();
            }

            var car = await _context.Cars.FindAsync(id);
            if (car == null)
            {
                return NotFound();
            }
            ViewData["CarMake"] = new SelectList(_context.CarMakes, "CrMake", "CrMake", car.CarMake);
            return View(car);
        }

        // POST: Cars/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("CarNo,CarModel,KMTravelled,ServiceKM,Available,CarMake,TypeID")] Car car)
        {
            if (id != car.CarNo)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(car);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!CarExists(car.CarNo))
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
            ViewData["CarMake"] = new SelectList(_context.CarMakes, "CrMake", "CrMake", car.CarMake);
            return View(car);
        }

        // GET: Cars/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null || _context.Cars == null)
            {
                return NotFound();
            }

            var car = await _context.Cars
                .Include(c => c.CarMakes)
                .FirstOrDefaultAsync(m => m.CarNo == id);
            if (car == null)
            {
                return NotFound();
            }

            return View(car);
        }

        // POST: Cars/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            if (_context.Cars == null)
            {
                return Problem("Entity set 'ApplicationDBContext.Cars'  is null.");
            }
            var car = await _context.Cars.FindAsync(id);
            if (car != null)
            {
                _context.Cars.Remove(car);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool CarExists(string id)
        {
          return (_context.Cars?.Any(e => e.CarNo == id)).GetValueOrDefault();
        }
    }
}
