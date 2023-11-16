package org.mythicprojects.yetanothermessageslibrary.replace.replacement;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;

public class SimpleStringReplacement extends SimpleReplacement {

    private final String replacement;

    protected SimpleStringReplacement(@NotNull Object placeholder, @NotNull String replacement) {
        super(placeholder, GlobalAdventureSerializer.deserialize(replacement));
        this.replacement = replacement;
    }

    @Override
    public boolean supportsStringReplacement() {
        return true;
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.getPlaceholderType().replace(text, this.getPlaceholder(), this.replacement);
    }

}
