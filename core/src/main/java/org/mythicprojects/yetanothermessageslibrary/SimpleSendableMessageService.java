package org.mythicprojects.yetanothermessageslibrary;

import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcherFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public abstract class SimpleSendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, ? extends D>> extends SimpleMessageService<C> implements SendableMessageService<R, C, D> {

    private final ViewerService<R> viewerService;
    private final MessageDispatcherFactory<R, ? extends D> dispatcherFactory;

    public SimpleSendableMessageService(
            @NotNull ViewerService<R> viewerService,
            @NotNull MessageDispatcherFactory<R, ? extends D> dispatcherFactory
    ) {
        this.viewerService = viewerService;
        this.dispatcherFactory = dispatcherFactory;
    }

    public SimpleSendableMessageService(
            @NotNull ViewerDataSupplier<R> viewerDataSupplier,
            @NotNull ViewerFactory<R> viewerFactory,
            @NotNull MessageDispatcherFactory<R, ? extends D> dispatcherFactory
    ) {
        this(
                new ViewerService<>(viewerDataSupplier, viewerFactory),
                dispatcherFactory
        );
    }

    @Override
    public @NotNull ViewerService<R> getViewerService() {
        return this.viewerService;
    }

    @Override
    public @NotNull MessageDispatcherFactory<R, ? extends D> getDispatcherFactory() {
        return this.dispatcherFactory;
    }

}
