using System;
using System.Collections.Generic;
using System.Windows;
using System.Windows.Controls;

namespace ST10084812.PROG.POE
{
    public partial class AddRecipe : Window
    {
        internal Recipe recipe;
        public Recipe newRecipe;


        public AddRecipe()
        {
            InitializeComponent();
        }


        private Recipe SaveRecipe()
        {
            string recipeName = txtRecipeName.Text;

            // Get the ingredients and their calorie values from the dynamically generated TextBox controls
            List<Ingredient> ingredients = new List<Ingredient>();
            int totalCalories = 0;
            int objCount = 0;
            string ingredientName = "";
            int calories = 0;
            int qty = 0;
            double unit = 0;

            // Get the ingredientsa from the dynamically generatediInputs
            foreach (var child in stackIngredients.Children)
            {
                if (child is Grid childGrid)
                {
                    foreach (var grandChild in childGrid.Children)

                        if (grandChild is TextBox textBox)
                        {
                            objCount++;
                            if (textBox.Name.Contains("txtIngredient"))
                                ingredientName = textBox.Text;
                            else if (textBox.Name.Contains("txtCalories"))
                                calories = Convert.ToInt32(textBox.Text);
                            else if (textBox.Name.Contains("txtQty"))
                                qty = Convert.ToInt32(textBox.Text);
                        }
                        else if (grandChild is ComboBox comboBox)
                        {

                            objCount++;
                            if (comboBox.Name.Contains("cmbUnit"))
                                unit = Convert.ToDouble(comboBox.SelectedItem);
                        }
                }
                // Check that the number of Input objects = 4 before adding the ingredients to the list
                if (objCount>0 && objCount%4 == 0)
                {
                    Ingredient ingredient = new Ingredient(ingredientName, calories, unit, qty);
                    ingredients.Add(ingredient);
                    totalCalories += calories;
                    // Reset variables
                    ingredientName = "";
                    calories = 0;
                    qty = 0;
                    unit = 0;
                }
            }

            // Get the steps from the dynamically generated TextBox controls
            List<string> steps = new List<string>();
            foreach (var child in stackSteps.Children)
            {
                if (child is Grid childGrid)
                {
                    foreach (var grandChild in childGrid.Children)
                    {
                        if (grandChild is TextBox textBox)
                        {
                            steps.Add(textBox.Text);
                        }
                    }
                }
            }


            if (totalCalories > 300)
            {
                MessageBox.Show("Warning: Total calories exceed 300!", "Calorie Warning", MessageBoxButton.OK, MessageBoxImage.Warning);
            }

            recipe = new Recipe(recipeName, ingredients, steps);

            // Reset the form
            txtRecipeName.Text = "";
            txtIngredientsCount.Text = "";
            stackIngredients.Children.Clear();
            txtStepsCount.Text = "";
            stackSteps.Children.Clear();

            return recipe;
        }
        private void txtIngredientsCount_TextChanged(object sender, TextChangedEventArgs e)
        {
            // Generate Ingredient Inputs
            if (int.TryParse(txtIngredientsCount.Text, out int ingredientCount))
            {
                stackIngredients.Children.Clear();
                Grid items = new Grid();
                for (int i = 0; i < ingredientCount; i++)
                {
                    Grid ingredientGrid = new Grid();
                    ingredientGrid.Margin = new Thickness(0, 5, 0, 5);

                    ingredientGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(100) });
                    ingredientGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(200) });

                    TextBlock ingredientTextBlock = new TextBlock
                    {
                        Text = "Ingredient " + (i + 1) + ":",
                        VerticalAlignment = VerticalAlignment.Center
                    };
                    TextBox ingredientTextBox = new TextBox
                    {
                        Name = "txtIngredient" + (i + 1),
                        Width = 200
                    };

                    Grid.SetColumn(ingredientTextBlock, 0);
                    Grid.SetColumn(ingredientTextBox, 1);

                    ingredientGrid.Children.Add(ingredientTextBlock);
                    ingredientGrid.Children.Add(ingredientTextBox);

                    stackIngredients.Children.Add(ingredientGrid);

                    Grid qtyGrid = new Grid();
                    qtyGrid.Margin = new Thickness(0, 5, 0, 5);

