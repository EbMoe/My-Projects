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
    public class RentalsController : Controller
    {
      
        private readonly ApplicationDBContext _context;

        public RentalsController(ApplicationDBContext context)
        {
            _context = context;
        }
      

        // GET: Rentals
        public async Task<IActionResult> Index()
        {
            var applicationDBContext = _context.Rentals.Include(r => r.Cars).Include(r => r.Drivers).Include(r => r.Inspectors);
            return View(await applicationDBContext.ToListAsync());
        }

        // GET: Rentals/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Rentals == null)
            {
                return NotFound();
            }

            var rental = await _context.Rentals
                .Include(r => r.Cars)
                .Include(r => r.Drivers)
                .Include(r => r.Inspectors)
                .FirstOrDefaultAsync(m => m.RentalCode == id);
            if (rental == null)
            {
                return NotFound();
            }

            return View(rental);
        }

        // GET: Rentals/Create
        public IActionResult Create()
        {
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo");
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo");
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo");
            return View();
        }

        // POST: Rentals/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("RentalCode,CarNo,InspectorNo,DriverNo,RentalFee,Start_Date,End_Date")] Rental rental)
        {
            if (ModelState.IsValid)
            {
                _context.Add(rental);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", rental.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", rental.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", rental.InspectorNo);
            return View(rental);
        }

        // GET: Rentals/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Rentals == null)
            {
                return NotFound();
            }

            var rental = await _context.Rentals.FindAsync(id);
            if (rental == null)
            {
                return NotFound();
            }
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", rental.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", rental.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", rental.InspectorNo);
            return View(rental);
        }

        // POST: Rentals/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("RentalCode,CarNo,InspectorNo,DriverNo,RentalFee,Start_Date,End_Date")] Rental rental)
        {
            if (id != rental.RentalCode)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(rental);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!RentalExists(rental.RentalCode))
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
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", rental.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", rental.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", rental.InspectorNo);
            return View(rental);
        }

        // GET: Rentals/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Rentals == null)
            {
                return NotFound();
            }

            var rental = await _context.Rentals
                .Include(r => r.Cars)
                .Include(r => r.Drivers)
                .Include(r => r.Inspectors)
                .FirstOrDefaultAsync(m => m.RentalCode == id);
            if (rental == null)
            {
                return NotFound();
            }

            return View(rental);
        }

        // POST: Rentals/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Rentals == null)
            {
                return Problem("Entity set 'ApplicationDBContext.Rentals'  is null.");
            }
            var rental = await _context.Rentals.FindAsync(id);
            if (rental != null)
            {
                _context.Rentals.Remove(rental);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool RentalExists(int id)
        {
          return (_context.Rentals?.Any(e => e.RentalCode == id)).GetValueOrDefault();
        }
    }
}
