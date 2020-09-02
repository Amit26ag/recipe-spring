package com.august.recipe.commands;

import com.august.recipe.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesCommand {
    private Long id;
    private String recipeNotes;
}
