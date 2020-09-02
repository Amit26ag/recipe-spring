package com.august.recipe.converters;

import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.model.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryCommandToCategory,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Nullable
    @Synchronized
    @Override
    public Recipe convert(RecipeCommand recipeCommand) {
        return Recipe.builder()
                .id(recipeCommand.getId())
                .categories(
                        recipeCommand.getCategories()
                                .stream()
                                .map(categoryCommandToCategory::convert).collect(Collectors.toSet())
                )
                .description(recipeCommand.getDescription())
                .cookTime(recipeCommand.getCookTime())
                .prepTime(recipeCommand.getPrepTime())
                .difficulty(recipeCommand.getDifficulty())
                .directions(recipeCommand.getDirections())
                .ingredients(
                        recipeCommand.getIngredients()
                                .stream()
                                .map(ingredientCommandToIngredient::convert).collect(Collectors.toSet())
                )
                .servings(recipeCommand.getServings())
                .source(recipeCommand.getSource())
                .notes(notesCommandToNotes.convert(recipeCommand.getNotes()))
                .url(recipeCommand.getUrl())
                .name(recipeCommand.getName())
                .build();
    }
}
