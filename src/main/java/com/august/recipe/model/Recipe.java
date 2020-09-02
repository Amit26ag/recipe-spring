package com.august.recipe.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    Byte[] image = null;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe", fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        notes.setRecipe(this);
        this.notes = notes;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredient.setRecipe(this);
        ingredients.add(ingredient);
    }

    public void addCategory(Category category) {
        category.addRecipe(this);
        categories.add(category);
    }

}
