package dev.peri.yetanothermessageslibrary.message;

import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.Locale;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MessageDispatcherFactory<RECEIVER, DISPATCHER extends MessageDispatcher<RECEIVER, ? extends DISPATCHER>> {

    @NotNull DISPATCHER prepareDispatcher(
            @NotNull ViewerService<RECEIVER> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    );

}
