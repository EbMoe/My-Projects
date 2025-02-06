using System;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    partial class Report
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
            this.lblLocation = new System.Windows.Forms.Label();
            this.txtLocation = new System.Windows.Forms.TextBox();
            this.lblCategory = new System.Windows.Forms.Label();
            this.cmbCategory = new System.Windows.Forms.ComboBox();
            this.lblDescription = new System.Windows.Forms.Label();
            this.rtbDescription = new System.Windows.Forms.RichTextBox();
            this.btnAttachMedia = new System.Windows.Forms.Button();
            this.lblMedia = new System.Windows.Forms.Label();
            this.btnSubmit = new System.Windows.Forms.Button();
            this.progressBar = new System.Windows.Forms.ProgressBar();
            this.btnBackToMenu = new System.Windows.Forms.Button();
            this.SuspendLayout();

            // lblLocation
            this.lblLocation.AutoSize = true;
            this.lblLocation.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblLocation.ForeColor = System.Drawing.Color.White;
            this.lblLocation.Location = new System.Drawing.Point(30, 20);
            this.lblLocation.Name = "lblLocation";
            this.lblLocation.Size = new System.Drawing.Size(67, 19);
            this.lblLocation.TabIndex = 0;
            this.lblLocation.Text = "Location:";

            // txtLocation
            this.txtLocation.BackColor = System.Drawing.Color.FromArgb(64, 64, 64);
            this.txtLocation.ForeColor = System.Drawing.Color.White;
            this.txtLocation.Location = new System.Drawing.Point(30, 45);
            this.txtLocation.Name = "txtLocation";
            this.txtLocation.Size = new System.Drawing.Size(300, 23);
            this.txtLocation.TabIndex = 1;
            this.txtLocation.TextChanged += new System.EventHandler(this.InputChanged);

            // lblCategory
            this.lblCategory.AutoSize = true;
            this.lblCategory.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblCategory.ForeColor = System.Drawing.Color.White;
            this.lblCategory.Location = new System.Drawing.Point(30, 80);
            this.lblCategory.Name = "lblCategory";
            this.lblCategory.Size = new System.Drawing.Size(72, 19);
            this.lblCategory.TabIndex = 2;
            this.lblCategory.Text = "Category:";

            // cmbCategory
            this.cmbCategory.BackColor = System.Drawing.Color.FromArgb(64, 64, 64);
            this.cmbCategory.ForeColor = System.Drawing.Color.White;
            this.cmbCategory.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.cmbCategory.FormattingEnabled = true;
            this.cmbCategory.Items.AddRange(new object[] {
            "Sanitation",
            "Roads",
            "Utilities"});
            this.cmbCategory.Location = new System.Drawing.Point(30, 105);
            this.cmbCategory.Name = "cmbCategory";
            this.cmbCategory.Size = new System.Drawing.Size(300, 23);
            this.cmbCategory.TabIndex = 3;
            this.cmbCategory.SelectedIndexChanged += new System.EventHandler(this.InputChanged);

            // lblDescription
            this.lblDescription.AutoSize = true;
            this.lblDescription.Font = new System.Drawing.Font("Segoe UI", 10F, System.Drawing.FontStyle.Bold);
            this.lblDescription.ForeColor = System.Drawing.Color.White;
            this.lblDescription.Location = new System.Drawing.Point(30, 140);
            this.lblDescription.Name = "lblDescription";
            this.lblDescription.Size = new System.Drawing.Size(84, 19);
            this.lblDescription.TabIndex = 4;
            this.lblDescription.Text = "Description:";

            // rtbDescription
            this.rtbDescription.BackColor = System.Drawing.Color.FromArgb(64, 64, 64);
            this.rtbDescription.ForeColor = System.Drawing.Color.White;
            this.rtbDescription.Location = new System.Drawing.Point(30, 165);
            this.rtbDescription.Name = "rtbDescription";
            this.rtbDescription.Size = new System.Drawing.Size(300, 100);
            this.rtbDescription.TabIndex = 5;
            this.rtbDescription.Text = "";
            this.rtbDescription.TextChanged += new System.EventHandler(this.InputChanged);

            // btnAttachMedia
            this.btnAttachMedia.BackColor = System.Drawing.Color.SteelBlue;
            this.btnAttachMedia.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAttachMedia.ForeColor = System.Drawing.Color.White;
            this.btnAttachMedia.Location = new System.Drawing.Point(30, 280);
            this.btnAttachMedia.Name = "btnAttachMedia";
            this.btnAttachMedia.Size = new System.Drawing.Size(100, 25);
            this.btnAttachMedia.TabIndex = 6;
            this.btnAttachMedia.Text = "Attach Media";
            this.btnAttachMedia.UseVisualStyleBackColor = false;
            this.btnAttachMedia.Click += new System.EventHandler(this.btnAttachMedia_Click);

            // lblMedia
            this.lblMedia.AutoSize = true;
            this.lblMedia.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Italic);
            this.lblMedia.ForeColor = System.Drawing.Color.Gray;
            this.lblMedia.Location = new System.Drawing.Point(140, 285);
            this.lblMedia.Name = "lblMedia";
            this.lblMedia.Size = new System.Drawing.Size(106, 15);
            this.lblMedia.TabIndex = 7;
            this.lblMedia.Text = "No file selected yet.";

            // btnSubmit
            this.btnSubmit.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.btnSubmit.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnSubmit.ForeColor = System.Drawing.Color.White;
            this.btnSubmit.Location = new System.Drawing.Point(230, 320);
            this.btnSubmit.Name = "btnSubmit";
            this.btnSubmit.Size = new System.Drawing.Size(100, 25);
            this.btnSubmit.TabIndex = 8;
            this.btnSubmit.Text = "Submit";
            this.btnSubmit.UseVisualStyleBackColor = false;
            this.btnSubmit.Click += new System.EventHandler(this.btnSubmit_Click);

            // progressBar
            this.progressBar.Location = new System.Drawing.Point(30, 360);
            this.progressBar.Name = "progressBar";
            this.progressBar.Size = new System.Drawing.Size(300, 23);
            this.progressBar.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.progressBar.TabIndex = 9;

            // btnBackToMenu
            this.btnBackToMenu.BackColor = System.Drawing.Color.DarkRed;
            this.btnBackToMenu.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnBackToMenu.ForeColor = System.Drawing.Color.White;
            this.btnBackToMenu.Location = new System.Drawing.Point(30, 320);
            this.btnBackToMenu.Name = "btnBackToMenu";
            this.btnBackToMenu.Size = new System.Drawing.Size(100, 25);
            this.btnBackToMenu.TabIndex = 10;
            this.btnBackToMenu.Text = "Back to Menu";
            this.btnBackToMenu.UseVisualStyleBackColor = false;
            this.btnBackToMenu.Click += new System.EventHandler(this.btnBackToMenu_Click);

            // Form1
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(45, 45, 48);
            this.ClientSize = new System.Drawing.Size(384, 411);
            this.Controls.Add(this.progressBar);
            this.Controls.Add(this.btnSubmit);
            this.Controls.Add(this.btnBackToMenu);
            this.Controls.Add(this.lblMedia);
            this.Controls.Add(this.btnAttachMedia);
            this.Controls.Add(this.rtbDescription);
            this.Controls.Add(this.lblDescription);
            this.Controls.Add(this.cmbCategory);
            this.Controls.Add(this.lblCategory);
            this.Controls.Add(this.txtLocation);
            this.Controls.Add(this.lblLocation);
            this.Name = "Form1";
            this.Text = "Report Issues";
            this.ResumeLayout(false);
            this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Label lblLocation;
        private System.Windows.Forms.TextBox txtLocation;
        private System.Windows.Forms.Label lblCategory;
        private System.Windows.Forms.ComboBox cmbCategory;
        private System.Windows.Forms.Label lblDescription;
        private System.Windows.Forms.RichTextBox rtbDescription;
        private System.Windows.Forms.Button btnAttachMedia;
        private System.Windows.Forms.Label lblMedia;
        private System.Windows.Forms.Button btnSubmit;
        private System.Windows.Forms.ProgressBar progressBar;
        private System.Windows.Forms.Button btnBackToMenu;
    }
}
