package dev.peri.yetanothermessageslibrary.replace;

import dev.peri.yetanothermessageslibrary.message.MessageDispatcher;
import eu.okaeri.placeholders.Placeholders;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface OkaeriPlaceholdersMessageDispatcher<RECEIVER, DISPATCHER extends MessageDispatcher<RECEIVER, DISPATCHER>>
        extends MessageDispatcher<RECEIVER, DISPATCHER> {

    @Contract("_ -> this")
    default DISPATCHER okaeriPlaceholders(@NotNull Placeholders placeholder) {
        return this.with(null, (receiver, locale, fields) -> new OkaeriPlaceholdersReplaceable(placeholder, context -> {
            context.with("receiver", receiver);
            context.with("locale", locale);
            context.with(fields);
            return context;
        }));
    };

}
