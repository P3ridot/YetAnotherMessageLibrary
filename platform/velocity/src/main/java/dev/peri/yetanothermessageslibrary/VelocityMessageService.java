package dev.peri.yetanothermessageslibrary;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.peri.yetanothermessageslibrary.message.VelocityMessageDispatcher;
import dev.peri.yetanothermessageslibrary.viewer.VelocityViewerDataSupplier;
import dev.peri.yetanothermessageslibrary.viewer.ViewerFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public class VelocityMessageService<C extends MessageRepository> extends SimpleSendableMessageService<CommandSource, C, VelocityMessageDispatcher<?>> {

    public VelocityMessageService(@NotNull ViewerService<CommandSource> viewerService) {
        super(
                viewerService,
                VelocityMessageDispatcher::new
        );
    }

    public VelocityMessageService(@NotNull ProxyServer proxyServer, @NotNull Object plugin) {
        this(new ViewerService<>(
                new VelocityViewerDataSupplier(),
                ViewerFactory.create(wrapScheduler(proxyServer, plugin))
        ));
    }

    public static BiConsumer<Runnable, Long> wrapScheduler(ProxyServer proxyServer, Object plugin) {
        return (runnable, delay) -> proxyServer.getScheduler()
                .buildTask(plugin, runnable)
                .delay(delay * 50L, TimeUnit.MILLISECONDS)
                .schedule();
    }

}
