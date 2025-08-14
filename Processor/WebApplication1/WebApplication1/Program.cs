using WebImageProcessor.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddHttpClient();
builder.Services.AddScoped<FilterListService>();
builder.Services.AddScoped<ApplyFilterService>();

var app = builder.Build();

app.MapControllers();

app.Run();
