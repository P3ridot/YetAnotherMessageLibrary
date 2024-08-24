package dev.peri.yetanothermessageslibrary.example;

import dev.peri.yetanothermessageslibrary.SendableMessageService;
import dev.peri.yetanothermessageslibrary.example.complex.ExampleMessageDispatcher;
import dev.peri.yetanothermessageslibrary.example.config.MessageConfiguration;
import dev.peri.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ExampleListener implements Listener {

    private final SendableMessageService<CommandSender, MessageConfiguration, BukkitMessageDispatcher<?>> simpleMessageService;
    private final SendableMessageService<CommandSender, MessageConfiguration, ExampleMessageDispatcher> complexMessageService;

    public ExampleListener(
            SendableMessageService<CommandSender, MessageConfiguration, BukkitMessageDispatcher<?>> simpleMessageService,
            SendableMessageService<CommandSender, MessageConfiguration, ExampleMessageDispatcher> complexMessageService
    ) {
        this.simpleMessageService = simpleMessageService;
        this.complexMessageService = complexMessageService;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        this.simpleMessageService.getMessage(config -> config.exampleMessage)
                .receiver(player)
                .with("{player}", player.getName())
                .send();
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        this.complexMessageService.getMessage(config -> config.youAreInOverworld)
                .receiver(event.getPlayer())
                .isInOverworld()
                .send();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        this.complexMessageService.getMessage(config -> config.youAreInWater)
                .receiver(event.getPlayer())
                .isInWater()
                .send();
    }

}
