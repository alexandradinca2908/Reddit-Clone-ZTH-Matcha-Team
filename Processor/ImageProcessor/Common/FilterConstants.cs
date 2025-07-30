using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Common
{
    public class GrayscaleFilterConstants
    {
        public const double RedWeight = 0.299;
        public const double GreenWeight = 0.587;
        public const double BlueWeight = 0.114;
    }

    public static class SepiaFilterConstants
    {
        public const double RedToRed = 0.393;
        public const double GreenToRed = 0.769;
        public const double BlueToRed = 0.189;

        public const double RedToGreen = 0.349;
        public const double GreenToGreen = 0.686;
        public const double BlueToGreen = 0.168;

        public const double RedToBlue = 0.272;
        public const double GreenToBlue = 0.534;
        public const double BlueToBlue = 0.131;
    }
}
