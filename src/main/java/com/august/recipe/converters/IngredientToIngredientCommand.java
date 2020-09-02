package com.august.recipe.converters;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.model.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Nullable
    @Synchronized
    @Override
    public IngredientCommand convert(Ingredient ingredient) {
        return IngredientCommand.builder()
                .id(ingredient.getId())
                .description(ingredient.getDescription())
                .unitOfMeasure(unitOfMeasureToUnitOfMeasureCommand.convert(ingredient.getUnitOfMeasure()))
                .amount(ingredient.getAmount())
                .build();
    }
}
