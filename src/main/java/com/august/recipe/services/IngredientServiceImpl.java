package com.august.recipe.services;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.converters.IngredientCommandToIngredient;
import com.august.recipe.converters.IngredientToIngredientCommand;
import com.august.recipe.model.Ingredient;
import com.august.recipe.repositories.IngredientRepository;
import com.august.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureService unitOfMeasureService;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 UnitOfMeasureService unitOfMeasureService,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 IngredientToIngredientCommand ingredientToIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
    }

    @Override
    public Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id).orElse(null);
    }

    @Override
    public IngredientCommand getIngredientCommand(Long id) {
        return ingredientToIngredientCommand.convert(getIngredientById(id));
    }

    @Transactional
    @Override
    public IngredientCommand save(IngredientCommand ingredientCommand, Long recipeId) {
        Ingredient toSave = ingredientCommandToIngredient.convert(ingredientCommand);
        assert toSave != null;
        toSave.setUnitOfMeasure(unitOfMeasureService.getById(toSave.getUnitOfMeasure().getId()));
        toSave.setRecipe(recipeRepository.findById(recipeId).orElse(null));
        Ingredient saved = ingredientRepository.save(toSave);
        return ingredientToIngredientCommand.convert(saved);
    }

    @Override
    public void deleteIngredient(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        assert ingredient != null;
        ingredient.setRecipe(null)             ;
        ingredientRepository.deleteById(id);
        log.info("Deleted ingredient!");
    }

}
