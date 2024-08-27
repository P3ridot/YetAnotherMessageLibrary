package dev.peri.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.minimessage.MiniMessage;

final class NativeMiniMessageSerializer {

    private NativeMiniMessageSerializer() {
    }

    static void init() {
        GlobalAdventureSerializer.globalSerializer(MiniMessage.miniMessage());
    }

}
