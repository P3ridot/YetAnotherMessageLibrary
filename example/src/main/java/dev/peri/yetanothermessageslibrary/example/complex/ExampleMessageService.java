package dev.peri.yetanothermessageslibrary.example.complex;

import dev.peri.yetanothermessageslibrary.example.config.MessageConfiguration;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.SimpleSendableMessageService;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcherFactory;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;

public class ExampleMessageService extends SimpleSendableMessageService<CommandSender, MessageConfiguration, ExampleMessageDispatcher> {

    public ExampleMessageService(
            @NotNull ViewerDataSupplier<CommandSender> viewerDataSupplier,
            @NotNull ViewerFactory<CommandSender> viewerFactory,
            @NotNull MessageDispatcherFactory<CommandSender, ? extends ExampleMessageDispatcher> dispatcherFactory
    ) {
        super(viewerDataSupplier, viewerFactory, dispatcherFactory);
    }

}
