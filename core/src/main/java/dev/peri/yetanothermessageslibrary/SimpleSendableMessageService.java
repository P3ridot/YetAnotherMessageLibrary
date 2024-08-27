package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import dev.peri.yetanothermessageslibrary.viewer.ViewerFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleSendableMessageService<RECEIVER, REPOSITORY extends MessageRepository, DISPATCHER extends MessageDispatcher<RECEIVER, ? extends DISPATCHER>>
        extends SimpleMessageService<REPOSITORY>
        implements SendableMessageService<RECEIVER, REPOSITORY, DISPATCHER> {

    private final ViewerService<RECEIVER> viewerService;
    private final MessageDispatcherFactory<RECEIVER, ? extends DISPATCHER> dispatcherFactory;

    public SimpleSendableMessageService(
            @NotNull ViewerService<RECEIVER> viewerService,
            @NotNull MessageDispatcherFactory<RECEIVER, ? extends DISPATCHER> dispatcherFactory
    ) {
        this.viewerService = viewerService;
        this.dispatcherFactory = dispatcherFactory;
    }

    public SimpleSendableMessageService(
            @NotNull ViewerDataSupplier<RECEIVER> viewerDataSupplier,
            @NotNull ViewerFactory<RECEIVER> viewerFactory,
            @NotNull MessageDispatcherFactory<RECEIVER, ? extends DISPATCHER> dispatcherFactory
    ) {
        this(
                new ViewerService<>(viewerDataSupplier, viewerFactory),
                dispatcherFactory
        );
    }

    @Override
    public @NotNull ViewerService<RECEIVER> getViewerService() {
        return this.viewerService;
    }

    @Override
    public @NotNull MessageDispatcherFactory<RECEIVER, ? extends DISPATCHER> getDispatcherFactory() {
        return this.dispatcherFactory;
    }

}
