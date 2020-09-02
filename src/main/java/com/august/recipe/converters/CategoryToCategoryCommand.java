package com.august.recipe.converters;

import com.august.recipe.commands.CategoryCommand;
import com.august.recipe.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Nullable
    @Synchronized
    @Override
    public CategoryCommand convert(Category category) {
        return CategoryCommand.builder()
                .id(category.getId())
                .description(category.getDescription())
                .build();
    }
}
