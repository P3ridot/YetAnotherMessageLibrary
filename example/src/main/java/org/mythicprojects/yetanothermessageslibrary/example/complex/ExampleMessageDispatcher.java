package org.mythicprojects.yetanothermessageslibrary.example.complex;

import java.util.Locale;
import java.util.function.Function;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.message.Sendable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public class ExampleMessageDispatcher extends BukkitMessageDispatcher<ExampleMessageDispatcher> {

    public ExampleMessageDispatcher(
            @NotNull ViewerService<CommandSender> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        super(viewerService, localeSupplier, messageSupplier);
    }

    public ExampleMessageDispatcher isInOverworld() {
        return this.predicate(Player.class, player -> player.getWorld().getEnvironment() == World.Environment.NORMAL);
    }

    public ExampleMessageDispatcher isInWater() {
        return this.predicate(Player.class, Entity::isInWater);
    }

}
