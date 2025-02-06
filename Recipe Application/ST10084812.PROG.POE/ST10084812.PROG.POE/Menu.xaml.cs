using System.Collections.Generic;
using System.Linq;
using System.Windows;

namespace ST10084812.PROG.POE
{
    public partial class Menu : Window
    {
        public static List<Recipe> recipeList = new List<Recipe>(); // A list to store the recipes

        public Menu()
        {
            InitializeComponent();
        }

        private void AddRecipe_Click(object sender, RoutedEventArgs e)
        {
            // Handle Add Recipe option
            AddRecipe addRecipeWindow = new AddRecipe();
            addRecipeWindow.ShowDialog();
            Recipe newRecipe = addRecipeWindow.recipe; // Access the recipe directly
            if (newRecipe != null) {
                recipeList.Add(newRecipe);
            }
        }

        private void DisplayRecipes_Click(object sender, RoutedEventArgs e)
        {
            DisplayRecipes();

        }

        private void ScaleRecipe_Click(object sender, RoutedEventArgs e)
        {
            
        }

        private void ResetQuantities_Click(object sender, RoutedEventArgs e)
        {
       
        }

        private void ClearData_Click(object sender, RoutedEventArgs e)
        {
            // Handle Clear Data option
            recipeList.Clear(); // Clear all recipes
            RecipeRichTextBox.Document.Blocks.Clear();
            MessageBox.Show("All data cleared.", "Clear Data", MessageBoxButton.OK, MessageBoxImage.Information);
        }

        private void Exit_Click(object sender, RoutedEventArgs e)
        {
            // Handle Exit option
            Close(); // Close the application
        }

        private void DisplayRecipes()
        {
            RecipeRichTextBox.Document.Blocks.Clear();
            if (recipeList.Count == 0)
            {
                RecipeRichTextBox.AppendText("No recipes found.");
            }
            else
            {
                recipeList = recipeList.OrderBy(r => r.Name).ToList(); // Sort recipes alphabetically
                foreach (Recipe recipe in recipeList)
                {
                    int calories = 0;
                    RecipeRichTextBox.AppendText($"Recipe: {recipe.Name}");
                    RecipeRichTextBox.AppendText("\nIngredients:\n");
                    foreach (Ingredient ingredient in recipe.Ingredients)
                    {
                        RecipeRichTextBox.AppendText($"- Ingredient Name: {ingredient.Name}  Qty: {ingredient.Quantity} Unit: {ingredient.Unit}\n");
                        calories = calories + ingredient.Calories;
                    }
                    RecipeRichTextBox.AppendText($"Total Calories: {calories}\n");
                    int stepNumber = 1;
                    foreach (var step in recipe.Steps)//for each step show
                    {
                        RecipeRichTextBox.AppendText($"Step {stepNumber}: {step.ToString()}\n"); stepNumber++;
                    }
                }
            }
        }

    }
}




