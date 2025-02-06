using System.Windows.Forms;

namespace MunicipalServicesApp
{
    partial class LocalEventsForm
    {
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.ListView lstEvents;
        private System.Windows.Forms.TextBox txtSearch;
        private System.Windows.Forms.Button btnSearch;
        private System.Windows.Forms.Button btnShowRecent;
        private System.Windows.Forms.Button btnProcessNext;
        private System.Windows.Forms.Button btnBackToMainMenu;
        private System.Windows.Forms.Button btnDateSearch;
        private System.Windows.Forms.Button btnReset;
        private System.Windows.Forms.ComboBox cmbCategory;
        private System.Windows.Forms.DateTimePicker dtpDateFilter;
        private System.Windows.Forms.GroupBox grpSearch;
        private System.Windows.Forms.GroupBox grpEvents;
        private System.Windows.Forms.GroupBox grpRecommendations;
        private System.Windows.Forms.ListBox lstRecommendations;

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
            this.grpSearch = new System.Windows.Forms.GroupBox();
            this.txtSearch = new System.Windows.Forms.TextBox();
            this.btnSearch = new System.Windows.Forms.Button();
            this.btnDateSearch = new System.Windows.Forms.Button();
            this.cmbCategory = new System.Windows.Forms.ComboBox();
            this.dtpDateFilter = new System.Windows.Forms.DateTimePicker();
            this.grpEvents = new System.Windows.Forms.GroupBox();
            this.lstEvents = new System.Windows.Forms.ListView();
            this.btnShowRecent = new System.Windows.Forms.Button();
            this.btnProcessNext = new System.Windows.Forms.Button();
            this.btnBackToMainMenu = new System.Windows.Forms.Button();
            this.btnReset = new System.Windows.Forms.Button(); 
            this.grpRecommendations = new System.Windows.Forms.GroupBox();
            this.lstRecommendations = new System.Windows.Forms.ListBox();

            // grpSearch
            this.grpSearch.Controls.Add(this.txtSearch);
            this.grpSearch.Controls.Add(this.btnSearch);
            this.grpSearch.Controls.Add(this.btnDateSearch);
            this.grpSearch.Controls.Add(this.cmbCategory);
            this.grpSearch.Controls.Add(this.dtpDateFilter);
            this.grpSearch.Location = new System.Drawing.Point(12, 12);
            this.grpSearch.Name = "grpSearch";
            this.grpSearch.Size = new System.Drawing.Size(760, 100);
            this.grpSearch.TabIndex = 0;
            this.grpSearch.TabStop = false;
            this.grpSearch.Text = "Search and Filter";
            this.grpSearch.ForeColor = System.Drawing.Color.White;
            this.grpSearch.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right;

            // txtSearch
            this.txtSearch.Location = new System.Drawing.Point(6, 22);
            this.txtSearch.Name = "txtSearch";
            this.txtSearch.Size = new System.Drawing.Size(250, 23);
            this.txtSearch.TabIndex = 1;

            // btnSearch
            this.btnSearch.Location = new System.Drawing.Point(642, 22);
            this.btnSearch.Name = "btnSearch";
            this.btnSearch.Size = new System.Drawing.Size(100, 23);
            this.btnSearch.TabIndex = 2;
            this.btnSearch.Text = "Search";
            this.btnSearch.UseVisualStyleBackColor = true;
            this.btnSearch.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.btnSearch.ForeColor = System.Drawing.Color.White;
            this.btnSearch.Click += new System.EventHandler(this.btnSearch_Click);

            // btnDateSearch
            this.btnDateSearch.Location = new System.Drawing.Point(642, 55);
            this.btnDateSearch.Name = "btnDateSearch";
            this.btnDateSearch.Size = new System.Drawing.Size(100, 23);
            this.btnDateSearch.TabIndex = 5;
            this.btnDateSearch.Text = "Date Search";
            this.btnDateSearch.UseVisualStyleBackColor = true;
            this.btnDateSearch.BackColor = System.Drawing.Color.CornflowerBlue;
            this.btnDateSearch.ForeColor = System.Drawing.Color.White;
            this.btnDateSearch.Click += new System.EventHandler(this.btnDateSearch_Click);

            // cmbCategory
            this.cmbCategory.FormattingEnabled = true;
            this.cmbCategory.Location = new System.Drawing.Point(272, 22);
            this.cmbCategory.Name = "cmbCategory";
            this.cmbCategory.Size = new System.Drawing.Size(150, 23);
            this.cmbCategory.TabIndex = 3;
            this.cmbCategory.Items.AddRange(new object[] {
                "All", "Environment", "Local", "Government", "Sports", "Music", "Charity", "Education" });

            // dtpDateFilter
            this.dtpDateFilter.Location = new System.Drawing.Point(428, 22);
            this.dtpDateFilter.Name = "dtpDateFilter";
            this.dtpDateFilter.Size = new System.Drawing.Size(200, 23);
            this.dtpDateFilter.TabIndex = 4;

            // grpEvents
            this.grpEvents.Controls.Add(this.lstEvents);
            this.grpEvents.Controls.Add(this.btnShowRecent);
            this.grpEvents.Controls.Add(this.btnProcessNext);
            this.grpEvents.Controls.Add(this.btnReset); 
            this.grpEvents.Controls.Add(this.btnBackToMainMenu);
            this.grpEvents.Location = new System.Drawing.Point(12, 120);
            this.grpEvents.Name = "grpEvents";
            this.grpEvents.Size = new System.Drawing.Size(760, 300);
            this.grpEvents.TabIndex = 5;
            this.grpEvents.TabStop = false;
            this.grpEvents.Text = "Upcoming Events";
            this.grpEvents.ForeColor = System.Drawing.Color.White;
            this.grpEvents.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;

