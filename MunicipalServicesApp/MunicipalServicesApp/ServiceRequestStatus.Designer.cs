using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    partial class ServiceRequestStatusForm
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.ListView lstRequests;
        private System.Windows.Forms.TextBox txtSearch;
        private System.Windows.Forms.Button btnSearch;
        private System.Windows.Forms.Button btnViewDependencies;
        private System.Windows.Forms.Button btnRefresh;
        private System.Windows.Forms.Button btnBackToMainMenu;
        private System.Windows.Forms.Button btnShowOldestRequest; 
        private System.Windows.Forms.Button btnViewHeap; 

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
            this.lstRequests = new System.Windows.Forms.ListView();
            this.txtSearch = new System.Windows.Forms.TextBox();
            this.btnSearch = new System.Windows.Forms.Button();
            this.btnViewDependencies = new System.Windows.Forms.Button();
            this.btnRefresh = new System.Windows.Forms.Button();
            this.btnBackToMainMenu = new System.Windows.Forms.Button();
            this.btnShowOldestRequest = new System.Windows.Forms.Button(); 
            this.btnViewHeap = new System.Windows.Forms.Button(); 
            this.SuspendLayout();

            // lstRequests
            this.lstRequests.Location = new System.Drawing.Point(12, 12);
            this.lstRequests.Name = "lstRequests";
            this.lstRequests.Size = new System.Drawing.Size(760, 300);
            this.lstRequests.TabIndex = 0;
            this.lstRequests.View = System.Windows.Forms.View.Details;
            this.lstRequests.Columns.Add("Request ID", 100);
            this.lstRequests.Columns.Add("Service Type", 300);
            this.lstRequests.Columns.Add("Status", 100);
            this.lstRequests.Columns.Add("Date Submitted", 150);
            this.lstRequests.BackColor = System.Drawing.Color.FromArgb(30, 30, 30);
            this.lstRequests.ForeColor = System.Drawing.Color.White;
            this.lstRequests.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;

            // txtSearch
            this.txtSearch.Location = new System.Drawing.Point(12, 330);
            this.txtSearch.Name = "txtSearch";
            this.txtSearch.Size = new System.Drawing.Size(200, 23);
            this.txtSearch.TabIndex = 1;
            this.txtSearch.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;

            // btnSearch 
            this.btnSearch.Location = new System.Drawing.Point(230, 330);
            this.btnSearch.Name = "btnSearch";
            this.btnSearch.Size = new System.Drawing.Size(75, 23);
            this.btnSearch.TabIndex = 2;
            this.btnSearch.Text = "Search";
            this.btnSearch.UseVisualStyleBackColor = true;
            this.btnSearch.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.btnSearch.ForeColor = System.Drawing.Color.White;
            this.btnSearch.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            this.btnSearch.Click += new System.EventHandler(this.btnSearch_Click);

            // btnViewDependencies
            this.btnViewDependencies.Location = new System.Drawing.Point(320, 330);
            this.btnViewDependencies.Name = "btnViewDependencies";
            this.btnViewDependencies.Size = new System.Drawing.Size(150, 23);
            this.btnViewDependencies.TabIndex = 3;
            this.btnViewDependencies.Text = "View Dependencies";
            this.btnViewDependencies.UseVisualStyleBackColor = true;
            this.btnViewDependencies.BackColor = System.Drawing.Color.CornflowerBlue;
            this.btnViewDependencies.ForeColor = System.Drawing.Color.White;
            this.btnViewDependencies.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            this.btnViewDependencies.Click += new System.EventHandler(this.btnViewDependencies_Click);

            // btnRefresh
            this.btnRefresh.Location = new System.Drawing.Point(490, 330);
            this.btnRefresh.Name = "btnRefresh";
            this.btnRefresh.Size = new System.Drawing.Size(75, 23);
            this.btnRefresh.TabIndex = 4;
            this.btnRefresh.Text = "Refresh";
            this.btnRefresh.UseVisualStyleBackColor = true;
            this.btnRefresh.BackColor = System.Drawing.Color.Orange;
            this.btnRefresh.ForeColor = System.Drawing.Color.White;
            this.btnRefresh.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            this.btnRefresh.Click += new System.EventHandler(this.btnRefresh_Click);

            // btnBackToMainMenu
            this.btnBackToMainMenu.Location = new System.Drawing.Point(612, 330);
            this.btnBackToMainMenu.Name = "btnBackToMainMenu";
            this.btnBackToMainMenu.Size = new System.Drawing.Size(150, 23);
            this.btnBackToMainMenu.TabIndex = 5;
            this.btnBackToMainMenu.Text = "Back to Main Menu";
            this.btnBackToMainMenu.UseVisualStyleBackColor = true;
            this.btnBackToMainMenu.BackColor = System.Drawing.Color.DarkRed;
            this.btnBackToMainMenu.ForeColor = System.Drawing.Color.White;
            this.btnBackToMainMenu.Anchor = AnchorStyles.Bottom | AnchorStyles.Right;
            this.btnBackToMainMenu.Click += new System.EventHandler(this.btnBackToMainMenu_Click);

            // btnShowOldestRequest
            this.btnShowOldestRequest.Location = new System.Drawing.Point(12, 370);
            this.btnShowOldestRequest.Name = "btnShowOldestRequest";
            this.btnShowOldestRequest.Size = new System.Drawing.Size(150, 23);
            this.btnShowOldestRequest.TabIndex = 6;
            this.btnShowOldestRequest.Text = "Show Oldest Request";
            this.btnShowOldestRequest.UseVisualStyleBackColor = true;
            this.btnShowOldestRequest.BackColor = System.Drawing.Color.LightSeaGreen;
            this.btnShowOldestRequest.ForeColor = System.Drawing.Color.White;
            this.btnShowOldestRequest.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            this.btnShowOldestRequest.Click += new System.EventHandler(this.btnShowOldestRequest_Click);

            // btnViewHeap
            this.btnViewHeap.Location = new System.Drawing.Point(180, 370);
            this.btnViewHeap.Name = "btnViewHeap";
            this.btnViewHeap.Size = new System.Drawing.Size(150, 23);
            this.btnViewHeap.TabIndex = 7;
            this.btnViewHeap.Text = "View Heap";
            this.btnViewHeap.UseVisualStyleBackColor = true;
            this.btnViewHeap.BackColor = System.Drawing.Color.MediumSlateBlue;
            this.btnViewHeap.ForeColor = System.Drawing.Color.White;
            this.btnViewHeap.Anchor = AnchorStyles.Bottom | AnchorStyles.Left;
            this.btnViewHeap.Click += new System.EventHandler(this.btnViewHeap_Click);

            // ServiceRequestStatusForm
            this.ClientSize = new System.Drawing.Size(800, 450);
            this.Controls.Add(this.lstRequests);
            this.Controls.Add(this.txtSearch);
            this.Controls.Add(this.btnSearch);
            this.Controls.Add(this.btnViewDependencies);
            this.Controls.Add(this.btnRefresh);
            this.Controls.Add(this.btnBackToMainMenu);
            this.Controls.Add(this.btnShowOldestRequest);
            this.Controls.Add(this.btnViewHeap); 
            this.Name = "ServiceRequestStatusForm";
            this.Text = "Service Request Status";
            this.BackColor = System.Drawing.Color.FromArgb(20, 20, 20);
            this.ForeColor = System.Drawing.Color.White;
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
