package com.august.recipe.converters;

import com.august.recipe.commands.UnitOfMeasureCommand;
import com.august.recipe.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {
        return UnitOfMeasureCommand.builder()
                .id(unitOfMeasure.getId())
                .unitDescription(unitOfMeasure.getUnitDescription())
                .build();
    }
}
