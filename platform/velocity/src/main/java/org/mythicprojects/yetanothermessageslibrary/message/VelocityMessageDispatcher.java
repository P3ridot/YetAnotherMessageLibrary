package org.mythicprojects.yetanothermessageslibrary.message;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public class VelocityMessageDispatcher<D extends VelocityMessageDispatcher<?>> extends MessageDispatcher<CommandSource, D> {

    public VelocityMessageDispatcher(
            @NotNull ViewerService<CommandSource> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    @Contract("_ -> this")
    public D broadcast(@NotNull ProxyServer proxy) {
        this.broadcastPlayers(proxy);
        this.console(proxy);
        return (D) this;
    }

    @Contract("_ -> this")
    public D broadcastPlayers(@NotNull ProxyServer proxy) {
        proxy.getAllPlayers().forEach(this::receiver);
        return (D) this;
    }

    @Contract("_ -> this")
    public D console(@NotNull ProxyServer proxy) {
        this.receiver(proxy.getConsoleCommandSource());
        return (D) this;
    }

    @Contract("_ -> this")
    public D permission(@NotNull String permission) {
        this.predicate(sender -> sender.hasPermission(permission));
        return (D) this;
    }

}
