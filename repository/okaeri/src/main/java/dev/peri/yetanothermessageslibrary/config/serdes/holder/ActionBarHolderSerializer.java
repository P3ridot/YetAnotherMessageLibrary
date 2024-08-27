package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import net.kyori.adventure.text.Component;

public class ActionBarHolderSerializer implements ObjectSerializer<ActionBarHolder> {

    @Override
    public boolean supports(Class<? super ActionBarHolder> type) {
        return ActionBarHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ActionBarHolder actionBar, SerializationData data, GenericsDeclaration generics) {
        data.setValue(actionBar.getMessage(), Component.class);
    }

    @Override
    public ActionBarHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        return new ActionBarHolder(data.getValue(Component.class));
    }

}
