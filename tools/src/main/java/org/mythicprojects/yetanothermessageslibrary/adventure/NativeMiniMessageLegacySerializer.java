package org.mythicprojects.yetanothermessageslibrary.adventure;

import java.util.regex.Pattern;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

final class NativeMiniMessageLegacySerializer {

    private static final Pattern SECTION_COLOR_PATTERN = Pattern.compile("(?i)§([0-9A-FK-OR])");
    private static final Pattern ALL_TEXT_PATTERN = Pattern.compile(".*");

    private static final Pattern HEX_TO_LEGACY_PATTERN = Pattern.compile("&#([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})([0-9A-Fa-f]{1})");
    private static final String LEGACY_HEX_REPLACEMENT = "&x&$1&$2&$3&$4&$5&$6";

    private static final LegacyComponentSerializer LEGACY_AMPERSAND = LegacyComponentSerializer.legacyAmpersand();
    private static final TextReplacementConfig COLOR_REPLACEMENTS = TextReplacementConfig.builder()
            .match(ALL_TEXT_PATTERN)
            .replacement((result, input) -> LEGACY_AMPERSAND.deserialize(result.group()))
            .build();
    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .preProcessor(text -> {
                String processedText = HEX_TO_LEGACY_PATTERN.matcher(text).replaceAll(LEGACY_HEX_REPLACEMENT);  // convert simple hex format to legacy
                processedText = SECTION_COLOR_PATTERN.matcher(processedText).replaceAll("&$1"); // convert section to ampersand
                return processedText;
            })
            .postProcessor(component -> component.replaceText(COLOR_REPLACEMENTS))
            .build();

    private NativeMiniMessageLegacySerializer() {
    }

    static void init() {
        GlobalAdventureSerializer.globalSerializer(MINI_MESSAGE);
    }

}
