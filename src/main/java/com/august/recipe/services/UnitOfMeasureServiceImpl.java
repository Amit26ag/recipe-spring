package com.august.recipe.services;

import com.august.recipe.model.UnitOfMeasure;
import com.august.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasure> getAllUnitsOfMeasure() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public UnitOfMeasure getById(Long id) {
        return unitOfMeasureRepository.findById(id).orElse(null);
    }
}
