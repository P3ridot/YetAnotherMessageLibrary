package org.mythicprojects.yetanothermessageslibrary.viewer;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

public class Viewer {

    private final Audience audience;
    private final boolean console;
    private final Set<BossBar> bossBars = ConcurrentHashMap.newKeySet();
    private final BiConsumer<Runnable, Long> schedule;

    public Viewer(@NotNull Audience audience, boolean console, @NotNull BiConsumer<Runnable, Long> schedule) {
        this.audience = audience;
        this.console = console;
        this.schedule = schedule;
    }

    @NotNull
    public Audience getAudience() {
        return this.audience;
    }

    public boolean isConsole() {
        return this.console;
    }

    public void sendChatMessage(@NotNull Collection<Component> messages) {
        messages.forEach(this.audience::sendMessage);
    }

    public void sendActionBar(@NotNull Component message) {
        this.audience.sendActionBar(message);
    }

    public void sendTitle(@NotNull Title title) {
        this.audience.showTitle(title);
    }

    public void sendBossBar(@NotNull BossBar bossBar, long stay) {
        this.audience.showBossBar(bossBar);
        this.bossBars.add(bossBar);

        if (stay < 0) {
            return;
        }

        this.schedule.accept(() -> {
            if (this.bossBars.remove(bossBar)) {
                this.audience.hideBossBar(bossBar);
            }
        }, (long) stay);
    }

    public void clearBossBars() {
        for (BossBar bossBar : this.bossBars) {
            this.audience.hideBossBar(bossBar);
            this.bossBars.remove(bossBar);
        }
    }

    public void sendSound(@NotNull Sound sound) {
        this.audience.playSound(sound);
    }

    public void stopSounds() {
        this.audience.stopSound(SoundStop.all());
    }

}
