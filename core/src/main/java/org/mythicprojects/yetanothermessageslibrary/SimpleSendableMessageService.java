package org.mythicprojects.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcherFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

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
