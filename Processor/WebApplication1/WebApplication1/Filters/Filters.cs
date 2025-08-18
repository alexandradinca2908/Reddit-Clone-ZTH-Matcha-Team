using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WebImageProcessor.Commons;
using WebImageProcessor.Filters;
using WebImageProcessor.Models;
using static System.Net.Mime.MediaTypeNames;

namespace WebImageProcessor.Filters
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
                double intGray = originalImage.PixelData[i + 2] * FilterConstants.GrayscaleFilterConstants.RedWeight +
                              originalImage.PixelData[i + 1] * FilterConstants.GrayscaleFilterConstants.GreenWeight +
                              originalImage.PixelData[i] * FilterConstants.GrayscaleFilterConstants.BlueWeight;
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

    public class SepiaFilter : IFilter
    {
        RawImage IFilter.Apply(RawImage originalImage)
        {
            for (int i = 0; i < originalImage.PixelData.Length; i += originalImage.BytesPerPixel)
            {
                byte r = originalImage.PixelData[i];
                byte g = originalImage.PixelData[i + 1];
                byte b = originalImage.PixelData[i + 2];

                double newRed = (r * FilterConstants.SepiaFilterConstants.RedToRed) +
                                (g * FilterConstants.SepiaFilterConstants.GreenToRed) +
                                (b * FilterConstants.SepiaFilterConstants.BlueToRed);

                double newGreen = (r * FilterConstants.SepiaFilterConstants.RedToGreen) +
                                  (g * FilterConstants.SepiaFilterConstants.GreenToGreen) +
                                  (b * FilterConstants.SepiaFilterConstants.BlueToGreen);

                double newBlue = (r * FilterConstants.SepiaFilterConstants.RedToBlue) +
                                 (g * FilterConstants.SepiaFilterConstants.GreenToBlue) +
                                 (b * FilterConstants.SepiaFilterConstants.BlueToBlue);

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

    public class FlipFilter : IFilter
    {
        public RawImage Apply(RawImage originalImage)
        {
            byte[] pixelData = (byte[]) originalImage.PixelData.Clone();
            int width = originalImage.Width;
            int height = originalImage.Height;
            int bpp = originalImage.BytesPerPixel;
            int rowStride = width * bpp;

            for (int y = 0; y < height; y++)
            {
                int rowStart = y * rowStride;
                for (int x = 0; x < width / 2; x++)
                {
                    int leftIndex = rowStart + x * bpp;
                    int rightIndex = rowStart + (width - 1 - x) * bpp;

                    //  Swap each pixel
                    for (int c = 0; c < bpp; c++)
                    {
                        byte tmp = pixelData[leftIndex + c];
                        pixelData[leftIndex + c] = pixelData[rightIndex + c];
                        pixelData[rightIndex + c] = tmp;
                    }
                }
            }

            return new RawImage(pixelData, width, height, bpp);
        }
    }
}

