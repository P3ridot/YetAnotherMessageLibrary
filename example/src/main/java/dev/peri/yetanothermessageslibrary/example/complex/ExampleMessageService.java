package dev.peri.yetanothermessageslibrary.example.complex;

import dev.peri.yetanothermessageslibrary.SimpleSendableMessageService;
import dev.peri.yetanothermessageslibrary.example.config.MessageConfiguration;
import dev.peri.yetanothermessageslibrary.message.MessageDispatcherFactory;
import dev.peri.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import dev.peri.yetanothermessageslibrary.viewer.ViewerFactory;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ExampleMessageService extends SimpleSendableMessageService<CommandSender, MessageConfiguration, ExampleMessageDispatcher> {

    public ExampleMessageService(
            @NotNull ViewerDataSupplier<CommandSender> viewerDataSupplier,
            @NotNull ViewerFactory<CommandSender> viewerFactory,
            @NotNull MessageDispatcherFactory<CommandSender, ? extends ExampleMessageDispatcher> dispatcherFactory
    ) {
        super(viewerDataSupplier, viewerFactory, dispatcherFactory);
    }

}
