package dev.peri.yetanothermessageslibrary.viewer;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VelocityViewerDataSupplier implements ViewerDataSupplier<CommandSource> {

    @Override
    public @NotNull Audience getAudience(@NotNull CommandSource receiver) {
        return receiver;
    }

    @Override
    public boolean isConsole(@NotNull CommandSource receiver) {
        return !(receiver instanceof Player);
    }

    @Override
    public @Nullable UUID getKey(@NotNull CommandSource receiver) {
        return receiver instanceof Player
                ? ((Player) receiver).getUniqueId()
                : null;
    }

}
