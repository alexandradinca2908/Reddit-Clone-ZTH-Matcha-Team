using Common;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Net.Mime.MediaTypeNames;

namespace Filters
{
    public class DoNothingFilter : IFilter
    {
        RawImage IFilter.Apply(RawImage originalImage)
        {
            return originalImage;
        }
    }

    public class GrayScaleFilter : IFilter
    {
        RawImage IFilter.Apply(RawImage originalImage)
        {
            for (int i = 0; i < originalImage.PixelData.Length; i += originalImage.BytesPerPixel)
            {
                double intGray = originalImage.PixelData[i + 2] * Common.GrayscaleFilterConstants.RedWeight +
                              originalImage.PixelData[i + 1] * Common.GrayscaleFilterConstants.GreenWeight +
                              originalImage.PixelData[i] * Common.GrayscaleFilterConstants.BlueWeight;
                byte gray = (byte)(intGray);
                
                originalImage.PixelData[i] = gray;
                originalImage.PixelData[i + 1] = gray;
                originalImage.PixelData[i + 2] = gray;

                if (originalImage.BytesPerPixel == 4)
                {
                    originalImage.PixelData[i + 3] = originalImage.PixelData[i + 3];
                }
            }

            return new RawImage(originalImage.PixelData, originalImage.Width, originalImage.Height, originalImage.BytesPerPixel);
        }
    }

    public class  SepiaFilter : IFilter
    {
        RawImage IFilter.Apply(RawImage originalImage)
        {
            for (int i = 0; i < originalImage.PixelData.Length; i += originalImage.BytesPerPixel)
            {
                byte r = originalImage.PixelData[i];
                byte g = originalImage.PixelData[i + 1];
                byte b = originalImage.PixelData[i + 2];

                double newRed = (r * SepiaFilterConstants.RedToRed) +
                                (g * SepiaFilterConstants.GreenToRed) +
                                (b * SepiaFilterConstants.BlueToRed);

                double newGreen = (r * SepiaFilterConstants.RedToGreen) +
                                  (g * SepiaFilterConstants.GreenToGreen) +
                                  (b * SepiaFilterConstants.BlueToGreen);

                double newBlue = (r * SepiaFilterConstants.RedToBlue) +
                                 (g * SepiaFilterConstants.GreenToBlue) +
                                 (b * SepiaFilterConstants.BlueToBlue);

                originalImage.PixelData[i] = (byte)Math.Min(255, newRed);
                originalImage.PixelData[i + 1] = (byte)Math.Min(255, newGreen);
                originalImage.PixelData[i + 2] = (byte)Math.Min(255, newBlue);

                if (originalImage.BytesPerPixel == 4)
                {
                    originalImage.PixelData[i + 3] = originalImage.PixelData[i + 3];
                }
            }

            return new RawImage(originalImage.PixelData, originalImage.Width, originalImage.Height, originalImage.BytesPerPixel);
        }
    }

    public class InvertFilter : IFilter
    {
        RawImage IFilter.Apply(RawImage originalImage)
        {
            for (int i = 0; i < originalImage.PixelData.Length; i += originalImage.BytesPerPixel)
            {
                originalImage.PixelData[i] = (byte)(255 - originalImage.PixelData[i]); 
                originalImage.PixelData[i + 1] = (byte)(255 - originalImage.PixelData[i + 1]);
                originalImage.PixelData[i + 2] = (byte)(255 - originalImage.PixelData[i + 2]);

                if (originalImage.BytesPerPixel == 4)
                {
                    originalImage.PixelData[i + 3] = originalImage.PixelData[i + 3];
                }
            }
            return new RawImage(originalImage.PixelData, originalImage.Width, originalImage.Height, originalImage.BytesPerPixel);
        }
    }
}
