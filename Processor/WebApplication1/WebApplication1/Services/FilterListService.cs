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
                new FilterInfo { Id = (int)FiltersEnum.Nothing, Name = "none", Label = "No Filter" },
                new FilterInfo { Id = (int)FiltersEnum.Grayscale, Name = "grayscale", Label = "Black and White" },
                new FilterInfo { Id = (int)FiltersEnum.Sepia, Name = "sepia", Label = "Sepia" },
                new FilterInfo { Id = (int)FiltersEnum.Invert, Name = "invert", Label = "Inverted" },
                new FilterInfo { Id = (int)FiltersEnum.HorizontalFlip, Name = "horizontalflip", Label = "Horizontal Flip" }
            };

            return FilterList;
        }
    }
}
