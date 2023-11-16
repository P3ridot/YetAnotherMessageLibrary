package org.mythicprojects.yetanothermessageslibrary.config.serdes.holder;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ChatHolder;

public class ChatSerializer implements ObjectSerializer<ChatHolder> {

    @Override
    public boolean supports(Class<? super ChatHolder> type) {
        return ChatHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(ChatHolder holder, SerializationData data, GenericsDeclaration generics) {
        boolean sendOnlyToConsole = holder.sendOnlyToConsole();
        List<Component> messages = holder.getMessages();

        if (!sendOnlyToConsole) {
            Object value = messages.size() == 1 ? messages.get(0) : messages;
            data.setValue(value);
            return;
        }

        data.add("console", true, boolean.class);
        if (messages.size() == 1) {
            data.add("message", messages.get(0), Component.class);
        } else {
            data.addCollection("message", messages, Component.class);
        }
    }

    @Override
    public ChatHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        boolean onlyConsole = false;
        List<Component> messages = new ArrayList<>();

        if (data.isValue()) {
            Object raw = data.getValueRaw();
            if (raw instanceof String) {
                messages.add(data.getValue(Component.class));
            } else if (raw instanceof List) {
                messages.addAll(data.getValueAsList(Component.class));
            }
        } else {
            onlyConsole = data.containsKey("console")
                    ? data.get("console", boolean.class)
                    : false;

            Object raw = data.getRaw("message");
            if (raw instanceof String) {
                messages.add(data.get("message", Component.class));
            } else if (raw instanceof List) {
                messages.addAll(data.getAsList("message", Component.class));
            }
        }

        return new ChatHolder(onlyConsole, messages);
    }

}
