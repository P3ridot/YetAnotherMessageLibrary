package org.mythicprojects.yetanothermessageslibrary;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcherFactory;
import org.mythicprojects.yetanothermessageslibrary.message.Sendable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public interface SendableMessageService<RECEIVER, REPOSITORY extends MessageRepository, DISPATCHER extends MessageDispatcher<RECEIVER, ? extends DISPATCHER>>
        extends MessageService<REPOSITORY> {

    /**
     * Wrap message in {@link MessageDispatcher} to easily send it
     *
     * @param messageSupplier function to get message from repository
     * @return MessageDispatcher
     */
    @SuppressWarnings("unchecked")
    default DISPATCHER getMessage(@NotNull Function<@NotNull REPOSITORY, @Nullable Sendable> messageSupplier) {
        return this.getDispatcherFactory().prepareDispatcher(this.getViewerService(), this::getLocale, (entity) -> this.get(entity, messageSupplier));
    }

    @NotNull ViewerService<RECEIVER> getViewerService();

    @NotNull MessageDispatcherFactory<RECEIVER, ? extends DISPATCHER> getDispatcherFactory();

}
