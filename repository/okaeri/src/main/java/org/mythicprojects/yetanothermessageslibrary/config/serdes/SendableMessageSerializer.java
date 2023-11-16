package org.mythicprojects.yetanothermessageslibrary.config.serdes;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.mythicprojects.yetanothermessageslibrary.message.SendableMessage;
import org.mythicprojects.yetanothermessageslibrary.message.holder.SendableHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ActionBarHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.BossBarHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.ChatHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.SoundHolder;
import org.mythicprojects.yetanothermessageslibrary.message.holder.impl.TitleHolder;

public class SendableMessageSerializer implements ObjectSerializer<SendableMessage> {

    private static final Map<String, Class<? extends SendableHolder>> HOLDER_TYPES = new LinkedHashMap<String, Class<? extends SendableHolder>>() {{
        this.put("chat", ChatHolder.class);
        this.put("actionbar", ActionBarHolder.class);
        this.put("title", TitleHolder.class);
        this.put("bossbar", BossBarHolder.class);
        this.put("sound", SoundHolder.class);
    }};

    @Override
    public boolean supports(Class<? super SendableMessage> type) {
        return SendableMessage.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(SendableMessage message, SerializationData data, GenericsDeclaration generics) {
        List<SendableHolder> holders = message.getHolders();
        if (holders.size() == 1) {
            SendableHolder holder = holders.get(0);
            if (holder instanceof ChatHolder) {
                data.setValue(holder);
                return;
            }
        }

        HOLDER_TYPES.forEach((key, clazz) -> this.serializeHolders(key, message.getHolders(clazz), data, clazz));
    }

    private <T extends SendableHolder> void serializeHolders(String key, List<? extends T> holders, SerializationData data, Class<? extends T> type) {
        if (holders.size() == 1) {
            data.add(key, holders.get(0), type);
        } else if (holders.size() > 1) {
            data.addCollection(key, holders, type);
        }
    }

    @Override
    public SendableMessage deserialize(DeserializationData data, GenericsDeclaration generics) {
        if (data.isValue()) {
            Object raw = data.getValueRaw();
            if (raw instanceof String || raw instanceof List<?>) {
                return SendableMessage.of(data.getValue(ChatHolder.class));
            }
        }

        List<SendableHolder> messageHolders = new ArrayList<>();
        HOLDER_TYPES.forEach((key, clazz) -> messageHolders.addAll(this.deserializeHolders(key, data, clazz)));
        return SendableMessage.of(messageHolders);
    }

    private <T extends SendableHolder> List<T> deserializeHolders(String key, DeserializationData data, Class<? extends T> type) {
        List<T> holders = new ArrayList<>();
        if (data.containsKey(key)) {
            if (data.getRaw(key) instanceof List<?>) {
                holders.addAll(data.getAsList(key, type));
            } else {
                holders.add(data.get(key, type));
            }
        }
        return holders;
    }

}
