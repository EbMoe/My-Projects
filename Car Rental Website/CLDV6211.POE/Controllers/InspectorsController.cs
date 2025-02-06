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
    public class InspectorsController : Controller
    {
        private readonly ApplicationDBContext _context;

        public InspectorsController(ApplicationDBContext context)
        {
            _context = context;
        }

        // GET: Inspectors
        public async Task<IActionResult> Index()
        {
              return _context.Inspectors != null ? 
                          View(await _context.Inspectors.ToListAsync()) :
                          Problem("Entity set 'ApplicationDBContext.Inspectors'  is null.");
        }

        // GET: Inspectors/Details/5
        public async Task<IActionResult> Details(string id)
        {
            if (id == null || _context.Inspectors == null)
            {
                return NotFound();
            }

            var inspector = await _context.Inspectors
                .FirstOrDefaultAsync(m => m.InspectorNo == id);
            if (inspector == null)
            {
                return NotFound();
            }

            return View(inspector);
        }

        // GET: Inspectors/Create
        public IActionResult Create()
        {
            return View();
        }

        // POST: Inspectors/Create
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create([Bind("InspectorNo,InspectorName,IEmail,IMobile")] Inspector inspector)
        {
            if (ModelState.IsValid)
            {
                _context.Add(inspector);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            return View(inspector);
        }

        // GET: Inspectors/Edit/5
        public async Task<IActionResult> Edit(string id)
        {
            if (id == null || _context.Inspectors == null)
            {
                return NotFound();
            }

            var inspector = await _context.Inspectors.FindAsync(id);
            if (inspector == null)
            {
                return NotFound();
            }
            return View(inspector);
        }

        // POST: Inspectors/Edit/5
        // To protect from overposting attacks, enable the specific properties you want to bind to.
        // For more details, see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(string id, [Bind("InspectorNo,InspectorName,IEmail,IMobile")] Inspector inspector)
        {
            if (id != inspector.InspectorNo)
            {
                return NotFound();
            }

            if (ModelState.IsValid)
            {
                try
                {
                    _context.Update(inspector);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateConcurrencyException)
                {
                    if (!InspectorExists(inspector.InspectorNo))
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
            return View(inspector);
        }

        // GET: Inspectors/Delete/5
        public async Task<IActionResult> Delete(string id)
        {
            if (id == null || _context.Inspectors == null)
            {
                return NotFound();
            }

            var inspector = await _context.Inspectors
                .FirstOrDefaultAsync(m => m.InspectorNo == id);
            if (inspector == null)
            {
                return NotFound();
            }

            return View(inspector);
        }

        // POST: Inspectors/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> DeleteConfirmed(string id)
        {
            if (_context.Inspectors == null)
            {
                return Problem("Entity set 'ApplicationDBContext.Inspectors'  is null.");
            }
            var inspector = await _context.Inspectors.FindAsync(id);
            if (inspector != null)
            {
                _context.Inspectors.Remove(inspector);
            }
            
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Index));
        }

        private bool InspectorExists(string id)
        {
          return (_context.Inspectors?.Any(e => e.InspectorNo == id)).GetValueOrDefault();
        }
    }
}
