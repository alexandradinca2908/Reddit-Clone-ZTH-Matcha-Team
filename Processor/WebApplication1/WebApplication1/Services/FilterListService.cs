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
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Nothing, Name = "none", Label = "Fara filtru" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Grayscale, Name = "grayscale", Label = "Alb-negru" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Sepia, Name = "sepia", Label = "Sepia" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Invert, Name = "invert", Label = "Invertit" }
            };

            return FilterList;
        }
    }
}
