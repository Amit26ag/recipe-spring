package com.august.recipe.services;

import com.august.recipe.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasure> getAllUnitsOfMeasure();
    UnitOfMeasure getById(Long id);
}
