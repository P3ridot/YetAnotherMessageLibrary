package org.mythicprojects.yetanothermessageslibrary;

import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcherFactory;
import org.mythicprojects.yetanothermessageslibrary.message.Sendable;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

public interface SendableMessageService<R, C extends MessageRepository, D extends MessageDispatcher<R, ? extends D>> extends MessageService<C> {

    /**
     * Wrap message in {@link MessageDispatcher} to easily send it
     *
     * @param messageSupplier function to get message from repository
     * @return MessageDispatcher
     */
    @SuppressWarnings("unchecked")
    default D getMessage(@NotNull Function<@NotNull C, @Nullable Sendable> messageSupplier) {
        return this.getDispatcherFactory().prepareDispatcher(this.getViewerService(), this::getLocale, (entity) -> this.get(entity, messageSupplier));
    }

    @NotNull ViewerService<R> getViewerService();

    @NotNull MessageDispatcherFactory<R, ? extends D> getDispatcherFactory();

}
