using Filters;
using Common;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using SixLabors.ImageSharp.Processing;

namespace App;
public class App {
    public void Start()
    {   
        string imagePath = "C:\\Users\\alexa\\OneDrive\\Desktop\\matcha_cups.webp";  //for easy testing

        if (!File.Exists(imagePath))
        {
            Console.WriteLine(Common.ConsoleMessages.ErrorFileNotFound);
            return;
        }

        string outputPath = Path.Combine(Path.GetDirectoryName(imagePath), "photo_out.jpeg");  //for easy testing

        Console.WriteLine($"""
            Welcome to the Image Processor Application!
            Please choose a filter option:

            1. {FiltersEnum.FilterTypes.Grayscale}
            2. {FiltersEnum.FilterTypes.Sepia}
            3. {FiltersEnum.FilterTypes.Invert}
            4. {FiltersEnum.FilterTypes.Flip}
            5. {FiltersEnum.FilterTypes.Nothing}
            """);

        string input = Console.ReadLine();
        while (!int.TryParse(input, out int filterType) && filterType < 1 || filterType > 4)
        {
            Console.WriteLine("Invalid input. Please enter a NUMBER between 1 and 4.");
            input = Console.ReadLine();
        }

        Image<Rgba32> loadedImage = Image.Load<Rgba32>(imagePath);
        byte[] pixelData = new byte[loadedImage.Width * loadedImage.Height * 4];
        loadedImage.CopyPixelDataTo(pixelData);
        RawImage originalRawImage = new RawImage(pixelData, loadedImage.Width, loadedImage.Height, 4);

        RawImage filteredRawImage;
        IFilter filter = new Filters.GrayScaleFilter();

        switch (input)
        {
            case "1":
                filter = new Filters.GrayScaleFilter();
                break;
            case "2":
                filter = new Filters.SepiaFilter();
                break;
            case "3":
                filter = new Filters.InvertFilter();
                break;
            case "4":
                filter = new Filters.FlipFilter();
                break;
            case "5":
                break;
            default:
                Console.WriteLine("Invalid option selected. No filter will be applied.");
                break;
        }

        RawImage copyRawImage = originalRawImage;
        filteredRawImage = filter.Apply(copyRawImage);
        Image<Rgba32> outputImage = Image.LoadPixelData<Rgba32>(filteredRawImage.PixelData, filteredRawImage.Width, filteredRawImage.Height);

        outputImage.Save(outputPath);

    }
}
