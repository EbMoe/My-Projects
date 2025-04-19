using System;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    public partial class MainMenuForm : Form
    {
        public MainMenuForm()
        {
            InitializeComponent();
        }

        private void btnReportIssues_Click(object sender, EventArgs e)
        {
            // Open the Report Issues form
            Form1 reportIssuesForm = new Form1();
            reportIssuesForm.Show();
        }
    }
}
