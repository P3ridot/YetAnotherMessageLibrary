package org.mythicprojects.yetanothermessageslibrary.replace;

import java.util.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Replaceable {

    default boolean supportsStringReplacement() {
        return false;
    }

    @NotNull Component replace(@Nullable Locale locale, @NotNull Component text);

    default @NotNull Component replace(@NotNull Component text) {
        return this.replace(null, text);
    }

    default @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        throw new UnsupportedOperationException("This replaceable does not support string replacement");
    }

    default @NotNull String replace(@NotNull String text) {
        return this.replace(null, text);
    }

}
