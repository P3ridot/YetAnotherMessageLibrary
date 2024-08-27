package dev.peri.yetanothermessageslibrary.replace;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface StringReplaceable extends Replaceable {

    @NotNull String replace(@Nullable Locale locale, @NotNull String text);

    default @NotNull String replace(@NotNull String text) {
        return this.replace(null, text);
    }

}
