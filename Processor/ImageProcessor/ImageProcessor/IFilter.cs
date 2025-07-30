using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Common;

namespace Filters
{
    public interface IFilter
    {
        RawImage Apply(RawImage originalImage);
    }
}
