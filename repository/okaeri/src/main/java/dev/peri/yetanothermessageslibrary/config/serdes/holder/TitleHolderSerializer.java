package dev.peri.yetanothermessageslibrary.config.serdes.holder;

import dev.peri.yetanothermessageslibrary.message.holder.impl.TitleHolder;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import java.time.Duration;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.util.Ticks;

public class TitleHolderSerializer implements ObjectSerializer<TitleHolder> {

    @Override
    public boolean supports(Class<? super TitleHolder> type) {
        return TitleHolder.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(TitleHolder holder, SerializationData data, GenericsDeclaration generics) {
        Component title = holder.getTitle();
        if (title.equals(Component.empty())) {
            data.add("title", title, Component.class);
        }

        Component subtitle = holder.getSubTitle();
        if (subtitle.equals(Component.empty())) {
            data.add("subtitle", subtitle, Component.class);
        }

        Times times = holder.getTimes();
        int fadeIn = ticksFromDuration(times.fadeIn());
        int stay = ticksFromDuration(times.stay());
        int fadeOut = ticksFromDuration(times.fadeOut());

        if (fadeIn > 0) {
            data.add("fade-in", fadeIn, int.class);
        }

        if (stay > 0) {
            data.add("stay", stay, int.class);
        }

        if (fadeOut > 0) {
            data.add("fade-out", fadeOut, int.class);
        }
    }

    @Override
    public TitleHolder deserialize(DeserializationData data, GenericsDeclaration generics) {
        TitleHolder.Builder builder = TitleHolder.builder();

        if (data.containsKey("title")) {
            builder.title(data.get("title", Component.class));
        }

        if (data.containsKey("subtitle")) {
            builder.subTitle(data.get("subtitle", Component.class));
        }

        int fadeIn = 0;
        if (data.containsKey("fade-in")) {
            fadeIn = data.get("fade-in", int.class);
        }

        int stay = 0;
        if (data.containsKey("stay")) {
            stay = data.get("stay", int.class);
        }

        int fadeOut = 0;
        if (data.containsKey("fade-out")) {
            fadeOut = data.get("fade-out", int.class);
        }

        builder.times(fadeIn, stay, fadeOut);

        return builder.build();
    }

    public static int ticksFromDuration(Duration duration) {
        return (int) (duration.toMillis() / Ticks.SINGLE_TICK_DURATION_MS);
    }

}
