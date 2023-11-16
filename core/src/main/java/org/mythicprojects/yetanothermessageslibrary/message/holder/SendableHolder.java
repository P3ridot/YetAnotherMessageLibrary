package org.mythicprojects.yetanothermessageslibrary.message.holder;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.Sendable;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;

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
