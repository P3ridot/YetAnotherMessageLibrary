package org.mythicprojects.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import org.jetbrains.annotations.NotNull;

public class ComponentTransformer extends BidirectionalTransformer<String, Component> {

    private final ComponentSerializer<Component, Component, String> componentSerializer;

    public ComponentTransformer(@NotNull ComponentSerializer<Component, Component, String> componentSerializer) {
        this.componentSerializer = componentSerializer;
    }

    @Override
    public GenericsPair<String, Component> getPair() {
        return this.genericsPair(String.class, Component.class);
    }

    @Override
    public Component leftToRight(String data, SerdesContext serdesContext) {
        return this.componentSerializer.deserialize(data);
    }

    @Override
    public String rightToLeft(Component data, SerdesContext serdesContext) {
        return this.componentSerializer.serialize(data);
    }

}
