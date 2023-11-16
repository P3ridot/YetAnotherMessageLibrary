package org.mythicprojects.yetanothermessageslibrary.replace.replacement;

import java.util.Locale;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ComponentReplacement extends Replacement {

    protected ComponentReplacement(@NotNull Object placeholder) {
        super(placeholder);
    }

    public abstract @NotNull TextReplacementConfig getReplacement(@Nullable Locale locale);

    @Override
    public @NotNull Component replace(@Nullable Locale locale, @NotNull Component text) {
        return text.replaceText(this.getReplacement(locale));
    }

}
