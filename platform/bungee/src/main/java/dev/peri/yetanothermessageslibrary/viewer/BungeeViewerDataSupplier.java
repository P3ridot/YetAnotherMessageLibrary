package dev.peri.yetanothermessageslibrary.viewer;

import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BungeeViewerDataSupplier implements ViewerDataSupplier<CommandSender> {

    private final BungeeAudiences adventure;

    public BungeeViewerDataSupplier(@NotNull BungeeAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSender receiver) {
        return this.adventure.sender(receiver);
    }

    @Override
    public boolean isConsole(@NotNull CommandSender receiver) {
        return !(receiver instanceof ProxiedPlayer);
    }

    @Override
    public @Nullable UUID getKey(@NotNull CommandSender receiver) {
        return receiver instanceof ProxiedPlayer
                ? ((ProxiedPlayer) receiver).getUniqueId()
                : null;
    }

}
