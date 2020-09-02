package com.august.recipe.controllers;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.commands.RecipeCommand;
import com.august.recipe.model.Difficulty;
import com.august.recipe.services.RecipeService;
import com.august.recipe.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller()
public class RecipeController {

    private final RecipeService recipeService;
    private final UnitOfMeasureService unitOfMeasureService;

    public RecipeController(RecipeService recipeService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("recipe")
    public String recipes(Model model) {
        model.addAttribute("recipes", recipeService.getRecipes());
        return "recipes/list";
    }

    @GetMapping
    @RequestMapping("/recipe/show/{id}")
    public String recipeInfo(Model model, @PathVariable("id") String id) {
        model.addAttribute("recipe", recipeService.findRecipeById(Long.parseLong(id)));
        return "recipes/recipe";
    }

    @GetMapping
    @RequestMapping("recipe/new")
    public String recipeNew(Model model) {
        model.addAttribute("recipe", RecipeCommand.builder().build());
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("units", unitOfMeasureService.getAllUnitsOfMeasure());
        System.out.println(unitOfMeasureService.getAllUnitsOfMeasure());
        return "recipes/recipeForm";
    }

    @PostMapping("/recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/show/" + savedRecipeCommand.getId();
    }

    @GetMapping
    @RequestMapping("/recipe/update/{id}")
    public String updateRecipe(@PathVariable("id") String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.valueOf(id)));
        model.addAttribute("ingredient", new IngredientCommand());
        model.addAttribute("units", unitOfMeasureService.getAllUnitsOfMeasure());
        System.out.println(unitOfMeasureService.getAllUnitsOfMeasure());
        return "recipes/recipeForm";
    }

    @GetMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable("id") String id) {
        recipeService.deleteRecipeById(Long.valueOf(id));
        return "redirect:/";
    }
}
