package org.mythicprojects.yetanothermessageslibrary.message;

import java.util.Locale;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public class BukkitMessageDispatcher<D extends BukkitMessageDispatcher<?>> extends MessageDispatcher<CommandSender, D> {

    public BukkitMessageDispatcher(
            @NotNull ViewerService<CommandSender> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    @Contract(" -> this")
    public D broadcast() {
        this.broadcastPlayers();
        this.console();
        return (D) this;
    }

    @Contract(" -> this")
    public D broadcastPlayers() {
        this.receivers(Bukkit.getOnlinePlayers());
        return (D) this;
    }

    @Contract(" -> this")
    public D console() {
        this.receiver(Bukkit.getConsoleSender());
        return (D) this;
    }

    @Contract("_ -> this")
    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
