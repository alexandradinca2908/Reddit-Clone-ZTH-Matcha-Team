using WebImageProcessor.Filters;

namespace WebImageProcessor.Services
{
    public class FilterInfo
    {
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string Label { get; set; } = string.Empty;
    }
    public class FilterListService
    {
        public IEnumerable<FilterInfo> GetAllFilters()
        {
            List<FilterInfo> FilterList = new List<FilterInfo>
            {
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Nothing, Name = "none", Label = "No Filter" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Grayscale, Name = "grayscale", Label = "Black and White" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Sepia, Name = "sepia", Label = "Sepia" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Invert, Name = "invert", Label = "Inverted" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.HorizontalFlip, Name = "horizontalflip", Label = "Horizontal Flip" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Blur, Name = "blur", Label = "Blur"}
            };

            return FilterList;
        }
    }
}
