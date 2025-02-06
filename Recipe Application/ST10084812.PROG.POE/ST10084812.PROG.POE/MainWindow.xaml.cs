using System;
using System.Windows;

namespace ST10084812.PROG.POE
{
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }

        private void GoToMenu_Click(object sender, RoutedEventArgs e)
        {
            // Open the menu selection window
            Menu menuWindow = new Menu();
            menuWindow.Show();
            Close(); // Close the welcome window
        }
    }
}
