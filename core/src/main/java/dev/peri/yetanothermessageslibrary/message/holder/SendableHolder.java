package dev.peri.yetanothermessageslibrary.message.holder;

import dev.peri.yetanothermessageslibrary.message.Sendable;
import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class SendableHolder implements Sendable {

    /**
     * Creates copy of holder applying replacements to content
     *
     * @param replacements replacements to replace
     * @return copy of this holder with applied replacements
     * @throws UnsupportedOperationException if this holder does not support copying
     */
    @Contract(pure = true)
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        throw new UnsupportedOperationException("This holder does not support copying");
    }

}
