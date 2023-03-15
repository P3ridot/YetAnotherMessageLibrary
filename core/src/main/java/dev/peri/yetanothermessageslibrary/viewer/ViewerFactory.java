package dev.peri.yetanothermessageslibrary.viewer;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface ViewerFactory<R> {

    @NotNull Viewer createViewer(@NotNull R receiver, @NotNull Audience audience, boolean console);

}
