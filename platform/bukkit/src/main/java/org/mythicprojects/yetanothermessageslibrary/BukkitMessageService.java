package org.mythicprojects.yetanothermessageslibrary;

import java.util.function.BiConsumer;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.viewer.BukkitViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public class BukkitMessageService<C extends MessageRepository> extends SimpleSendableMessageService<CommandSender, C, BukkitMessageDispatcher<?>> {

    public BukkitMessageService(@NotNull ViewerService<CommandSender> viewerService) {
        super(
                viewerService,
                BukkitMessageDispatcher::new
        );
    }

    public BukkitMessageService(@NotNull JavaPlugin plugin, @NotNull BukkitAudiences adventure) {
        this(new ViewerService<>(
                new BukkitViewerDataSupplier(adventure),
                ViewerFactory.create(wrapScheduler(plugin))
        ));
    }

    public static BiConsumer<Runnable, Long> wrapScheduler(JavaPlugin plugin) {
        return (runnable, delay) -> plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

}
