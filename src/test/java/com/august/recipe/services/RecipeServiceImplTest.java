package com.august.recipe.services;

import com.august.recipe.converters.RecipeCommandToRecipe;
import com.august.recipe.converters.RecipeToRecipeCommand;
import com.august.recipe.exceptions.NotFoundException;
import com.august.recipe.model.Recipe;
import com.august.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

class RecipeServiceImplTest {

  RecipeService recipeService;

  @Mock
  RecipeRepository recipeRepository;

  @Mock
  private RecipeToRecipeCommand recipeToRecipeCommand;
  @Mock
  private RecipeCommandToRecipe recipeCommandToRecipe;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCommand, recipeCommandToRecipe);
  }

  @Test
  void getRecipes() {
    Recipe recipe = new Recipe();
    HashSet recipeData = new HashSet();
    recipeData.add(recipe);

    when(recipeRepository.findAll()).thenReturn(recipeData);
    Set recipes = recipeService.getRecipes();
    recipes = recipeService.getRecipes();

    assertEquals(recipeData.size(), 1);
    verify(recipeRepository, times(2)).findAll();
  }

  @Test
  void findRecipeByIdException() {
    Optional<Recipe> optionalRecipe = Optional.empty();
    when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipe);
    assertThrows(NotFoundException.class, () -> {
      recipeService.findRecipeById(anyLong());
    });
  }
}