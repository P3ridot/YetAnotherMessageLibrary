package dev.peri.yetanothermessageslibrary;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.message.Sendable;
import dev.peri.yetanothermessageslibrary.viewer.ViewerService;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
