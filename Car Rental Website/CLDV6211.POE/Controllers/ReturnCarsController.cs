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
    public class ReturnCarsController : Controller
    {
        private readonly ApplicationDBContext _context;

        public ReturnCarsController(ApplicationDBContext context)
        {
            _context = context;
        }

        // GET: ReturnCars
        public async Task<IActionResult> Index()
        {
            var applicationDBContext = _context.Returns.Include(r => r.Cars).Include(r => r.Drivers).Include(r => r.Inspectors);
            return View(await applicationDBContext.ToListAsync());
        }

        // GET: ReturnCars/Details/5
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null || _context.Returns == null)
            {
                return NotFound();
            }

            var returnCar = await _context.Returns
                .Include(r => r.Cars)
                .Include(r => r.Drivers)
                .Include(r => r.Inspectors)
                .FirstOrDefaultAsync(m => m.ReturnCode == id);
            if (returnCar == null)
            {
                return NotFound();
            }

            return View(returnCar);
        }

        // GET: ReturnCars/Create
        public IActionResult Create()
        {
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo");
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo");
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo");
            return View();
        }

        // POST: ReturnCars/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("ReturnCode,CarNo,InspectorNo,DriverNo,ReturnDate,ElapsedDate,Fine")] ReturnCar returnCar)
        {
            if (ModelState.IsValid)
            {
                _context.Add(returnCar);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", returnCar.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", returnCar.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", returnCar.InspectorNo);
            return View(returnCar);
        }

        // GET: ReturnCars/Edit/5
        public async Task<IActionResult> Edit(int? id)
        {
            if (id == null || _context.Returns == null)
            {
                return NotFound();
            }

            var returnCar = await _context.Returns.FindAsync(id);
            if (returnCar == null)
            {
                return NotFound();
            }
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", returnCar.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", returnCar.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", returnCar.InspectorNo);
            return View(returnCar);
        }

        // POST: ReturnCars/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, [Bind("ReturnCode,CarNo,InspectorNo,DriverNo,ReturnDate,ElapsedDate,Fine")] ReturnCar returnCar)
        {
            if (id != returnCar.ReturnCode)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(returnCar);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!ReturnCarExists(returnCar.ReturnCode))
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
            ViewData["CarNo"] = new SelectList(_context.Cars, "CarNo", "CarNo", returnCar.CarNo);
            ViewData["DriverNo"] = new SelectList(_context.Drivers, "DriverNo", "DriverNo", returnCar.DriverNo);
            ViewData["InspectorNo"] = new SelectList(_context.Inspectors, "InspectorNo", "InspectorNo", returnCar.InspectorNo);
            return View(returnCar);
        }

        // GET: ReturnCars/Delete/5
        public async Task<IActionResult> Delete(int? id)
        {
            if (id == null || _context.Returns == null)
            {
                return NotFound();
            }

            var returnCar = await _context.Returns
                .Include(r => r.Cars)
                .Include(r => r.Drivers)
                .Include(r => r.Inspectors)
                .FirstOrDefaultAsync(m => m.ReturnCode == id);
            if (returnCar == null)
            {
                return NotFound();
            }

            return View(returnCar);
        }

        // POST: ReturnCars/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(int id)
        {
            if (_context.Returns == null)
            {
                return Problem("Entity set 'ApplicationDBContext.Returns'  is null.");
            }
            var returnCar = await _context.Returns.FindAsync(id);
            if (returnCar != null)
            {
                _context.Returns.Remove(returnCar);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool ReturnCarExists(int id)
        {
          return (_context.Returns?.Any(e => e.ReturnCode == id)).GetValueOrDefault();
        }
    }
}
