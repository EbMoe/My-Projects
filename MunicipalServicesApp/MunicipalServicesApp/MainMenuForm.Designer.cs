namespace MunicipalServicesApp
{
    partial class MainMenuForm
    {
        private System.ComponentModel.IContainer components = null;

        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        private void InitializeComponent()
        {
            this.lblAppName = new System.Windows.Forms.Label();
            this.btnReportIssues = new System.Windows.Forms.Button();
            this.btnLocalEvents = new System.Windows.Forms.Button();
            this.btnServiceRequestStatus = new System.Windows.Forms.Button();
            this.lblAboutTitle = new System.Windows.Forms.Label();
            this.lblAppDetails = new System.Windows.Forms.Label();
            this.lblAppVersion = new System.Windows.Forms.Label();
            this.SuspendLayout();

            // lblAppName
            this.lblAppName.AutoSize = true;
            this.lblAppName.Font = new System.Drawing.Font("Segoe UI", 28F, System.Drawing.FontStyle.Bold);
            this.lblAppName.ForeColor = System.Drawing.Color.LightSkyBlue;
            this.lblAppName.Location = new System.Drawing.Point(150, 30);
            this.lblAppName.Name = "lblAppName";
            this.lblAppName.Size = new System.Drawing.Size(300, 50);
            this.lblAppName.TabIndex = 0;
            this.lblAppName.Text = "Municipal App";
            this.lblAppName.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;

            // btnReportIssues
            this.btnReportIssues.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right;
            this.btnReportIssues.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.btnReportIssues.FlatAppearance.BorderSize = 0;
            this.btnReportIssues.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnReportIssues.Font = new System.Drawing.Font("Segoe UI", 14F, System.Drawing.FontStyle.Bold);
            this.btnReportIssues.ForeColor = System.Drawing.Color.White;
            this.btnReportIssues.Location = new System.Drawing.Point(100, 120);
            this.btnReportIssues.Name = "btnReportIssues";
            this.btnReportIssues.Size = new System.Drawing.Size(400, 60);
            this.btnReportIssues.TabIndex = 1;
            this.btnReportIssues.Text = "Report Issues";
            this.btnReportIssues.UseVisualStyleBackColor = false;
            this.btnReportIssues.Click += new System.EventHandler(this.btnReportIssues_Click);

            // btnLocalEvents
            this.btnLocalEvents.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right;
            this.btnLocalEvents.BackColor = System.Drawing.Color.SteelBlue;
            this.btnLocalEvents.FlatAppearance.BorderSize = 0;
            this.btnLocalEvents.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnLocalEvents.Font = new System.Drawing.Font("Segoe UI", 14F, System.Drawing.FontStyle.Bold);
            this.btnLocalEvents.ForeColor = System.Drawing.Color.White;
            this.btnLocalEvents.Location = new System.Drawing.Point(100, 200);
            this.btnLocalEvents.Name = "btnLocalEvents";
            this.btnLocalEvents.Size = new System.Drawing.Size(400, 60);
            this.btnLocalEvents.TabIndex = 2;
            this.btnLocalEvents.Text = "Local Events";
            this.btnLocalEvents.UseVisualStyleBackColor = false;
            this.btnLocalEvents.Click += new System.EventHandler(this.btnLocalEvents_Click);

            // btnServiceRequestStatus
            this.btnServiceRequestStatus.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right;
            this.btnServiceRequestStatus.BackColor = System.Drawing.Color.Orange;
            this.btnServiceRequestStatus.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnServiceRequestStatus.Font = new System.Drawing.Font("Segoe UI", 14F, System.Drawing.FontStyle.Bold);
            this.btnServiceRequestStatus.ForeColor = System.Drawing.Color.White;
            this.btnServiceRequestStatus.Location = new System.Drawing.Point(100, 280);
            this.btnServiceRequestStatus.Name = "btnServiceRequestStatus";
            this.btnServiceRequestStatus.Size = new System.Drawing.Size(400, 60);
            this.btnServiceRequestStatus.TabIndex = 3;
            this.btnServiceRequestStatus.Text = "Service Request Status";
            this.btnServiceRequestStatus.UseVisualStyleBackColor = false;
            this.btnServiceRequestStatus.Click += new System.EventHandler(this.btnServiceRequestStatus_Click);

            // lblAboutTitle
            this.lblAboutTitle.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left;
            this.lblAboutTitle.AutoSize = true;
            this.lblAboutTitle.Font = new System.Drawing.Font("Segoe UI", 12F, System.Drawing.FontStyle.Bold);
            this.lblAboutTitle.ForeColor = System.Drawing.Color.SteelBlue;
            this.lblAboutTitle.Location = new System.Drawing.Point(100, 360);
            this.lblAboutTitle.Name = "lblAboutTitle";
            this.lblAboutTitle.Size = new System.Drawing.Size(55, 21);
            this.lblAboutTitle.TabIndex = 4;
            this.lblAboutTitle.Text = "About";

            // lblAppDetails
            this.lblAppDetails.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left;
            this.lblAppDetails.AutoSize = true;
            this.lblAppDetails.Font = new System.Drawing.Font("Segoe UI", 10F);
            this.lblAppDetails.ForeColor = System.Drawing.Color.White;
            this.lblAppDetails.Location = new System.Drawing.Point(100, 385);
            this.lblAppDetails.Name = "lblAppDetails";
            this.lblAppDetails.Size = new System.Drawing.Size(400, 19);
            this.lblAppDetails.Text = "Manage your municipal services efficiently.";

            // lblAppVersion
            this.lblAppVersion.Anchor = System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left;
            this.lblAppVersion.AutoSize = true;
            this.lblAppVersion.Font = new System.Drawing.Font("Segoe UI", 10F);
            this.lblAppVersion.ForeColor = System.Drawing.Color.White;
            this.lblAppVersion.Location = new System.Drawing.Point(100, 410);
            this.lblAppVersion.Name = "lblAppVersion";
            this.lblAppVersion.Size = new System.Drawing.Size(150, 19);
            this.lblAppVersion.Text = "Version: 3.0";

            // MainMenuForm
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 16F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(40)))), ((int)(((byte)(40)))), ((int)(((byte)(45)))));
            this.ClientSize = new System.Drawing.Size(600, 450);
            this.Controls.Add(this.lblAppVersion);
            this.Controls.Add(this.lblAppDetails);
            this.Controls.Add(this.lblAboutTitle);
            this.Controls.Add(this.btnServiceRequestStatus);
            this.Controls.Add(this.btnLocalEvents);
            this.Controls.Add(this.btnReportIssues);
            this.Controls.Add(this.lblAppName);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Name = "MainMenuForm";
            this.Text = "Main Menu";
            this.Load += new System.EventHandler(this.MainMenuForm_Load);
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Label lblAppName;
        private System.Windows.Forms.Button btnReportIssues;
        private System.Windows.Forms.Button btnLocalEvents;
        private System.Windows.Forms.Button btnServiceRequestStatus;
        private System.Windows.Forms.Label lblAboutTitle;
        private System.Windows.Forms.Label lblAppDetails;
        private System.Windows.Forms.Label lblAppVersion;

        private void MainMenuForm_Load(object sender, System.EventArgs e)
        {
            int centerX = (this.ClientSize.Width - lblAppName.Width) / 2;
            lblAppName.Location = new System.Drawing.Point(centerX, lblAppName.Location.Y);
        }
    }
}
