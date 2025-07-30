using Filters;
using Common;

namespace App;
public class App {
    public void Start()
    {   
        string imagePath = "C:\\Users\\DARIUS\\Desktop\\photos\\photo.jpeg";
        Console.WriteLine("Welcome to the Image Processor Application!");
        Console.WriteLine("Please choose a filter option:\n");
        Console.WriteLine($"""
            1. {FiltersEnum.FilterTypes.Grayscale}
            2. {FiltersEnum.FilterTypes.Sepia}
            3. {FiltersEnum.FilterTypes.Invert}
            """);
        string input = Console.ReadLine();
        if (!int.TryParse(input, out int filterType))
        {
            Console.WriteLine($"Invalid filter argument: {input}. Please provide valid integers.");
            return;
        }
        if (filterType < 1 || filterType > 3)
        {
            Console.WriteLine($"Invalid filter type: {filterType}. Please choose a number between 1 and 3.");
            return;
        }
        // DO STUFF
    }
}
