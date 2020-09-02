package com.august.recipe.converters;

import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    private final NotesToNotesCommand notesToNotesCommand;
    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand notesToNotesCommand,
                                 CategoryToCategoryCommand categoryToCategoryCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.notesToNotesCommand = notesToNotesCommand;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Nullable
    @Synchronized
    @Override
    public RecipeCommand convert(Recipe recipe) {
        return RecipeCommand.builder()
                .id(recipe.getId())
                .categories(
                        recipe.getCategories()
                                .stream()
                                .map(categoryToCategoryCommand::convert).collect(Collectors.toSet())
                )
                .description(recipe.getDescription())
                .cookTime(recipe.getCookTime())
                .prepTime(recipe.getPrepTime())
                .difficulty(recipe.getDifficulty())
                .directions(recipe.getDirections())
                .ingredients(
                        recipe.getIngredients()
                                .stream()
                                .map(ingredientToIngredientCommand::convert).collect(Collectors.toSet())
                )
                .servings(recipe.getServings())
                .source(recipe.getSource())
                .notes(notesToNotesCommand.convert(recipe.getNotes()))
                .url(recipe.getUrl())
                .name(recipe.getName())
                .build();
    }
}
