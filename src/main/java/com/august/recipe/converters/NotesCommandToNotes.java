package com.august.recipe.converters;

import com.august.recipe.commands.NotesCommand;
import com.august.recipe.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Nullable
    @Synchronized
    @Override
    public Notes convert(NotesCommand notesCommand) {
        return Notes.builder()
                .id(notesCommand.getId())
                .recipeNotes(notesCommand.getRecipeNotes())
                .build();
    }
}
