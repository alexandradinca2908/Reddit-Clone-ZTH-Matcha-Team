using System.Text.Json;

namespace WebImageProcessor.Services;

public class TestApiService
{
    private readonly IHttpClientFactory _httpClientFactory;

    public TestApiService(IHttpClientFactory httpClientFactory)
    {
        _httpClientFactory = httpClientFactory;
    }

    public async Task<string> GetJsonPlaceholderData()
    {
        // Creează un client HTTP folosind factory-ul
        var httpClient = _httpClientFactory.CreateClient();

        // Definește URL-ul API-ului extern
        var apiUrl = "https://jsonplaceholder.typicode.com/todos/1";

        // Face cererea GET asincron
        var response = await httpClient.GetAsync(apiUrl);

        // Aruncă o excepție dacă răspunsul nu este unul de succes (ex: 404, 500)
        response.EnsureSuccessStatusCode();

        // Citește conținutul răspunsului ca string
        var responseBody = await response.Content.ReadAsStringAsync();

        // Poți deserializa JSON-ul într-un obiect C# dacă ai o clasă definită
        // var todoItem = JsonSerializer.Deserialize<Todo>(responseBody);

        return responseBody; // Returnează string-ul JSON brut
    }
}