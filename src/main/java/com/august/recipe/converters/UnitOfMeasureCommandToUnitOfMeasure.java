package com.august.recipe.converters;

import com.august.recipe.commands.UnitOfMeasureCommand;
import com.august.recipe.model.UnitOfMeasure;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand unitOfMeasure) {
        log.info("ss");
        return UnitOfMeasure.builder()
                .id(unitOfMeasure.getId())
                .unitDescription(unitOfMeasure.getUnitDescription())
                .build();
    }
}
