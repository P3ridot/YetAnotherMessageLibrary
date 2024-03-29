package org.mythicprojects.yetanothermessageslibrary.replace;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.mythicprojects.yetanothermessageslibrary.message.MessageDispatcher;

public interface PlaceholderAPIMessageDispatcher<RECEIVER extends CommandSender, DISPATCHER extends PlaceholderAPIMessageDispatcher<RECEIVER, DISPATCHER>>
        extends MessageDispatcher<RECEIVER, DISPATCHER> {

    @Contract(" -> this")
    default DISPATCHER placeholderAPI() {
        return this.with((Class<? extends RECEIVER>) Player.class, player -> new PlaceholderAPIReplaceable((Player) player));
    }

}
