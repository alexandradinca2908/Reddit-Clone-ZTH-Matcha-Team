using WebImageProcessor.Services;
using Microsoft.AspNetCore.Mvc;

namespace WebImageProcessor.Controllers;

[ApiController]
[Route("/test")]
public class TestController : ControllerBase
{
    private readonly TestApiService _apiService;

    public TestController(TestApiService apiService)
    {
        _apiService = apiService;
    }

    [HttpGet]
    public async Task<IActionResult> GetHomePage()
    {
        try
        {
            // Apelează metoda din serviciu pentru a obține datele
            var externalData = await _apiService.GetJsonPlaceholderData();

            // Returnează datele ca răspuns JSON
            return Ok(externalData);
        }
        catch (HttpRequestException e)
        {
            // Gestionează erorile de rețea
            return StatusCode(500, $"Eroare la apelarea API-ului extern: {e.Message}");
        }
    }
}