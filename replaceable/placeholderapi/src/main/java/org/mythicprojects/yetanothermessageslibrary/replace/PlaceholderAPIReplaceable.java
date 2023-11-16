package org.mythicprojects.yetanothermessageslibrary.replace;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.regex.Pattern;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.replace.replacement.ComponentReplacement;

public class PlaceholderAPIReplaceable extends ComponentReplacement {

    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("[%]([^%]+)[%]");

    private final WeakReference<Player> playerRef;
    private final TextReplacementConfig replacement;

    public PlaceholderAPIReplaceable(@Nullable Player player) {
        super(PLACEHOLDER_PATTERN);
        this.playerRef = new WeakReference<>(player);
        this.replacement = this.newReplacementBuilder()
                .replacement((result, input) -> GlobalAdventureSerializer.deserialize(this.replacePlaceholder(result.group())))
                .build();
    }

    @Override
    public boolean supportsStringReplacement() {
        return true;
    }

    @Override
    public @NotNull TextReplacementConfig getReplacement(@Nullable Locale locale) {
        return this.replacement;
    }

    @Override
    public @NotNull String replace(@Nullable Locale locale, @NotNull String text) {
        return this.replacePlaceholder(text);
    }

    private String replacePlaceholder(String text) {
        Player player = this.playerRef.get();
        if (player == null) {
            return text;
        }
        return PlaceholderAPI.setPlaceholders(player, text);
    }

}
