using WebImageProcessor.Services;
using Microsoft.AspNetCore.Mvc;

namespace WebImageProcessor.Controllers
{
    [ApiController]
    [Route("/filters")]
    public class FilterListController : ControllerBase
    {
        private readonly FilterListService _apiService;

        public FilterListController(FilterListService apiService)
        {
            _apiService = apiService;
        }

        [HttpGet]
        public IActionResult GetFilters()
        {
            try
            {
                var filters = _apiService.GetAllFilters();
                return Ok(new { success = true, data = filters });
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Eroare la obținerea filtrelor: {ex.Message}");
            }
        }
    }
}
