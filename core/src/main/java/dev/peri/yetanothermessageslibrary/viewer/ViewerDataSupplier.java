package dev.peri.yetanothermessageslibrary.viewer;

import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ViewerDataSupplier<R> {

    @NotNull Audience getAudience(@NotNull R receiver);

    boolean isConsole(@NotNull R receiver);

    /**
     * Get unique key for receiver used to cache viewers, if null is returned viewer won't be cached
     *
     * @param receiver receiver
     * @return unique key
     */
    @Nullable UUID getKey(@NotNull R receiver);

}
