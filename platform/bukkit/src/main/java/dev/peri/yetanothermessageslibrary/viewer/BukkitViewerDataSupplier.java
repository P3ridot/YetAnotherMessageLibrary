package dev.peri.yetanothermessageslibrary.viewer;

import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BukkitViewerDataSupplier implements ViewerDataSupplier<CommandSender> {

    private final BukkitAudiences adventure;

    public BukkitViewerDataSupplier(@NotNull BukkitAudiences adventure) {
        this.adventure = adventure;
    }

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSender receiver) {
        return this.adventure.sender(receiver);
    }

    @Override
    public boolean isConsole(@NotNull CommandSender receiver) {
        return !(receiver instanceof Player);
    }

    @Override
    public @Nullable UUID getKey(@NotNull CommandSender receiver) {
        return receiver instanceof Player
                ? ((Player) receiver).getUniqueId()
                : null;
    }

}
