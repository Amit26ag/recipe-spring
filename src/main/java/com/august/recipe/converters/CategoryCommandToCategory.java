package com.august.recipe.converters;

import com.august.recipe.commands.CategoryCommand;
import com.august.recipe.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Nullable
    @Synchronized
    @Override
    public Category convert(CategoryCommand categoryCommand) {
        return Category.builder()
                .id(categoryCommand.getId())
                .description(categoryCommand.getDescription())
                .build();
    }
}
