package dev.peri.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ComponentSerializer implements ObjectSerializer<Component> {

    private final net.kyori.adventure.text.serializer.ComponentSerializer<Component, ? extends Component, String> componentSerializer;

    public ComponentSerializer(@NotNull net.kyori.adventure.text.serializer.ComponentSerializer<Component, ? extends Component, String> componentSerializer) {
        this.componentSerializer = componentSerializer;
    }

    @Override
    public boolean supports(Class<? super Component> type) {
        return Component.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Component object, SerializationData data, GenericsDeclaration generics) {
        data.setValue(this.componentSerializer.serialize(object), String.class);
    }

    @Override
    public Component deserialize(DeserializationData data, GenericsDeclaration generics) {
        return this.componentSerializer.deserialize(data.getValue(String.class));
    }

}
