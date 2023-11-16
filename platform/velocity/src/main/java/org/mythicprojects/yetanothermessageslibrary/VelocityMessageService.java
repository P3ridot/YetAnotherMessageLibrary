package org.mythicprojects.yetanothermessageslibrary;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.VelocityMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.viewer.VelocityViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

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
