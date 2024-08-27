package dev.peri.yetanothermessageslibrary.replace.replacement;

import dev.peri.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import dev.peri.yetanothermessageslibrary.replace.StringReplaceable;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleStringReplacement extends SimpleReplacement implements StringReplaceable {

    private final String replacement;

    protected SimpleStringReplacement(@NotNull Object placeholder, @NotNull String replacement) {
        super(placeholder, GlobalAdventureSerializer.deserialize(replacement));
        this.replacement = replacement;
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.getPlaceholderType().replace(text, this.getPlaceholder(), this.replacement);
    }

}
