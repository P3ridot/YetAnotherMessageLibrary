package org.mythicprojects.yetanothermessageslibrary.replace.replacement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.util.TriFunction;
import org.mythicprojects.yetanothermessageslibrary.util.Validate;

public abstract class Replacement implements Replaceable {

    private final PlaceholderType placeholderType;
    private final Object placeholder;

    protected Replacement(@NotNull Object placeholder) {
        PlaceholderType placeholderType = PlaceholderType.match(placeholder.getClass());
        Validate.isTrue(placeholderType != null, "placeholder must be a String or Pattern");
        this.placeholderType = placeholderType;
        this.placeholder = placeholder;
    }

    protected final @NotNull Replacement.PlaceholderType getPlaceholderType() {
        return this.placeholderType;
    }

    protected final @NotNull TextReplacementConfig.Builder newReplacementBuilder() {
        return this.placeholderType.newReplacementBuilder(this.placeholder);
    }

    public final @NotNull Object getPlaceholder() {
        return this.placeholder;
    }

    protected enum PlaceholderType {

        STRING(
                String.class,
                (text, placeholder, replacement) -> text.replace((String) placeholder, replacement),
                replacement -> TextReplacementConfig.builder().matchLiteral((String) replacement)
        ),
        REGEX(
                Pattern.class,
                (text, placeholder, replacement) -> ((Pattern) placeholder).matcher(text).replaceAll(replacement),
                replacement -> TextReplacementConfig.builder().match((Pattern) replacement)
        );

        private static final Map<Class<?>, PlaceholderType> PLACEHOLDERS_MAP;

        static {
            Map<Class<?>, PlaceholderType> map = new HashMap<>();
            for (PlaceholderType placeholderType : PlaceholderType.values()) {
                map.put(placeholderType.getClazz(), placeholderType);
            }
            PLACEHOLDERS_MAP = Collections.unmodifiableMap(map);
        }

        private final Class<?> clazz;
        private final TriFunction<String, Object, String, String> replacer;
        private final Function<Object, TextReplacementConfig.Builder> replacementBuilderFactory;

        PlaceholderType(
                @NotNull Class<?> clazz,
                @NotNull TriFunction<@NotNull String, @NotNull Object, @NotNull String, @NotNull String> replacer,
                @NotNull Function<@NotNull Object, TextReplacementConfig.Builder> replacementBuilderFactory
        ) {
            this.clazz = clazz;
            this.replacer = replacer;
            this.replacementBuilderFactory = replacementBuilderFactory;
        }

        public Class<?> getClazz() {
            return this.clazz;
        }

        public boolean isInstance(Object obj) {
            return this.clazz.isInstance(obj);
        }

        public @NotNull String replace(@NotNull String text, @NotNull Object placeholder, @NotNull String replacement) {
            return this.replacer.apply(text, placeholder, replacement);
        }

        public @NotNull TextReplacementConfig.Builder newReplacementBuilder(@NotNull Object placeholder) {
            return this.replacementBuilderFactory.apply(placeholder);
        }

        public static @Nullable Replacement.PlaceholderType match(@NotNull Class<?> clazz) {
            return PLACEHOLDERS_MAP.get(clazz);
        }

    }

    public static @NotNull SimpleStringReplacement string(@NotNull String placeholder, @NotNull Object replacement) {
        return new SimpleStringReplacement(placeholder, Objects.toString(replacement));
    }

    public static @NotNull SimpleStringReplacement string(@NotNull Pattern placeholder, @NotNull Object replacement) {
        return new SimpleStringReplacement(placeholder, Objects.toString(replacement));
    }

    public static @NotNull FunctionStringReplacement string(@NotNull String placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull Object> replacementFunction) {
        return new FunctionStringReplacement(placeholder, locale -> Objects.toString(replacementFunction.apply(locale)));
    }

    public static @NotNull FunctionStringReplacement string(@NotNull Pattern placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull Object> replacementFunction) {
        return new FunctionStringReplacement(placeholder, locale -> Objects.toString(replacementFunction.apply(locale)));
    }

    public static @NotNull FunctionStringReplacement string(@NotNull String placeholder, @NotNull Supplier<? extends @NotNull Object> replacementSupplier) {
        return new FunctionStringReplacement(placeholder, locale -> Objects.toString(replacementSupplier.get()));
    }

    public static @NotNull FunctionStringReplacement string(@NotNull Pattern placeholder, @NotNull Supplier<? extends @NotNull Object> replacementSupplier) {
        return new FunctionStringReplacement(placeholder, locale -> Objects.toString(replacementSupplier.get()));
    }

    public static @NotNull SimpleReplacement component(@NotNull String placeholder, @NotNull ComponentLike replacement) {
        return new SimpleReplacement(placeholder, replacement);
    }

    public static @NotNull SimpleReplacement component(@NotNull Pattern placeholder, @NotNull ComponentLike replacement) {
        return new SimpleReplacement(placeholder, replacement);
    }

    public static @NotNull FunctionReplacement component(@NotNull String placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacementFunction) {
        return new FunctionReplacement(placeholder, replacementFunction);
    }

    public static @NotNull FunctionReplacement component(@NotNull Pattern placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacementFunction) {
        return new FunctionReplacement(placeholder, replacementFunction);
    }

    public static @NotNull FunctionReplacement component(@NotNull String placeholder, @NotNull Supplier<? extends @NotNull ComponentLike> replacementSupplier) {
        return new FunctionReplacement(placeholder, replacementSupplier);
    }

    public static @NotNull FunctionReplacement component(@NotNull Pattern placeholder, @NotNull Supplier<? extends @NotNull ComponentLike> replacementSupplier) {
        return new FunctionReplacement(placeholder, replacementSupplier);
    }

}
