package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ActionBarHolderSerializer implements ObjectSerializer<ActionBarHolder> {

    private final net.kyori.adventure.text.serializer.ComponentSerializer<Component, ? extends Component, String> componentSerializer;

    public ActionBarHolderSerializer(@NotNull net.kyori.adventure.text.serializer.ComponentSerializer<Component, ? extends Component, String> componentSerializer) {
        this.componentSerializer = componentSerializer;
    }

    @Override
    public boolean supports(Class<? super ActionBarHolder> type) {
        return ActionBarHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ActionBarHolder actionBar, SerializationData data, GenericsDeclaration generics) {
        data.setValue(this.componentSerializer.serialize(actionBar.getMessage()), String.class);
    }

    @Override
    public ActionBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        Component component = this.componentSerializer.deserialize(data.getValue(String.class));
        return new ActionBarHolder(component);
    }

}
