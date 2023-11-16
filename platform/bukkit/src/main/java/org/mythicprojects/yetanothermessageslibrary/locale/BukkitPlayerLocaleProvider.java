package org.mythicprojects.yetanothermessageslibrary.locale;

import java.util.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.util.BukkitLocaleHelper;

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
