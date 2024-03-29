package org.mythicprojects.yetanothermessageslibrary.replace;

import eu.okaeri.placeholders.Placeholders;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;

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
