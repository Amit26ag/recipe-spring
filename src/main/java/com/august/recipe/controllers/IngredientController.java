package com.august.recipe.controllers;

import com.august.recipe.commands.IngredientCommand;
import com.august.recipe.model.Ingredient;
import com.august.recipe.services.IngredientService;
import com.august.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IngredientController {

    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @PostMapping("/add/{recipeId}/ingredient")
    public String addIngredient(@ModelAttribute IngredientCommand ingredientCommand, @PathVariable String recipeId) {
        ingredientService.save(ingredientCommand, Long.valueOf(recipeId));
        return "redirect:/recipe/update/" + recipeId;
    }

    @GetMapping("/delete/{recipeId}/ingredient/{ingredientId}")
    public String deleteIngredient(@PathVariable("ingredientId") String id, @PathVariable("recipeId") String recipeId) {
        ingredientService.deleteIngredient(Long.valueOf(id));
        return "redirect:/recipe/update/" + recipeId;
    }

    @GetMapping("update/{recipeId}/ingredient/{ingredientId}")
    public String updateIngredientView(@PathVariable("ingredientId") String ingredientId, @PathVariable String recipeId, Model model) {
        IngredientCommand ingredientCommand = ingredientService.getIngredientCommand(Long.valueOf(ingredientId));
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("units", unitOfMeasureService.getAllUnitsOfMeasure());
        model.addAttribute("recipeId", recipeId);
        return "ingredient/updateIngredient";
    }

    @PostMapping("/update/{recipeId}/ingredient")
    public String updateIngredient(@ModelAttribute IngredientCommand ingredientCommand, @PathVariable String recipeId) {
        ingredientService.save(ingredientCommand, Long.valueOf(recipeId));
        return "redirect:/recipe/update/" + recipeId;
    }

}
