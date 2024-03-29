package org.mythicprojects.yetanothermessageslibrary.adventure;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

final class NativeLegacySerializer {

    private static final LegacyComponentSerializer LEGACY_AMPERSAND = LegacyComponentSerializer.legacyAmpersand();

    private NativeLegacySerializer() {
    }

    static void init() {
        GlobalAdventureSerializer.globalSerializer(LEGACY_AMPERSAND);
    }

}
