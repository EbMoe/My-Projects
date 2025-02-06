using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace MunicipalServicesApp
{
    public partial class ServiceRequestStatusForm : Form
    {
        // Advanced Data Structures for Service Request Management
        public AVLTree serviceRequestsTree; // Made public to be accessed from Report form
        public MinHeap serviceRequestsHeap; // Made public to be accessed from Report form
        private Graph dependenciesGraph;
        private MainMenuForm mainMenu;  // To hold reference to MainMenuForm

        public ServiceRequestStatusForm(MainMenuForm menu)
        {
            InitializeComponent();
            mainMenu = menu;  // Reference to existing MainMenuForm
            try
            {
                InitializeDataStructures();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"An error occurred while initializing: {ex.Message}");
            }
        }

        // Initialize data structures
        private void InitializeDataStructures()
        {
            try
            {
                serviceRequestsTree = new AVLTree();
                serviceRequestsHeap = new MinHeap();
                dependenciesGraph = new Graph();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error initializing data structures: {ex.Message}");
            }
        }

        // Display all service requests in the ListView
        private void DisplayRequests()
        {
            try
            {
                lstRequests.Items.Clear();
                List<ServiceRequest> requests = serviceRequestsTree.InOrderTraversal();

                foreach (var req in requests)
                {
                    ListViewItem item = new ListViewItem(req.RequestID.ToString());
                    item.SubItems.Add(req.ServiceType);
                    item.SubItems.Add(req.Status);
                    item.SubItems.Add(req.DateSubmitted.ToShortDateString());
                    item.SubItems.Add(req.Progress.ToString() + "%"); // Display progress
                    lstRequests.Items.Add(item);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error displaying requests: {ex.Message}");
            }
        }

        // Method to add a new service request from Report form
        public void AddNewServiceRequest(ServiceRequest request)
        {
            serviceRequestsTree.Insert(request);
            serviceRequestsHeap.Add(request);
            DisplayRequests();
        }

        // Search service request by ID
        private void btnSearch_Click(object sender, EventArgs e)
        {
            try
            {
                if (int.TryParse(txtSearch.Text, out int requestID))
                {
                    var request = serviceRequestsTree.Search(requestID);

                    if (request != null)
                    {
                        MessageBox.Show($"Request Found: {request.ServiceType} - {request.Status} (Submitted: {request.DateSubmitted.ToShortDateString()}) - Progress: {request.Progress}%");
                    }
                    else
                    {
                        MessageBox.Show("Service request not found.");
                    }
                }
                else
                {
                    MessageBox.Show("Please enter a valid request ID.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error searching for service request: {ex.Message}");
            }
        }

        // View request dependencies
        private void btnViewDependencies_Click(object sender, EventArgs e)
        {
            try
            {
                if (int.TryParse(txtSearch.Text, out int requestID))
                {
                    List<int> dependencies = dependenciesGraph.GetDependencies(requestID);

                    if (dependencies.Count > 0)
                    {
                        string depList = string.Join(", ", dependencies);
                        MessageBox.Show($"Request {requestID} has dependencies on the following request IDs: {depList}");
                    }
                    else
                    {
                        MessageBox.Show("No dependencies for this request.");
                    }
                }
                else
                {
                    MessageBox.Show("Please enter a valid request ID.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error viewing dependencies: {ex.Message}");
            }
        }

        // Show the oldest request using MinHeap
        private void btnShowOldestRequest_Click(object sender, EventArgs e)
        {
            try
            {
                var oldestRequest = serviceRequestsHeap.Peek(); // Retrieve the highest priority item (oldest request)

                if (oldestRequest != null)
                {
                    MessageBox.Show($"Oldest Request:\nID: {oldestRequest.RequestID}\nService: {oldestRequest.ServiceType}\nStatus: {oldestRequest.Status}\nDate Submitted: {oldestRequest.DateSubmitted.ToShortDateString()}");
                }
                else
                {
                    MessageBox.Show("No service requests available.");
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error retrieving the oldest request: {ex.Message}");
            }
        }

        // Display all requests in the MinHeap on the ListView in reverse order (most recent first)
        private void btnViewHeap_Click(object sender, EventArgs e)
        {
            try
            {
                lstRequests.Items.Clear(); // Clear the existing items in the ListView
                List<ServiceRequest> heapRequests = serviceRequestsHeap.GetHeap();

                // Sort the heapRequests by DateSubmitted in descending order (most recent first)
                heapRequests.Sort((x, y) => y.DateSubmitted.CompareTo(x.DateSubmitted));

                // Populate the ListView with sorted requests
                foreach (var req in heapRequests)
                {
                    ListViewItem item = new ListViewItem(req.RequestID.ToString());
                    item.SubItems.Add(req.ServiceType);
                    item.SubItems.Add(req.Status);
                    item.SubItems.Add(req.DateSubmitted.ToShortDateString());
                    lstRequests.Items.Add(item);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error displaying heap view: {ex.Message}");
            }
        }

        // Refresh the display to show all requests again
        private void btnRefresh_Click(object sender, EventArgs e)
        {
            try
            {
                txtSearch.Clear();
                DisplayRequests();
            }
            catch (Exception ex)
            {
                MessageBox.Show($"Error refreshing the display: {ex.Message}");
            }
        }

        // Back to Main Menu without creating duplicate
        private void btnBackToMainMenu_Click(object sender, EventArgs e)
        {
            this.Close();
            mainMenu.Show();  // Show the referenced existing MainMenuForm
        }
    }

    // Service Request Model
    public class ServiceRequest
    {
        public int RequestID { get; set; }
        public string ServiceType { get; set; }
        public string Status { get; set; }
        public DateTime DateSubmitted { get; set; }
        public int Progress { get; set; } // New property

        public ServiceRequest(int requestId, string serviceType, string status, DateTime dateSubmitted, int progress)
        {
            RequestID = requestId;
            ServiceType = serviceType;
            Status = status;
            DateSubmitted = dateSubmitted;
            Progress = progress;
        }
    }

    // AVL Tree Implementation for Service Requests
    public class AVLTree
    {
        private TreeNode root;

        public void Insert(ServiceRequest request)
        {
            root = Insert(root, request);
        }

        private TreeNode Insert(TreeNode node, ServiceRequest request)
        {
            if (node == null) return new TreeNode(request);
            if (request.RequestID < node.Request.RequestID) node.Left = Insert(node.Left, request);
            else if (request.RequestID > node.Request.RequestID) node.Right = Insert(node.Right, request);
            else return node;

            node.Height = 1 + Math.Max(Height(node.Left), Height(node.Right));
            return Balance(node);
        }

        private TreeNode Balance(TreeNode node)
        {
            int balance = BalanceFactor(node);
            if (balance > 1)
            {
                if (BalanceFactor(node.Left) < 0) node.Left = RotateLeft(node.Left);
                return RotateRight(node);
            }
            if (balance < -1)
            {
                if (BalanceFactor(node.Right) > 0) node.Right = RotateRight(node.Right);
                return RotateLeft(node);
            }
            return node;
        }

        private TreeNode RotateRight(TreeNode y)
        {
            TreeNode x = y.Left;
            y.Left = x.Right;
            x.Right = y;
            y.Height = Math.Max(Height(y.Left), Height(y.Right)) + 1;
            x.Height = Math.Max(Height(x.Left), Height(x.Right)) + 1;
            return x;
        }

        private TreeNode RotateLeft(TreeNode x)
        {
            TreeNode y = x.Right;
            x.Right = y.Left;
            y.Left = x;
            x.Height = Math.Max(Height(x.Left), Height(x.Right)) + 1;
            y.Height = Math.Max(Height(y.Left), Height(y.Right)) + 1;
            return y;
        }

        private int BalanceFactor(TreeNode node) => node == null ? 0 : Height(node.Left) - Height(node.Right);
        private int Height(TreeNode node) => node == null ? 0 : node.Height;

        public ServiceRequest Search(int requestID) => Search(root, requestID);

        private ServiceRequest Search(TreeNode node, int requestID)
        {
            if (node == null) return null;
            if (requestID < node.Request.RequestID) return Search(node.Left, requestID);
            if (requestID > node.Request.RequestID) return Search(node.Right, requestID);
            return node.Request;
        }

        public List<ServiceRequest> InOrderTraversal()
        {
            List<ServiceRequest> result = new List<ServiceRequest>();
            InOrderTraversal(root, result);
            return result;
        }

        private void InOrderTraversal(TreeNode node, List<ServiceRequest> result)
        {
            if (node == null) return;
            InOrderTraversal(node.Left, result);
            result.Add(node.Request);
            InOrderTraversal(node.Right, result);
        }

        private class TreeNode
        {
            public ServiceRequest Request { get; set; }
            public TreeNode Left { get; set; }
            public TreeNode Right { get; set; }
            public int Height { get; set; }
            public TreeNode(ServiceRequest request) => (Request, Left, Right, Height) = (request, null, null, 1);
        }
    }

    // Min-Heap Implementation for Prioritizing Service Requests by Date
    public class MinHeap
    {
        private List<ServiceRequest> heap = new List<ServiceRequest>();

        public void Add(ServiceRequest request)
        {
            heap.Add(request);
            HeapifyUp(heap.Count - 1);
        }

        public ServiceRequest Peek() => heap.Count > 0 ? heap[0] : null;
        public List<ServiceRequest> GetHeap() => new List<ServiceRequest>(heap);

        private void HeapifyUp(int index)
        {
            if (index == 0) return;
            int parentIndex = (index - 1) / 2;
            if (heap[index].DateSubmitted < heap[parentIndex].DateSubmitted)
            {
                Swap(index, parentIndex);
                HeapifyUp(parentIndex);
            }
        }

        private void Swap(int i, int j)
        {
            var temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    // Graph for Request Dependencies
    public class Graph
    {
        private Dictionary<int, List<int>> adjacencyList = new Dictionary<int, List<int>>();

        public void AddEdge(int from, int to)
        {
            if (!adjacencyList.ContainsKey(from))
                adjacencyList[from] = new List<int>();
            adjacencyList[from].Add(to);
        }

        public List<int> GetDependencies(int requestID) =>
            adjacencyList.ContainsKey(requestID) ? adjacencyList[requestID] : new List<int>();
    }
}
