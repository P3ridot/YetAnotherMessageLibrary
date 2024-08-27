package dev.peri.yetanothermessageslibrary.replace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentReplacer {

    private ComponentReplacer() {
    }

    @Contract(pure = true, value = "_, null, _ -> null")
    public static Component replace(@Nullable Locale locale, @Nullable Component text, @NotNull Iterable<? extends Replaceable> replacements) {
        if (text == null) {
            return null;
        }

        for (Replaceable replacement : replacements) {
            text = replacement.replace(locale, text);
        }
        return text;
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static Component replace(@Nullable Component text, @NotNull Iterable<? extends Replaceable> replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true, value = "_, null, _ -> null")
    public static Component replace(@Nullable Locale locale, @Nullable Component text, @NotNull Replaceable... replacements) {
        return replace(locale, text, Arrays.asList(replacements));
    }

    @Contract(pure = true, value = "null, _, -> null")
    public static Component replace(@Nullable Component text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true)
    public static @NotNull List<Component> replace(@Nullable Locale locale, @NotNull Iterable<? extends Component> text, @NotNull Iterable<? extends Replaceable> replacements) {
        List<Component> result = new ArrayList<>();
        for (Component line : text) {
            result.add(replace(locale, line, replacements));
        }
        return result;
    }

    @Contract(pure = true)
    public static @NotNull List<Component> replace(@NotNull Iterable<? extends Component> text, @NotNull Collection<? extends Replaceable> replacements) {
        return replace(null, text, replacements);
    }

    @Contract(pure = true)
    public static @NotNull List<Component> replace(@Nullable Locale locale, @NotNull Iterable<? extends Component> text, @NotNull Replaceable... replacements) {
        return replace(locale, text, Arrays.asList(replacements));
    }

    @Contract(pure = true)
    public static @NotNull List<Component> replace(@NotNull Iterable<? extends Component> text, @NotNull Replaceable... replacements) {
        return replace(null, text, replacements);
    }

}
