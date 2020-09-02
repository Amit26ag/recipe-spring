package com.august.recipe.repositories;

import com.august.recipe.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
//    @DirtiesContext
    void findByUnitDescription() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUnitDescription("Teaspoon");
        assert unitOfMeasure.orElse(null) != null;
        assertEquals(unitOfMeasure.orElse(null).getUnitDescription(), "Teaspoon");
    }

    @Test
    void findByUnitDescriptionForCup() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUnitDescription("Cup");
        assert unitOfMeasure.orElse(null) != null;
        assertEquals(unitOfMeasure.orElse(null).getUnitDescription(), "Cup");
    }
}