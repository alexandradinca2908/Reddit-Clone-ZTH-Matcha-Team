using System;
using App;

namespace EntryPoint
{
    class Program
    {
        static void Main(string[] args)
        {
            var app = new App.App();

            app.ConsoleMode();
            app.Start();

            Console.WriteLine("Press any key to exit.");
            Console.ReadKey();
        }
    }
}