namespace WebImageProcessor.Commons
{
    public class RawImage
    {
        public byte[] PixelData { get; }
        public int Width { get; }
        public int Height { get; }
        public int BytesPerPixel { get; }
        public RawImage(byte[] pixelData, int width, int height, int bytesPerPixel)
        {
            if (pixelData == null || pixelData.Length != width * height * bytesPerPixel)
            {
                throw new ArgumentException("Pixel data length does not match the image dimensions and bytes per pixel.");
            }

            PixelData = pixelData;
            Width = width;
            Height = height;
            BytesPerPixel = bytesPerPixel;
        }
    }
}
