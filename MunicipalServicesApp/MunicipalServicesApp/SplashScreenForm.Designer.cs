using System.Windows.Forms;
using System.Drawing;

namespace MunicipalServicesApp
{
    partial class SplashScreenForm
    {
        private System.ComponentModel.IContainer components = null;
        private Label lblSplashTitle;
        private ProgressBar progressBar;
        private Timer timer;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.lblSplashTitle = new System.Windows.Forms.Label();
            this.progressBar = new System.Windows.Forms.ProgressBar();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // lblSplashTitle
            this.lblSplashTitle.AutoSize = true;
            this.lblSplashTitle.Font = new System.Drawing.Font("Segoe UI", 24F, System.Drawing.FontStyle.Bold);
            this.lblSplashTitle.ForeColor = System.Drawing.Color.LightSkyBlue;
            this.lblSplashTitle.Location = new System.Drawing.Point(176, 146);
            this.lblSplashTitle.Name = "lblSplashTitle";
            this.lblSplashTitle.Size = new System.Drawing.Size(462, 54);
            this.lblSplashTitle.TabIndex = 0;
            this.lblSplashTitle.Text = "Municipal Services App";
            this.lblSplashTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // progressBar
            this.progressBar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(30)))), ((int)(((byte)(30)))), ((int)(((byte)(30)))));
            this.progressBar.ForeColor = System.Drawing.Color.LimeGreen;
            this.progressBar.Location = new System.Drawing.Point(201, 227);
            this.progressBar.Name = "progressBar";
            this.progressBar.Size = new System.Drawing.Size(400, 30);
            this.progressBar.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.progressBar.TabIndex = 1;
            // timer
            this.timer.Enabled = true;
            this.timer.Interval = 80;
            this.timer.Tick += new System.EventHandler(this.Timer_Tick);
            // SplashScreenForm
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(20)))), ((int)(((byte)(20)))), ((int)(((byte)(20)))));
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.lblSplashTitle);
            this.Controls.Add(this.progressBar);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "SplashScreenForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        // Progress bar animation
        private void Timer_Tick(object sender, System.EventArgs e)
        {
            if (progressBar.Value < 100)
            {
                progressBar.Value += 2; 
            }
            else
            {
                timer.Stop();
                this.Close(); // Closes splash screen when loading is complete
                MainMenuForm mainMenu = new MainMenuForm();
                mainMenu.Show();
            }
        }
    }
}
