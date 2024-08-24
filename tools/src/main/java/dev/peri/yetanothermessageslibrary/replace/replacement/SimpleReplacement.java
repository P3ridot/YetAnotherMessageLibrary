package dev.peri.yetanothermessageslibrary.replace.replacement;

import java.util.Locale;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleReplacement extends ComponentReplacement {

    private final TextReplacementConfig replacement;

    protected SimpleReplacement(@NotNull Object placeholder, @NotNull ComponentLike replacement) {
        super(placeholder);
        this.replacement = this.newReplacementBuilder()
                .replacement(replacement)
                .build();
    }

    @Override
    public @NotNull TextReplacementConfig getReplacement(@Nullable Locale locale) {
        return this.replacement;
    }

}
