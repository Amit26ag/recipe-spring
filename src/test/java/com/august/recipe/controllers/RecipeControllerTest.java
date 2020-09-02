package com.august.recipe.controllers;

import com.august.recipe.exceptions.NotFoundException;
import com.august.recipe.model.Recipe;
import com.august.recipe.services.RecipeService;
import com.august.recipe.services.UnitOfMeasureService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    Model model;

    RecipeController recipeController;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService, unitOfMeasureService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
    }

    @Test
    void testController() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipes/list"));
    }

    @Test
    void recipes() {

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(new Recipe());

        when(recipeService.getRecipes()).thenReturn(recipeSet);

        String result = recipeController.recipes(model);
        Assert.assertEquals("recipes/list", result);
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

        Assert.assertEquals(argumentCaptor.getValue().size(), recipeSet.size());
    }

    @Test
    void testRecipeNotFound() throws Exception {
        when(recipeService.findRecipeById(anyLong())).thenThrow(NotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.view().name("404error"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));
    }

    @Test
    void testNumberFormatException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/show/1xx"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.view().name("404error"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("exception"));;
    }

}
