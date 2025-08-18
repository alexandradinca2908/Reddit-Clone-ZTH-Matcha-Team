using SixLabors.ImageSharp;
using SixLabors.ImageSharp.Formats.Png;
using SixLabors.ImageSharp.PixelFormats;
using System;
using WebImageProcessor.Commons;
using WebImageProcessor.Filters;

namespace WebImageProcessor.Services
{
    public class ApplyFilterService
    {
        public async Task<byte[]> GetBytesFromImageFileAsync(IFormFile imageFile)
        {
            if (imageFile == null || imageFile.Length == 0)
            {
                throw new ArgumentException("No file was uploaded or the file is empty.");
            }

            using (var memoryStream = new MemoryStream())
            {
                await imageFile.CopyToAsync(memoryStream);
                return memoryStream.ToArray();
            }
        }
        public byte[] ApplyFilter(byte[] imageBytes, FiltersEnum.FilterTypes filterType)
        {
            using (var originalImage = Image.Load<Rgba32>(imageBytes))
            {
                IFilter filter;
                switch (filterType)
                {
                    case FiltersEnum.FilterTypes.Nothing:
                        filter = new Filters.DoNothingFilter();
                        break;
                    case FiltersEnum.FilterTypes.Grayscale:
                        filter = new Filters.GrayScaleFilter();
                        break;
                    case FiltersEnum.FilterTypes.Sepia:
                        filter = new Filters.SepiaFilter();
                        break;
                    case FiltersEnum.FilterTypes.Invert:
                        filter = new Filters.InvertFilter();
                        break;
                    case FiltersEnum.FilterTypes.Flip:
                        filter = new Filters.FlipFilter();
                        break;
                    default:
                        throw new NotSupportedException($"Filter type '{filterType}' is not supported.");
                }

                var pixelData = new byte[originalImage.Width * originalImage.Height * 4];
                originalImage.CopyPixelDataTo(pixelData);
                var rawImage = new Models.RawImage(pixelData, originalImage.Width, originalImage.Height, 4);
                var filteredRawImage = filter.Apply(rawImage);

                using (var finalImage = Image.LoadPixelData<Rgba32>(
                    filteredRawImage.PixelData,
                    originalImage.Width,
                    originalImage.Height))
                {
                    using (var memoryStream = new MemoryStream())
                    {
                        finalImage.Save(memoryStream, new PngEncoder());
                        return memoryStream.ToArray();
                    }
                }
            }
        }
    }
}
