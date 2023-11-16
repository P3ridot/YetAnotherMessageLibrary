package org.mythicprojects.yetanothermessageslibrary.replace;

import eu.okaeri.placeholders.Placeholders;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Pattern;
import net.kyori.adventure.text.TextReplacementConfig;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.replace.replacement.ComponentReplacement;

@ApiStatus.Experimental
public class OkaeriPlaceholdersReplaceable extends ComponentReplacement {

    private static final Pattern FIELD_PATTERN = Pattern.compile("\\{(?<content>[^}]+)\\}");

    private final Placeholders placeholders;
    private final Function<PlaceholderContext, PlaceholderContext> applyContexts;

    public OkaeriPlaceholdersReplaceable(@NotNull Placeholders placeholders, @NotNull Function<PlaceholderContext, PlaceholderContext> applyContexts) {
        super(FIELD_PATTERN);
        this.placeholders = placeholders;
        this.applyContexts = applyContexts;
    }

    @Override
    public boolean supportsStringReplacement() {
        return super.supportsStringReplacement();
    }

    @Override
    public @NotNull TextReplacementConfig getReplacement(@Nullable Locale locale) {
        return this.newReplacementBuilder()
                .replacement((result, input) -> GlobalAdventureSerializer.deserialize(this.replacePlaceholders(locale, result.group())))
                .build();
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.replacePlaceholders(locale, text);
    }

    private String replacePlaceholders(@Nullable Locale locale, @NotNull String text) {
        CompiledMessage compiled = locale != null
                ? CompiledMessage.of(locale, text)
                : CompiledMessage.of(text);
        return this.applyContexts.apply(this.placeholders.contextOf(compiled)).apply();
    }

}
