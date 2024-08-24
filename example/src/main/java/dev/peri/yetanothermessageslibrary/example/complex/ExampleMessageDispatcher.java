package dev.peri.yetanothermessageslibrary.example.complex;

import dev.peri.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.Sendable;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
