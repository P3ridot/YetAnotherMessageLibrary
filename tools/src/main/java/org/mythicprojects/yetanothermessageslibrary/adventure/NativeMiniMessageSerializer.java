package org.mythicprojects.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.minimessage.MiniMessage;

final class NativeMiniMessageSerializer {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private NativeMiniMessageSerializer() {
    }

    static void init() {
        GlobalAdventureSerializer.globalSerializer(MINI_MESSAGE);
    }

}
