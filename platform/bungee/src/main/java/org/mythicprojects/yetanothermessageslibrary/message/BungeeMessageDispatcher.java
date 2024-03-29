package org.mythicprojects.yetanothermessageslibrary.message;

import java.util.Locale;
import java.util.function.Function;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

@SuppressWarnings("unchecked")
public class BungeeMessageDispatcher<DISPATCHER extends BungeeMessageDispatcher<DISPATCHER>>
        extends SimpleMessageDispatcher<CommandSender, DISPATCHER> {

    public BungeeMessageDispatcher(
            @NotNull ViewerService<CommandSender> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    @Contract(" -> this")
    public DISPATCHER allPlayers() {
        this.receivers(ProxyServer.getInstance().getPlayers());
        return (DISPATCHER) this;
    }

    @Contract(" -> this")
    public DISPATCHER console() {
        this.receiver(ProxyServer.getInstance().getConsole());
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (DISPATCHER) this;
    }

}
