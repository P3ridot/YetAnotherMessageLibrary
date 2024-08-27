package dev.peri.yetanothermessageslibrary.replace.replacement;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FunctionReplacement extends ComponentReplacement {

    private final Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacementFunction;

    protected FunctionReplacement(@NotNull Object placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacementFunction) {
        super(placeholder);
        this.replacementFunction = replacementFunction;
    }

    protected FunctionReplacement(@NotNull Object placeholder, @NotNull Supplier<? extends @NotNull ComponentLike> replacementSupplier) {
        this(placeholder, locale -> replacementSupplier.get());
    }

    @Override
    public @NotNull TextReplacementConfig getReplacement(@Nullable Locale locale) {
        return this.newReplacementBuilder()
                .replacement(this.replacementFunction.apply(locale))
                .build();
    }

}
