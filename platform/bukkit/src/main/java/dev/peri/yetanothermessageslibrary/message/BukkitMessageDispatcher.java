package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
public class BukkitMessageDispatcher<DISPATCHER extends BukkitMessageDispatcher<DISPATCHER>>
        extends SimpleMessageDispatcher<CommandSender, DISPATCHER> {

    public BukkitMessageDispatcher(
            @NotNull ViewerService<CommandSender> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    @Contract(" -> this")
    public DISPATCHER allPlayers() {
        this.receivers(Bukkit.getOnlinePlayers());
        return (DISPATCHER) this;
    }

    @Contract(" -> this")
    public DISPATCHER console() {
        this.receiver(Bukkit.getConsoleSender());
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (DISPATCHER) this;
    }

}
