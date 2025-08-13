using WebImageProcessor.Services;
using Microsoft.AspNetCore.Mvc;

namespace WebImageProcessor.Controllers;

[ApiController]
[Route("/home")]
public class HomeController : ControllerBase
{
    private readonly TestApiService _apiService;

    public HomeController(TestApiService apiService)
    {
        _apiService = apiService;
    }

    [HttpGet]
    public IActionResult GetHomePage()
    {
        try
        {
            var externalData = "Hello from Matcha!";
            return Ok(externalData);
        }
        catch (HttpRequestException e)
        {
            return StatusCode(500, $"Eroare la apelarea GET /home: {e.Message}");
        }
    }
}