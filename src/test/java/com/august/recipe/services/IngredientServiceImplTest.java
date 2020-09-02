package com.august.recipe.services;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.commands.UnitOfMeasureCommand;
import com.august.recipe.converters.IngredientCommandToIngredient;
import com.august.recipe.converters.IngredientToIngredientCommand;
import com.august.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.august.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.august.recipe.model.Ingredient;
import com.august.recipe.model.Recipe;
import com.august.recipe.model.UnitOfMeasure;
import com.august.recipe.repositories.IngredientRepository;
import com.august.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class IngredientServiceImplTest {

    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private IngredientToIngredientCommand ingredientToIngredientCommand;

    IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientService = new IngredientServiceImpl(ingredientRepository,
                                                      recipeRepository,
                                                      unitOfMeasureService,
                                                      ingredientCommandToIngredient,
                                                      ingredientToIngredientCommand);
    }

    @Test
    void getIngredientById() {

        //given
        Optional<Ingredient> ingredient = Optional.of(Ingredient.builder().id(1L).amount(new BigDecimal("2.0")).description("Eggs").build());
        when(ingredientRepository.findById(anyLong())).thenReturn(ingredient);

        //when
        Ingredient ingredientTest = ingredientService.getIngredientById(1L);

        //then
        assertEquals(Long.valueOf(1L), ingredientTest.getId());
        assertEquals(new String("Eggs"), ingredientTest.getDescription());
        verify(ingredientRepository, times(1)).findById(anyLong());

    }

    @Test
    void save() {
        Recipe recipe = Recipe.builder().id(1L).build();
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().id(1L).build();
        IngredientCommand ingredientCommand = IngredientCommand.builder().id(1L).description("Eggs").build();
        ingredientCommand.setUnitOfMeasure(UnitOfMeasureCommand.builder().id(unitOfMeasure.getId()).build());

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
        when(unitOfMeasureService.getById(anyLong())).thenReturn(unitOfMeasure);
        when(ingredientRepository.save(any())).thenReturn(ingredientCommandToIngredient.convert(ingredientCommand));

        IngredientCommand savedIngredient = ingredientService.save(ingredientCommand, 1L);

        assertEquals(savedIngredient.getId(), ingredientCommand.getId());
        assertEquals(savedIngredient.getUnitOfMeasure().getId(), unitOfMeasure.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void deleteIngredient() {
    }
}