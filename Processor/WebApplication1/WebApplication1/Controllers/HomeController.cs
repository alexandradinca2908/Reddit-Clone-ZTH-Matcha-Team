using WebImageProcessor.Services;
using Microsoft.AspNetCore.Mvc;

namespace WebImageProcessor.Controllers;

[ApiController]
[Route("/home")]
public class HomeController : ControllerBase
{
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
            return StatusCode(500, e.Message);
        }
    }
}