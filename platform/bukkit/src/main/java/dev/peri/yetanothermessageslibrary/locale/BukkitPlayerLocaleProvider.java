package dev.peri.yetanothermessageslibrary.locale;

import dev.peri.yetanothermessageslibrary.util.BukkitLocaleHelper;
import java.util.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitPlayerLocaleProvider implements LocaleProvider<Player> {

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return Player.class.isAssignableFrom(clazz);
    }

    @Override
    public @Nullable Locale getLocale(@NotNull Player entity) {
        return BukkitLocaleHelper.getLocale(entity);
    }

}
