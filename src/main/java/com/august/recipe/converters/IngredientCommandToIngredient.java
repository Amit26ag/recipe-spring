package com.august.recipe.converters;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Nullable
    @Synchronized
    @Override
    public Ingredient convert(IngredientCommand ingredient) {
        return Ingredient.builder()
                .id(ingredient.getId())
                .amount(ingredient.getAmount())
                .unitOfMeasure(unitOfMeasureCommandToUnitOfMeasure.convert(ingredient.getUnitOfMeasure()))
                .description(ingredient.getDescription())
                .build();
    }
}
