package org.mythicprojects.yetanothermessageslibrary.example;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import java.io.File;
import java.util.Locale;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.mythicprojects.yetanothermessageslibrary.BukkitMessageService;
import org.mythicprojects.yetanothermessageslibrary.SendableMessageService;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.config.serdes.YAMLSerdes;
import org.mythicprojects.yetanothermessageslibrary.example.complex.ExampleMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.example.complex.ExampleMessageService;
import org.mythicprojects.yetanothermessageslibrary.example.config.MessageConfiguration;
import org.mythicprojects.yetanothermessageslibrary.locale.BukkitPlayerLocaleProvider;
import org.mythicprojects.yetanothermessageslibrary.message.BukkitMessageDispatcher;
import org.mythicprojects.yetanothermessageslibrary.viewer.BukkitViewerDataSupplier;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerFactory;

public class ExamplePlugin extends JavaPlugin {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    private final File englishMessageFile = new File(this.getDataFolder(), "en.yml");
    private final File polishMessageFile = new File(this.getDataFolder(), "pl.yml");

    private BukkitAudiences adventure;

    // Simple way
    private SendableMessageService<CommandSender, MessageConfiguration, BukkitMessageDispatcher<?>> simpleMessageService;

    // Complex way
    private SendableMessageService<CommandSender, MessageConfiguration, ExampleMessageDispatcher> complexMessageService;

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);

        // Register global serializer (used for e.g. by string replacements)
        GlobalAdventureSerializer.globalSerializer(MINI_MESSAGE);

        // Simple way
        this.simpleMessageService = new BukkitMessageService<>(this, this.adventure);
        this.simpleMessageService.registerDefaultRepository(Locale.ENGLISH, prepareMessageConfiguration(this.englishMessageFile));

        // Complex way
        this.complexMessageService = new ExampleMessageService(
                new BukkitViewerDataSupplier(this.adventure),
                ViewerFactory.create(BukkitMessageService.wrapScheduler(this)),
                ExampleMessageDispatcher::new
        );
        this.complexMessageService.registerLocaleProvider(new BukkitPlayerLocaleProvider());
        this.complexMessageService.registerDefaultRepository(Locale.ENGLISH, prepareMessageConfiguration(this.englishMessageFile));
        this.complexMessageService.registerRepository(Locale.forLanguageTag("pl"), prepareMessageConfiguration(this.polishMessageFile));

        this.getServer().getPluginManager().registerEvents(new ExampleListener(this.simpleMessageService, this.complexMessageService), this);
    }

    @Override
    public void onDisable() {
        this.adventure.close();
    }

    private static MessageConfiguration prepareMessageConfiguration(File file) {
        return ConfigManager.create(MessageConfiguration.class, it -> {
           it.withConfigurer(new YamlBukkitConfigurer());
           it.withSerdesPack(new YAMLSerdes());

           it.withBindFile(file);
           it.saveDefaults();
           it.load(true);
        });
    }

}
