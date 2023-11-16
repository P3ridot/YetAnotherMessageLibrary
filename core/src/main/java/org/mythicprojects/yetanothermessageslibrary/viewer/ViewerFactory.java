package org.mythicprojects.yetanothermessageslibrary.viewer;

import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface ViewerFactory<R> {

    @NotNull Viewer createViewer(@NotNull R receiver, @NotNull Audience audience, boolean console);

    static <R> ViewerFactory<R> create(@NotNull BiConsumer<Runnable, Long> schedule) {
        return (receiver, audience, console) -> new Viewer(audience, console, schedule);
    }

}
