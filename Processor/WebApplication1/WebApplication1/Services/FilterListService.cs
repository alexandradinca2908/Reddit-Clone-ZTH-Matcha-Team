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
<<<<<<< HEAD
                new FilterInfo { Id = (int)FiltersEnum.Nothing, Name = "none", Label = "No Filter" },
                new FilterInfo { Id = (int)FiltersEnum.Grayscale, Name = "grayscale", Label = "Black and White" },
                new FilterInfo { Id = (int)FiltersEnum.Sepia, Name = "sepia", Label = "Sepia" },
                new FilterInfo { Id = (int)FiltersEnum.Invert, Name = "invert", Label = "Inverted" },
                new FilterInfo { Id = (int)FiltersEnum.HorizontalFlip, Name = "horizontalflip", Label = "Horizontal Flip" }
=======
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Nothing, Name = "none", Label = "No Filter" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Grayscale, Name = "grayscale", Label = "Black and White" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Sepia, Name = "sepia", Label = "Sepia" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Invert, Name = "invert", Label = "Inverted" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.HorizontalFlip, Name = "horizontalflip", Label = "Horizontal Flip" },
                new FilterInfo { Id = (int)FiltersEnum.FilterTypes.Blur, Name = "blur", Label = "Blur"}
>>>>>>> 9c5d90d04c1e467928b85fdcfafd298d4453771e
            };

            return FilterList;
        }
    }
}
