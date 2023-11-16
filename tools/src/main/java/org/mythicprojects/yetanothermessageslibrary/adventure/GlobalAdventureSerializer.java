package org.mythicprojects.yetanothermessageslibrary.adventure;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.replace.replacement.FunctionStringReplacement;
import org.mythicprojects.yetanothermessageslibrary.replace.replacement.SimpleStringReplacement;

/**
 * Simple utility class to provide global access to adventure serializer.
 * <p>
 * This class is used mostly by string replacements ({@link SimpleStringReplacement}, {@link FunctionStringReplacement})
 * and some of {@link org.mythicprojects.yetanothermessageslibrary.message.holder.SendableHolder} implementations.
 */
public final class GlobalAdventureSerializer {

    private static ComponentSerializer<Component, Component, String> GLOBAL_SERIALIZER = new ComponentSerializer<Component, Component, String>() {
        @Override
        public @NotNull Component deserialize(@NotNull String input) {
            return Component.text(input);
        }

        @Override
        public @NotNull String serialize(@NotNull Component component) {
            throw new UnsupportedOperationException("Register global serializer to serialize components");
        }
    };

    private GlobalAdventureSerializer() {
    }

    public static void globalSerializer(@NotNull ComponentSerializer<Component, Component, String> globalSerializer) {
        GLOBAL_SERIALIZER = Objects.requireNonNull(globalSerializer, "globalSerializer cannot be null");
    }

    public static @NotNull ComponentSerializer<Component, Component, String> globalSerializer() {
        return GLOBAL_SERIALIZER;
    }

    public static @NotNull String serialize(@NotNull Component component) {
        return GLOBAL_SERIALIZER.serialize(component);
    }

    public static @NotNull Component deserialize(@NotNull String input) {
        return GLOBAL_SERIALIZER.deserialize(input);
    }

    public static @NotNull List<Component> deserialize(@NotNull List<String> input) {
        return input.stream().map(GlobalAdventureSerializer::deserialize).collect(Collectors.toList());
    }

    public static @NotNull List<Component> deserialize(@NotNull String... input) {
        return deserialize(Arrays.asList(input));
    }

    public static @NotNull Component[] deserializeArray(@NotNull List<String> input) {
        return deserialize(input).toArray(new Component[0]);
    }

    public static @NotNull Component[] deserializeArray(@NotNull String... input) {
        return deserialize(input).toArray(new Component[0]);
    }

}
