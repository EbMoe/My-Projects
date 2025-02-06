using System;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    public partial class MainMenuForm : Form
    {
        private static LocalEventsForm localEventsForm;
        private static ServiceRequestStatusForm serviceRequestStatusForm;

        public MainMenuForm()
        {
            InitializeComponent();
        }

        private void btnReportIssues_Click(object sender, EventArgs e)
        {
            // Open the Report Issues form
            Report reportIssuesForm = new Report();
            reportIssuesForm.Show();
        }

        private void btnLocalEvents_Click(object sender, EventArgs e)
        {
            // Check if the LocalEventsForm is already open
            if (localEventsForm == null || localEventsForm.IsDisposed)
            {
                localEventsForm = new LocalEventsForm();
                localEventsForm.Show();
            }
            else
            {
                // Bring the existing form to the front
                localEventsForm.BringToFront();
            }
        }

        private void btnServiceRequestStatus_Click(object sender, EventArgs e)
        {
            // Check if the ServiceRequestStatusForm is already open
            if (serviceRequestStatusForm == null || serviceRequestStatusForm.IsDisposed)
            {
                // Pass the current instance of MainMenuForm (this) to the ServiceRequestStatusForm constructor
                serviceRequestStatusForm = new ServiceRequestStatusForm(this);
                serviceRequestStatusForm.Show();
            }
            else
            {
                // Bring the existing form to the front
                serviceRequestStatusForm.BringToFront();
            }
        }
    }
}
