using System;
using System.Windows.Forms;
using System.Threading.Tasks;

namespace MunicipalServicesApp
{
    static class Program
    {
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            // Show the splash screen
            using (SplashScreenForm splash = new SplashScreenForm())
            {
                splash.Show();
                // Delay to keep the splash screen visible for 4 seconds
                Task.Delay(4000).Wait();
            }

            // After the splash screen, run the main menu form
            Application.Run(new MainMenuForm());
        }
    }
}
