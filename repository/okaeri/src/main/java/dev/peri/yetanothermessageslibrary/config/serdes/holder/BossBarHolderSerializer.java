package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.HashSet;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;

public class BossBarHolderSerializer implements ObjectSerializer<BossBarHolder> {

    @Override
    public boolean supports(Class<? super BossBarHolder> type) {
        return BossBarHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(BossBarHolder holder, SerializationData data, GenericsDeclaration generics) {
        data.add("name", holder.getName(), Component.class);
        data.add("color", holder.getColor(), BossBar.Color.class);
        data.add("overlay", holder.getOverlay(), BossBar.Overlay.class);

        if (!holder.getFlags().isEmpty()) {
            data.addCollection("flags", holder.getFlags(), BossBar.Flag.class);
        }

        if (holder.getProgress() >= 0) {
            data.add("progress", holder.getProgress(), float.class);
        }

        if (holder.getStay() >= 0) {
            data.add("stay", holder.getStay(), int.class);
        }

        if (holder.clearOtherBars()) {
            data.add("clear-other-bars", true, boolean.class);
        }
    }

    @Override
    public BossBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        return BossBarHolder.builder(data.get("name", Component.class))
                .progress(data.containsKey("progress") ? data.get("progress", float.class) : 1)
                .color(data.containsKey("color") ? data.get("color", BossBar.Color.class) : BossBar.Color.PINK)
                .overlay(data.containsKey("overlay") ? data.get("overlay", BossBar.Overlay.class) : BossBar.Overlay.PROGRESS)
                .addFlags(data.containsKey("flags") ? data.getAsList("flags", BossBar.Flag.class) : new HashSet<>())
                .stay(data.containsKey("stay") ? data.get("stay", int.class) : -1)
                .clearOtherBars(data.containsKey("clear-other-bars") && data.get("clear-other-bars", boolean.class))
                .build();
    }

}
