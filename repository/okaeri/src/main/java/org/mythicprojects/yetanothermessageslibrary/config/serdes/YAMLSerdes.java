package org.mythicprojects.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.holder.ActionBarHolderTransformer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.holder.BossBarHolderSerializer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.holder.ChatSerializer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.holder.SoundHolderSerializer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.holder.TitleHolderSerializer;

public class YAMLSerdes implements OkaeriSerdesPack {

    private final ComponentSerializer<Component, Component, String> componentSerializer;

    public YAMLSerdes(@NotNull ComponentSerializer<Component, Component, String> componentSerializer) {
        this.componentSerializer = componentSerializer;
    }

    public YAMLSerdes() {
        this(GlobalAdventureSerializer.globalSerializer());
    }

    @Override
    public void register(SerdesRegistry registry) {
        // Messages
        registry.register(new ChatSerializer());
        registry.register(new ActionBarHolderTransformer());
        registry.register(new TitleHolderSerializer());
        registry.register(new BossBarHolderSerializer());
        registry.register(new SoundHolderSerializer());
        registry.register(new SendableMessageSerializer());

        // Utilities
        registry.register(new ComponentTransformer(componentSerializer));
        registry.register(new KeyTransformer());
    }

}
