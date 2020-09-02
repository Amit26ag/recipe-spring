package com.august.recipe.bootstrap;

import com.august.recipe.model.*;
import com.august.recipe.repositories.CategoryRepository;
import com.august.recipe.repositories.RecipeRepository;
import com.august.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Slf4j
@Component
public class BootstrapData implements CommandLineRunner {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public BootstrapData(UnitOfMeasureRepository unitOfMeasureRepository,
                         CategoryRepository categoryRepository,
                         RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        UnitOfMeasure tableSpoon = unitOfMeasureRepository.findByUnitDescription("Tablespoon").get();
        UnitOfMeasure unit = unitOfMeasureRepository.findByUnitDescription("Unit").get();

        Ingredient salt = new Ingredient();
        salt.setDescription("Salt");
        salt.setUnitOfMeasure(tableSpoon);
        salt.setAmount(new BigDecimal("1.0"));

        Ingredient avocados = new Ingredient();
        avocados.setAmount(new BigDecimal("2.0"));
        avocados.setDescription("Avocados");
        avocados.setUnitOfMeasure(unit);


        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setName("Perfect Guacamole");
        perfectGuacamole.setCookTime(20);
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setServings(2);
        perfectGuacamole.setDescription("This is a really delicious dish!");
        perfectGuacamole.setDirections("Gently tap the pit with your chef’s knife so the knife gets wedged into the pit." +
                " Twist your knife slightly to dislodge the pit and lift to remove. If you use this method, first protect your" +
                " hand with a thick kitchen towel before proceeding.");
        perfectGuacamole.addIngredient(salt);
        perfectGuacamole.addIngredient(avocados);

        Notes notes = new Notes();
        notes.setRecipeNotes("Nothing big, keep it cool!");

        perfectGuacamole.setNotes(notes);
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDifficulty(Difficulty.EASY);

        Category american = categoryRepository.findByDescription("American").get();
        perfectGuacamole.getCategories().add(american);

        recipeRepository.save(perfectGuacamole);

        for (Recipe recipe : recipeRepository.findAll()) {
            log.info(String.valueOf(recipe.getId()));
            log.info(recipe.getDifficulty().name());
//            for (Ingredient ingredient: recipe.getIngredients()) System.out.println(ingredient.getDescription());
        }
    }

}
