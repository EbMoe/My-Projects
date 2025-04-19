using System;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        // Event handler for input changes (Location, Category, Description)
        private void InputChanged(object sender, EventArgs e)
        {
            int progress = 0;
            if (!string.IsNullOrWhiteSpace(txtLocation.Text))
                progress += 25;
            if (cmbCategory.SelectedIndex >= 0)
                progress += 25;
            if (!string.IsNullOrWhiteSpace(rtbDescription.Text))
                progress += 25;
            if (!lblMedia.Text.Contains("No file selected"))
                progress += 25;

            // Update the progress bar
            progressBar.Value = progress;
        }

        // Event handler for attaching media
        private void btnAttachMedia_Click(object sender, EventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();
            if (openFileDialog.ShowDialog() == DialogResult.OK)
            {
                lblMedia.Text = "File: " + openFileDialog.FileName;
                InputChanged(sender, e); // Update progress bar
            }
        }

        // Event handler for submitting the form
        private void btnSubmit_Click(object sender, EventArgs e)
        {
            // Validate inputs before submission
            if (ValidateInputs())
            {
                MessageBox.Show("Issue reported successfully!", "Confirmation", MessageBoxButtons.OK, MessageBoxIcon.Information);

                // Optionally reset the form after submission
                ResetForm();
            }
            else
            {
                MessageBox.Show("Please fill out all fields and attach a media file.", "Error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        // Event handler for "Back to Main Menu" button
        private void btnBackToMenu_Click(object sender, EventArgs e)
        {
            // Close this form and return to the main menu
            this.Close();
        }

        // Method to validate inputs
        private bool ValidateInputs()
        {
            return !string.IsNullOrWhiteSpace(txtLocation.Text) &&
                   cmbCategory.SelectedIndex >= 0 &&
                   !string.IsNullOrWhiteSpace(rtbDescription.Text) &&
                   !lblMedia.Text.Contains("No file selected");
        }

        // Method to reset the form after submission
        private void ResetForm()
        {
            txtLocation.Clear();
            cmbCategory.SelectedIndex = -1;
            rtbDescription.Clear();
            lblMedia.Text = "No file selected yet.";
            progressBar.Value = 0;
        }
    }
}
