using Microsoft.AspNetCore.Mvc;
using System.IO;
using System.Threading.Tasks;
using WebImageProcessor.Commons;
using WebImageProcessor.Filters;
using WebImageProcessor.Services;

namespace WebImageProcessor.Controllers
{
    [ApiController]
    [Route("/filter")]
    public class ApplyFilterController : ControllerBase
    {
        private readonly ApplyFilterService _applyFilterService;

        public ApplyFilterController(ApplyFilterService applyFilterService)
        {
            _applyFilterService = applyFilterService;
        }

        [HttpPost]
        public async Task<IActionResult> ApplyFilter(
            [FromForm] IFormFile imageFile,
            [FromForm] FiltersEnum filter)
        {

            try
            {
                var imageBytes = await _applyFilterService.GetBytesFromImageFileAsync(imageFile);
                var resultBytes = _applyFilterService.ApplyFilter(imageBytes, filter);
                return File(resultBytes, "image/png");
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error while trying to apply filter: {ex.Message}");
            }
        }
    }
}
