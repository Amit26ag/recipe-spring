package com.august.recipe.services;

import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.converters.RecipeCommandToRecipe;
import com.august.recipe.converters.RecipeToRecipeCommand;
import com.august.recipe.model.Recipe;
import com.august.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceImplTestIT {

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    private final String DESCRIPTION = "A Description";


    @Test
    void saveRecipeCommand() {
        Set<Recipe> recipes = recipeService.getRecipes();
        Recipe testRecipe = recipes.iterator().next();

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);

        assert recipeCommand != null;
        recipeCommand.setDescription(DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        assertEquals(DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}