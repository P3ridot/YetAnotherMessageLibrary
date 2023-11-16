package org.mythicprojects.yetanothermessageslibrary.replace.replacement;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;

public class FunctionStringReplacement extends FunctionReplacement {

    private final Function<@Nullable Locale, ? extends @NotNull String> replacementFunction;

    protected FunctionStringReplacement(@NotNull Object placeholder, @NotNull Function<@Nullable Locale, @NotNull String> replacementFunction) {
        super(placeholder, locale -> GlobalAdventureSerializer.deserialize(replacementFunction.apply(locale)));
        this.replacementFunction = replacementFunction;
    }

    protected FunctionStringReplacement(@NotNull Object placeholder, @NotNull Supplier<@NotNull String> replacementSupplier) {
        super(placeholder, locale -> GlobalAdventureSerializer.deserialize(replacementSupplier.get()));
        this.replacementFunction = locale -> replacementSupplier.get();
    }

    @Override
    public boolean supportsStringReplacement() {
        return true;
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.getPlaceholderType().replace(text, this.getPlaceholder(), this.replacementFunction.apply(locale));
    }

}
