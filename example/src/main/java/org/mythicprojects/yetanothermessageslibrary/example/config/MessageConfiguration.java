package org.mythicprojects.yetanothermessageslibrary.example.config;

import eu.okaeri.configs.OkaeriConfig;
import net.kyori.adventure.bossbar.BossBar;
import org.mythicprojects.yetanothermessageslibrary.MessageRepository;
import org.mythicprojects.yetanothermessageslibrary.message.SendableMessage;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ChatHolder;

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
