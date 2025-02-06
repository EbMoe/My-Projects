using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    public partial class LocalEventsForm : Form
    {
        // Data Structures
        private SortedDictionary<DateTime, List<Event>> eventsDictionary;
        private HashSet<string> categoriesSet;
        private Queue<Event> eventQueue;
        private Stack<Event> eventStack;
        private List<string> userSearchHistory; 
        private Dictionary<string, int> searchFrequency; 

        public LocalEventsForm()
        {
            InitializeComponent();
            InitializeDataStructures();
            LoadSampleEvents();
            btnShowRecent.Click += btnShowRecent_Click;
            btnProcessNext.Click += btnProcessNext_Click;
            btnReset.Click += btnReset_Click;
            btnBackToMainMenu.Click += btnBackToMainMenu_Click;
            btnDateSearch.Click += btnDateSearch_Click; 
        }

        // Initialize all required data structures
        private void InitializeDataStructures()
        {
            eventsDictionary = new SortedDictionary<DateTime, List<Event>>();
            categoriesSet = new HashSet<string>();
            eventQueue = new Queue<Event>();
            eventStack = new Stack<Event>();
            userSearchHistory = new List<string>();
            searchFrequency = new Dictionary<string, int>(); 
        }


        private void LoadSampleEvents()
        {
            // Environment events
            AddEvent(new Event("Community Cleanup", new DateTime(2024, 9, 28), "Environment"));
            AddEvent(new Event("Tree Planting Day", new DateTime(2024, 10, 18), "Environment"));
            AddEvent(new Event("Beach Cleanup", new DateTime(2024, 11, 2), "Environment"));
            AddEvent(new Event("Recycling Workshop", new DateTime(2024, 11, 20), "Environment"));
            AddEvent(new Event("Eco-Friendly Products Expo", new DateTime(2024, 12, 1), "Environment"));

            // Local events
            AddEvent(new Event("Local Market Fair", new DateTime(2024, 10, 5), "Local"));
            AddEvent(new Event("Farmers Market", new DateTime(2024, 10, 12), "Local"));
            AddEvent(new Event("Craft Show", new DateTime(2024, 11, 10), "Local"));
            AddEvent(new Event("Food Truck Festival", new DateTime(2024, 11, 18), "Local"));
            AddEvent(new Event("Community Potluck", new DateTime(2024, 12, 2), "Local"));

            // Government events
            AddEvent(new Event("Town Hall Meeting", new DateTime(2024, 10, 15), "Government"));
            AddEvent(new Event("Budget Meeting", new DateTime(2024, 10, 30), "Government"));
            AddEvent(new Event("City Planning Meeting", new DateTime(2024, 11, 12), "Government"));
            AddEvent(new Event("Public Safety Forum", new DateTime(2024, 11, 28), "Government"));
            AddEvent(new Event("Annual Report Review", new DateTime(2024, 12, 5), "Government"));

            // Sports events
            AddEvent(new Event("Charity Run", new DateTime(2024, 11, 7), "Sports"));
            AddEvent(new Event("Youth Soccer Tournament", new DateTime(2024, 11, 15), "Sports"));
            AddEvent(new Event("Rugby League Finals", new DateTime(2024, 11, 23), "Sports"));
            AddEvent(new Event("Community Gyming", new DateTime(2024, 12, 10), "Sports"));
            AddEvent(new Event("Swimming Gala", new DateTime(2024, 12, 20), "Sports"));

            // Music events
            AddEvent(new Event("Music Concert", new DateTime(2024, 11, 15), "Music"));
            AddEvent(new Event("Jazz Night", new DateTime(2024, 11, 25), "Music"));
            AddEvent(new Event("Choir Festival", new DateTime(2024, 12, 5), "Music"));
            AddEvent(new Event("Rock Band Battle", new DateTime(2024, 12, 12), "Music"));
            AddEvent(new Event("Piano Recital", new DateTime(2024, 12, 18), "Music"));

            // Charity events
            AddEvent(new Event("Food Drive", new DateTime(2024, 12, 12), "Charity"));
            AddEvent(new Event("Charity Gala", new DateTime(2024, 11, 22), "Charity"));
            AddEvent(new Event("Blood Donation", new DateTime(2024, 11, 29), "Charity"));
            AddEvent(new Event("Clothing Donation Drive", new DateTime(2024, 12, 6), "Charity"));
            AddEvent(new Event("Orphanage Fundraiser", new DateTime(2024, 12, 16), "Charity"));

            // Education events
            AddEvent(new Event("Coding Workshop", new DateTime(2024, 11, 25), "Education"));
            AddEvent(new Event("Educational Fair", new DateTime(2024, 11, 19), "Education"));
            AddEvent(new Event("Science Exhibition", new DateTime(2024, 12, 3), "Education"));
            AddEvent(new Event("Career Counseling", new DateTime(2024, 12, 8), "Education"));
            AddEvent(new Event("Book Reading Event", new DateTime(2024, 12, 14), "Education"));

            DisplayEvents(); 
        }


        // Add a new event to the data structures
        private void AddEvent(Event newEvent)
        {
            if (!eventsDictionary.ContainsKey(newEvent.Date))
            {
                eventsDictionary[newEvent.Date] = new List<Event>();
            }
            eventsDictionary[newEvent.Date].Add(newEvent);
            categoriesSet.Add(newEvent.Category);

            eventQueue.Enqueue(newEvent);
            eventStack.Push(newEvent);
        }

        // Display events in the ListView
        private void DisplayEvents()
        {
            lstEvents.Items.Clear();

            foreach (var date in eventsDictionary.Keys)
            {
                foreach (var ev in eventsDictionary[date])
                {
                    var item = new ListViewItem(ev.Date.ToShortDateString())
                    {
                        SubItems = { ev.Name, ev.Category }
                    };
                    lstEvents.Items.Add(item);
                }
            }
        }

        // Date Search functionality - Only filter events by date
        private void btnDateSearch_Click(object sender, EventArgs e)
        {
            DateTime selectedDate = dtpDateFilter.Value.Date; 

            // Filter events by the selected date
            var filteredEvents = eventsDictionary
                .Where(kv => kv.Key.Date == selectedDate)
                .SelectMany(kv => kv.Value);

            // Display filtered events by date
            lstEvents.Items.Clear();
            foreach (var ev in filteredEvents)
            {
                var item = new ListViewItem(ev.Date.ToShortDateString())
                {
                    SubItems = { ev.Name, ev.Category }
                };
                lstEvents.Items.Add(item);
            }

            if (lstEvents.Items.Count == 0)
            {
                MessageBox.Show($"No events found for {selectedDate.ToShortDateString()}.");
            }
        }

        // Reset button functionality
        private void btnReset_Click(object sender, EventArgs e)
        {
            // Reset filters, search, and reload the original list of events
            txtSearch.Text = string.Empty;
            cmbCategory.SelectedIndex = -1;
            dtpDateFilter.Value = DateTime.Now;
            DisplayEvents();
        }

        // Generate recommendations based on user search patterns
        private void GenerateRecommendations()
        {
            lstRecommendations.Items.Clear();

            if (userSearchHistory.Count == 0)
            {
                lstRecommendations.Items.Add("No recommendations available. Start searching!");
                return;
            }

            // Get the most frequent search terms
            string mostFrequentSearch = searchFrequency
                .OrderByDescending(kv => kv.Value)
                .FirstOrDefault().Key;

            var recommendedEvents = eventsDictionary.Values
                .SelectMany(list => list)
                .Where(ev => ev.Name.ToLower().Contains(mostFrequentSearch.ToLower()) 
                             || ev.Category.ToLower().Contains(mostFrequentSearch.ToLower())) 
                .Distinct();

            foreach (var recEvent in recommendedEvents)
            {
                lstRecommendations.Items.Add($"{recEvent.Name} - {recEvent.Date.ToShortDateString()} [{recEvent.Category}]");
            }

            if (lstRecommendations.Items.Count == 0)
            {
                lstRecommendations.Items.Add("No recommendations available based on your search.");
            }
        }

        // Algorithm-based search 
        private void btnSearch_Click(object sender, EventArgs e)
        {
            string searchText = txtSearch.Text.Trim().ToLower(); 

            // Store search pattern into search history
            if (!string.IsNullOrEmpty(searchText))
            {
                userSearchHistory.Add(searchText);
                if (searchFrequency.ContainsKey(searchText))
                {
                    searchFrequency[searchText]++;
                }
                else
                {
                    searchFrequency.Add(searchText, 1);
                }
            }

            // Filter events by search text 
            var filteredEvents = eventsDictionary
                .SelectMany(kv => kv.Value)
                .Where(ev => ev.Name.ToLower().Contains(searchText) 
                          || ev.Category.ToLower().Contains(searchText));

            // Display filtered results by text
            lstEvents.Items.Clear();
            foreach (var ev in filteredEvents)
            {
                var item = new ListViewItem(ev.Date.ToShortDateString())
                {
                    SubItems = { ev.Name, ev.Category }
                };
                lstEvents.Items.Add(item);
            }

            if (lstEvents.Items.Count == 0)
            {
                MessageBox.Show($"No events found for search term '{searchText}'.");
            }

            // Update recommendations based on search history
            GenerateRecommendations();
        }

        // Button to show the most recent event
        private void btnShowRecent_Click(object sender, EventArgs e)
        {
            if (eventStack.Count > 0)
            {
                var recentEvent = eventStack.Peek();
                MessageBox.Show($"Recent Event: {recentEvent.Name} on {recentEvent.Date.ToShortDateString()} [{recentEvent.Category}]");
            }
            else
            {
                MessageBox.Show("No recent events available.");
            }
        }

        // Button to process the next event 
        private void btnProcessNext_Click(object sender, EventArgs e)
        {
            if (eventQueue.Count > 0)
            {
                var nextEvent = eventQueue.Dequeue();
                MessageBox.Show($"Processing Event: {nextEvent.Name} on {nextEvent.Date.ToShortDateString()} [{nextEvent.Category}]");
                DisplayEvents();
            }
            else
            {
                MessageBox.Show("No events to process.");
            }
        }

        // Button to go back to the main menu
        private void btnBackToMainMenu_Click(object sender, EventArgs e)
        {
            this.Close();
            MainMenuForm mainMenu = new MainMenuForm();
        }
    }

    // Event class to represent individual events
    public class Event
    {
        public string Name { get; set; }
        public DateTime Date { get; set; }
        public string Category { get; set; }

        public Event(string name, DateTime date, string category)
        {
            Name = name;
            Date = date;
            Category = category;
        }
    }
}
