package org.mythicprojects.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.SoundHolder;

public class SoundHolderSerializer implements ObjectSerializer<SoundHolder> {

    @Override
    public boolean supports(Class<? super SoundHolder> type) {
        return SoundHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(SoundHolder holder, SerializationData data, GenericsDeclaration generics) {
        Sound sound = holder.getSound();
        data.add("name", sound.name().asString(), String.class);
        data.add("source", sound.source(), Sound.Source.class);
        data.add("volume", sound.volume(), float.class);
        data.add("pitch", sound.pitch(), float.class);
        if (holder.stopOtherSounds()) {
            data.add("stop-other-sounds", true, boolean.class);
        }
    }

    @Override
    public SoundHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        Key name = data.get("name", Key.class);
        Sound.Source source = data.get("source", Sound.Source.class);
        float volume = data.get("volume", float.class);
        float pitch = data.get("pitch", float.class);
        boolean stopOtherSounds = data.containsKey("stop-other-sounds")
                ? data.get("stop-other-sounds", boolean.class)
                : false;

        return new SoundHolder(Sound.sound(name, source, volume, pitch), stopOtherSounds);
    }

}
