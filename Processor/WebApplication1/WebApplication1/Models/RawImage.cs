namespace WebImageProcessor.Models
{
    public class RawImage
    {
        public byte[] PixelData { get; }
        public int Width { get; }
        public int Height { get; }
        public int BytesPerPixel { get; }
        public RawImage(byte[] pixelData, int width, int height, int bytesPerPixel)
        {
            PixelData = pixelData;
            Width = width;
            Height = height;
            BytesPerPixel = bytesPerPixel;
        }
    }
}
