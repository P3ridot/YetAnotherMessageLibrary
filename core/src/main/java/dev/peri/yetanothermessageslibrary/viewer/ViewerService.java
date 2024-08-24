package dev.peri.yetanothermessageslibrary.viewer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ViewerService<R> {

    private final ViewerDataSupplier<R> viewerDataSupplier;
    private final ViewerFactory<R> viewerFactory;

    private final Map<UUID, Viewer> viewers = new ConcurrentHashMap<>();

    public ViewerService(
            @NotNull ViewerDataSupplier<R> viewerDataSupplier,
            @NotNull ViewerFactory<R> viewerFactory
    ) {
        this.viewerDataSupplier = viewerDataSupplier;
        this.viewerFactory = viewerFactory;
    }

    public @NotNull Viewer findOrCreateViewer(@NotNull R receiver) {
        Audience audience = this.viewerDataSupplier.getAudience(receiver);
        boolean console = this.viewerDataSupplier.isConsole(receiver);

        UUID uuid = this.viewerDataSupplier.getKey(receiver);
        if (uuid == null) {
            return this.viewerFactory.createViewer(receiver, audience, console);
        }

        return this.viewers.computeIfAbsent(
                uuid,
                key -> this.viewerFactory.createViewer(receiver, audience, console)
        );
    }

    public @Nullable Viewer removeViewer(@NotNull R receiver) {
        UUID uuid = this.viewerDataSupplier.getKey(receiver);
        if (uuid == null) {
            return null;
        }

        Viewer remove = this.viewers.remove(uuid);
        if (remove != null) {
            remove.clearBossBars();
            remove.stopSounds();
        }
        return remove;
    }

}
