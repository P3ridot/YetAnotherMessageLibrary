package dev.peri.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

final class NativeLegacySerializer {

    private NativeLegacySerializer() {
    }

    static void init() {
        GlobalAdventureSerializer.globalSerializer(LegacyComponentSerializer.legacySection());
    }

}
