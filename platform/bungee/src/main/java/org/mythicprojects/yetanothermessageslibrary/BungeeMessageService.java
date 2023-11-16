package org.mythicprojects.yetanothermessageslibrary;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.BungeeMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.viewer.BungeeViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public class BungeeMessageService<C extends MessageRepository> extends SimpleSendableMessageService<CommandSender, C, BungeeMessageDispatcher<?>> {

    public BungeeMessageService(@NotNull ViewerService<CommandSender> viewerService) {
        super(
                viewerService,
                BungeeMessageDispatcher::new
        );
    }

    public BungeeMessageService(@NotNull Plugin plugin, @NotNull BungeeAudiences adventure) {
        this(new ViewerService<>(
                new BungeeViewerDataSupplier(adventure),
                ViewerFactory.create( wrapScheduler(plugin))
        ));
    }

    public static BiConsumer<Runnable, Long> wrapScheduler(Plugin plugin) {
        return (runnable, delay) -> plugin.getProxy().getScheduler().schedule(plugin, runnable, delay * 50L, TimeUnit.MILLISECONDS);
    }

}
