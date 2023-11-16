package org.mythicprojects.yetanothermessageslibrary.message.holder.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.message.SendableMessage;
import org.mythicprojects.yetanothermessageslibrary.message.holder.SendableHolder;
import org.mythicprojects.yetanothermessageslibrary.replace.ComponentReplacer;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.viewer.Viewer;

public class ChatHolder extends SendableHolder {

    protected boolean onlyConsole;
    private final List<Component> messages = new ArrayList<>();

    public ChatHolder(boolean onlyConsole, @NotNull Collection<Component> messages) {
        this.onlyConsole = onlyConsole;
        this.messages.addAll(messages);
    }

    public ChatHolder(boolean onlyConsole, @NotNull Component... messages) {
        this(onlyConsole, Arrays.asList(messages));
    }

    public boolean sendOnlyToConsole() {
        return this.onlyConsole;
    }

    public @NotNull List<Component> getMessages() {
        return this.messages;
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        if (this.onlyConsole && !viewer.isConsole()) {
            return;
        }

        List<Component> finalMessage = this.messages.stream()
                .map(message -> ComponentReplacer.replace(locale, message, replacements))
                .collect(Collectors.toList());
        viewer.sendChatMessage(finalMessage);
    }

    @Override
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        List<Component> finalMessages = this.messages.stream()
                .map(message -> ComponentReplacer.replace(message, replacements))
                .collect(Collectors.toList());
        return new ChatHolder(this.onlyConsole, finalMessages);
    }

    public static @NotNull SendableMessage message(@NotNull Component... messages) {
        return SendableMessage.of(new ChatHolder(false, messages));
    }

    public static @NotNull SendableMessage message(@NotNull String... messages) {
        return SendableMessage.of(new ChatHolder(false, GlobalAdventureSerializer.deserialize(messages)));
    }

}
