package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Sendable {

    void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements);

    default void send(@NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        this.send(null, viewer, replacements);
    }

}
