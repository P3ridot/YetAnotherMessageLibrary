package dev.peri.yetanothermessageslibrary.util;

import java.lang.reflect.Method;
import java.util.Locale;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BukkitLocaleHelper {

    private static Method GET_LOCALE_SPIGOT;

    static {
        try {
            GET_LOCALE_SPIGOT = Player.Spigot.class.getMethod("getLocale");
        } catch (NoSuchMethodException e) {
            GET_LOCALE_SPIGOT = null;
        }
    }

    private BukkitLocaleHelper() {
    }

    public @Nullable static String getLocaleString(@NotNull Player player) {
        if (GET_LOCALE_SPIGOT != null) {
            try {
                return (String) GET_LOCALE_SPIGOT.invoke(player.spigot());
            }
            catch (Exception ex) {
                return null;
            }
        }

        return player.getLocale();
    }

    public @Nullable static Locale getLocale(@NotNull Player player) {
        String localeString = getLocaleString(player);
        if (localeString == null) {
            return null;
        }

        localeString = localeString.replace('_', '-');
        return Locale.forLanguageTag(localeString);
    }

}