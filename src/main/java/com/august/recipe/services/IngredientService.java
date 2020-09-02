package com.august.recipe.services;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.model.Ingredient;

public interface IngredientService {
    Ingredient getIngredientById(Long id);
    IngredientCommand getIngredientCommand(Long id);
    void deleteIngredient(Long id);
    IngredientCommand save(IngredientCommand ingredientCommand, Long recipeId);
}
