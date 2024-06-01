package org.mythicprojects.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

@SuppressWarnings("unchecked")
public class VelocityMessageDispatcher<DISPATCHER extends VelocityMessageDispatcher<DISPATCHER>>
        extends SimpleMessageDispatcher<CommandSource, DISPATCHER> {

    public VelocityMessageDispatcher(
            @NotNull ViewerService<CommandSource> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    @Contract("_ -> this")
    public DISPATCHER all(@NotNull ProxyServer proxy) {
        this.allPlayers(proxy);
        this.console(proxy);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER allPlayers(@NotNull ProxyServer proxy) {
        proxy.getAllPlayers().forEach(this::receiver);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER console(@NotNull ProxyServer proxy) {
        this.receiver(proxy.getConsoleCommandSource());
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (DISPATCHER) this;
    }

}
