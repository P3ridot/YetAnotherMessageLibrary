package org.mythicprojects.yetanothermessageslibrary.message;

import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public interface MessageDispatcherFactory<R, D extends MessageDispatcher<R, ? extends D>> {

    @NotNull D prepareDispatcher(
            @NotNull ViewerService<R> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    );

}
