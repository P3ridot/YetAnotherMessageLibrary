package dev.peri.yetanothermessageslibrary.example.config;

import dev.peri.yetanothermessageslibrary.MessageRepository;
import dev.peri.yetanothermessageslibrary.message.SendableMessage;
import dev.peri.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import dev.peri.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import dev.peri.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import eu.okaeri.configs.OkaeriConfig;
import net.kyori.adventure.bossbar.BossBar;

public class MessageConfiguration extends OkaeriConfig implements MessageRepository {

    public SendableMessage exampleMessage = ChatHolder.message("&aHello, &b{player}!");

    public SendableMessage youAreInOverworld = ActionBarHolder.message("&cYou are in overworld!");
    public SendableMessage youAreInWater = SendableMessage.builder()
            .actionBar("&cYou are in water!")
            .chat("&#C21790You are in water!")
            .title("<rainbow>You are in water!</rainbow>", "", 10, 40, 10)
            .addHolders(BossBarHolder.builder("<gradient:#5EA899A:#E4A010>You are in water!</gradient>")
                    .color(BossBar.Color.GREEN)
                    .overlay(BossBar.Overlay.NOTCHED_6)
                    .progress(0.5f)
                    .build())
            .build();


}