                    qtyGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(100) });
                    qtyGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(200) });

                    TextBlock qtyTextBlock = new TextBlock
                    {
                        Text = "Qty " + (i + 1) + ":",
                        VerticalAlignment = VerticalAlignment.Center
                    };
                    TextBox qtyTextBox = new TextBox
                    {
                        Name = "txtQty" + (i + 1),
                        Width = 200
                    };

                    Grid.SetColumn(qtyTextBlock, 0);
                    Grid.SetColumn(qtyTextBox, 1);

                    qtyGrid.Children.Add(qtyTextBlock);
                    qtyGrid.Children.Add(qtyTextBox);

                    stackIngredients.Children.Add(qtyGrid);

                    Grid unitGrid = new Grid();
                    unitGrid.Margin = new Thickness(0, 5, 0, 5);

                    unitGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(100) });
                    unitGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(200) });

                    TextBlock unitTextBlock = new TextBlock
                    {
                        Text = "Unit " + (i + 1) + ":",
                        VerticalAlignment = VerticalAlignment.Center
                    };
                    ComboBox unitComboBox = new ComboBox
                    {
                        Name = "cmbUnit" + (i + 1),
                        Width = 100,
                        Margin = new Thickness(0, 0, 0, 5),
                        ItemsSource = new List<double> { 0.5, 2, 3 }
                    };

                    Grid.SetColumn(unitTextBlock, 0);
                    Grid.SetColumn(unitComboBox, 1);

                    unitGrid.Children.Add(unitTextBlock);
                    unitGrid.Children.Add(unitComboBox);

                    stackIngredients.Children.Add(unitGrid);

                    Grid calorieGrid = new Grid();
                    calorieGrid.Margin = new Thickness(0, 5, 0, 5);

                    calorieGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(100) });
                    calorieGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(200) });

                    TextBlock calorieTextBlock = new TextBlock
                    {
                        Text = "Calories " + (i + 1) + ":",
                        VerticalAlignment = VerticalAlignment.Center
                    };
                    TextBox calorieTextBox = new TextBox
                    {
                        Name = "txtCalories" + (i + 1),
                        Width = 200
                    };

                    Grid.SetColumn(calorieTextBlock, 0);
                    Grid.SetColumn(calorieTextBox, 1);

                    calorieGrid.Children.Add(calorieTextBlock);
                    calorieGrid.Children.Add(calorieTextBox);

                    stackIngredients.Children.Add(calorieGrid);
                }
            }
        }

        private void txtStepsCount_TextChanged(object sender, TextChangedEventArgs e)
        {
            // Generate Steps Inputsv
            if (int.TryParse(txtStepsCount.Text, out int stepCount))
            {
                stackSteps.Children.Clear();
                //Loop for number of steps inputted
                for (int i = 0; i < stepCount; i++)
                {
                    //Create Grid
                    Grid stepGrid = new Grid();
                    stepGrid.Margin = new Thickness(0, 5, 0, 5);

                    stepGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(100) });
                    stepGrid.ColumnDefinitions.Add(new ColumnDefinition() { Width = new GridLength(200) });
                    //Create TextBlock
                    TextBlock stepTextBlock = new TextBlock
                    {
                        Text = "Step " + (i + 1) + ":",
                        VerticalAlignment = VerticalAlignment.Center
                    };
                    // Create TextBox
                    TextBox stepTextBox = new TextBox
                    {
                        Name = "txtStep" + (i + 1),
                        Width = 200
                    };
                    //Set Column in grid to TextBlock and TextBox
                    Grid.SetColumn(stepTextBlock, 0);
                    Grid.SetColumn(stepTextBox, 1);
                    //Add to Grid
                    stepGrid.Children.Add(stepTextBlock);
                    stepGrid.Children.Add(stepTextBox);
                    //Add to steps Stack
                    stackSteps.Children.Add(stepGrid);
                }
            }
        }

        private void SaveRecipe_Click(object sender, RoutedEventArgs e)
        {
            if (string.IsNullOrEmpty(txtRecipeName.Text) || string.IsNullOrEmpty(txtIngredientsCount.Text) || string.IsNullOrEmpty(txtFoodGroup.Text) || string.IsNullOrEmpty(txtStepsCount.Text)) {
                MessageBox.Show("Please Fill in all fields", "Error", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            else
            {
                SaveRecipe();
                this.Close();
                MessageBox.Show("Recipe saved successfully!", "Recipe Saved", MessageBoxButton.OK, MessageBoxImage.Information);
            }
        }
    }


    public class Recipe
    {
        public string Name { get; set; }
        public List<Ingredient> Ingredients { get; set; }
        public List<string> Steps { get; set; }

        public Recipe(string name, List<Ingredient> ingredients, List<string> steps)
        {
            Name = name;
            Ingredients = ingredients;
            Steps = steps;
        }
    }

    public class Ingredient
    {
        public string Name { get; set; }
        public int Calories { get; set; }
        public double Unit { get; set; }
        public int Quantity { get; set; } 

        public Ingredient(string name, int calories, double unit, int quantity)
        {
            Name = name;
            Calories = calories;
            Unit = unit;
            Quantity = quantity;
        }

        public Ingredient(string ingredientName, int calorieValue)
        {
            Name = ingredientName;
            Calories = calorieValue;
            Unit = 0; // Set the default unit value to 0
            Quantity = 0; // Set the default quantity value to 0
        }
    }
}