            // lstEvents
            this.lstEvents.Location = new System.Drawing.Point(6, 22);
            this.lstEvents.Name = "lstEvents";
            this.lstEvents.Size = new System.Drawing.Size(600, 250);
            this.lstEvents.TabIndex = 6;
            this.lstEvents.View = View.Details;
            this.lstEvents.Columns.Add("Date", 150);
            this.lstEvents.Columns.Add("Event", 300);
            this.lstEvents.Columns.Add("Category", 150);
            this.lstEvents.BackColor = System.Drawing.Color.FromArgb(30, 30, 30);
            this.lstEvents.ForeColor = System.Drawing.Color.White;
            this.lstEvents.Anchor = AnchorStyles.Top | AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Bottom;

            // btnShowRecent
            this.btnShowRecent.Location = new System.Drawing.Point(612, 22);
            this.btnShowRecent.Name = "btnShowRecent";
            this.btnShowRecent.Size = new System.Drawing.Size(140, 23);
            this.btnShowRecent.TabIndex = 7;
            this.btnShowRecent.Text = "Show Recent";
            this.btnShowRecent.UseVisualStyleBackColor = true;
            this.btnShowRecent.BackColor = System.Drawing.Color.SteelBlue;
            this.btnShowRecent.ForeColor = System.Drawing.Color.White;
            this.btnShowRecent.Anchor = AnchorStyles.Top | AnchorStyles.Right;

            // btnProcessNext
            this.btnProcessNext.Location = new System.Drawing.Point(612, 51);
            this.btnProcessNext.Name = "btnProcessNext";
            this.btnProcessNext.Size = new System.Drawing.Size(140, 23);
            this.btnProcessNext.TabIndex = 8;
            this.btnProcessNext.Text = "Process Next";
            this.btnProcessNext.UseVisualStyleBackColor = true;
            this.btnProcessNext.BackColor = System.Drawing.Color.MediumSeaGreen;
            this.btnProcessNext.ForeColor = System.Drawing.Color.White;
            this.btnProcessNext.Anchor = AnchorStyles.Top | AnchorStyles.Right;

            // btnReset
            this.btnReset.Location = new System.Drawing.Point(612, 80); 
            this.btnReset.Name = "btnReset";
            this.btnReset.Size = new System.Drawing.Size(140, 23);
            this.btnReset.TabIndex = 10;
            this.btnReset.Text = "Reset";
            this.btnReset.UseVisualStyleBackColor = true;
            this.btnReset.BackColor = System.Drawing.Color.Orange;
            this.btnReset.ForeColor = System.Drawing.Color.White;
            this.btnReset.Anchor = AnchorStyles.Top | AnchorStyles.Right;
            this.btnReset.Click += new System.EventHandler(this.btnReset_Click);

            // btnBackToMainMenu
            this.btnBackToMainMenu.Location = new System.Drawing.Point(612, 109);
            this.btnBackToMainMenu.Name = "btnBackToMainMenu";
            this.btnBackToMainMenu.Size = new System.Drawing.Size(140, 23);
            this.btnBackToMainMenu.TabIndex = 9;
            this.btnBackToMainMenu.Text = "Back to Menu";
            this.btnBackToMainMenu.UseVisualStyleBackColor = true;
            this.btnBackToMainMenu.BackColor = System.Drawing.Color.DarkRed;
            this.btnBackToMainMenu.ForeColor = System.Drawing.Color.White;
            this.btnBackToMainMenu.Anchor = AnchorStyles.Top | AnchorStyles.Right;

            // grpRecommendations
            this.grpRecommendations.Controls.Add(this.lstRecommendations);
            this.grpRecommendations.Location = new System.Drawing.Point(12, 430);
            this.grpRecommendations.Name = "grpRecommendations";
            this.grpRecommendations.Size = new System.Drawing.Size(760, 150);
            this.grpRecommendations.TabIndex = 9;
            this.grpRecommendations.TabStop = false;
            this.grpRecommendations.Text = "Recommendations";
            this.grpRecommendations.ForeColor = System.Drawing.Color.White;
            this.grpRecommendations.Anchor = AnchorStyles.Bottom | AnchorStyles.Left | AnchorStyles.Right;

            // lstRecommendations
            this.lstRecommendations.FormattingEnabled = true;
            this.lstRecommendations.ItemHeight = 15;
            this.lstRecommendations.Location = new System.Drawing.Point(6, 22);
            this.lstRecommendations.Name = "lstRecommendations";
            this.lstRecommendations.Size = new System.Drawing.Size(740, 109);
            this.lstRecommendations.TabIndex = 10;
            this.lstRecommendations.BackColor = System.Drawing.Color.FromArgb(30, 30, 30);
            this.lstRecommendations.ForeColor = System.Drawing.Color.White;
            this.lstRecommendations.Dock = DockStyle.Fill;

            // LocalEventsForm
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 600);
            this.Controls.Add(this.grpRecommendations);
            this.Controls.Add(this.grpEvents);
            this.Controls.Add(this.grpSearch);
            this.Name = "LocalEventsForm";
            this.Text = "Local Events and Announcements";
            this.BackColor = System.Drawing.Color.FromArgb(20, 20, 20);
            this.ForeColor = System.Drawing.Color.White;
            this.WindowState = FormWindowState.Maximized;
            this.ResumeLayout(false);
        }

        #endregion
    }
}
