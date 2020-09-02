package com.august.recipe.services;

import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.converters.RecipeCommandToRecipe;
import com.august.recipe.converters.RecipeToRecipeCommand;
import com.august.recipe.exceptions.NotFoundException;
import com.august.recipe.model.Recipe;
import com.august.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeToRecipeCommand recipeToRecipeCommand,
                             RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false).collect(Collectors.toSet());
    }

    @Override
    public Recipe findRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (!recipeOptional.isPresent()) {
            throw new NotFoundException("Recipe not found! Could not find recipe for ID " + id.toString());
        }
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        assert recipe != null;
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findRecipeCommandById(Long id) {
        return recipeToRecipeCommand.convert(recipeRepository.findById(id).orElse(new Recipe()));
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}
