package com.august.recipe.converters;

import com.august.recipe.commands.NotesCommand;
import com.august.recipe.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Nullable
    @Synchronized
    @Override
    public NotesCommand convert(Notes notes) {
        return NotesCommand.builder()
                .id(notes.getId())
                .recipeNotes(notes.getRecipeNotes())
                .build();
    }
}
