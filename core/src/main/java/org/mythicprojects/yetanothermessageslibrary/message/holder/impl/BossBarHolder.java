package org.mythicprojects.yetanothermessageslibrary.message.holder.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.adventure.GlobalAdventureSerializer;
import org.mythicprojects.yetanothermessageslibrary.message.holder.SendableHolder;
import org.mythicprojects.yetanothermessageslibrary.replace.ComponentReplacer;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.viewer.Viewer;

public class BossBarHolder extends SendableHolder {

    private final Component name;

    private final float progress;
    private final BossBar.Color color;
    private final BossBar.Overlay overlay;
    private final Set<BossBar.Flag> flags = new HashSet<>();
    private final int stay;

    private final boolean clearOtherBars;

    public BossBarHolder(@NotNull Component name, float progress, @NotNull BossBar.Color color, @NotNull BossBar.Overlay overlay, @NotNull Collection<BossBar.Flag> flags, int stay, boolean clearOtherBars) {
        this.name = name;
        this.progress = progress;
        this.color = color;
        this.overlay = overlay;
        this.clearOtherBars = clearOtherBars;
        this.flags.addAll(flags);
        this.stay = stay;
    }

    public @NotNull Component getName() {
        return this.name;
    }

    public float getProgress() {
        return this.progress;
    }

    public @NotNull BossBar.Color getColor() {
        return this.color;
    }

    public @NotNull BossBar.Overlay getOverlay() {
        return this.overlay;
    }

    public @NotNull Set<BossBar.Flag> getFlags() {
        return this.flags;
    }

    public int getStay() {
        return this.stay;
    }

    public boolean clearOtherBars() {
        return this.clearOtherBars;
    }

    public @NotNull BossBar prepareBossBar(@Nullable Locale locale, @NotNull Replaceable... replacements) {
        return BossBar.bossBar(ComponentReplacer.replace(locale, this.name, replacements), this.progress, this.color, this.overlay);
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        BossBar bossBar = this.prepareBossBar(locale, replacements);
        if (this.clearOtherBars) {
            viewer.clearBossBars();
        }
        viewer.sendBossBar(bossBar, this.stay);
    }

    @Override
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        return new BossBarHolder(
                ComponentReplacer.replace(this.name, replacements),
                this.progress,
                this.color,
                this.overlay,
                this.flags,
                this.stay,
                this.clearOtherBars
        );
    }

    public static @NotNull Builder builder(@NotNull Component name) {
        return new Builder(name);
    }

    public static @NotNull Builder builder(@NotNull String name) {
        return new Builder(GlobalAdventureSerializer.deserialize(name));
    }

    public static class Builder {

        private final Component name;

        private float progress = BossBar.MAX_PROGRESS;
        private BossBar.Color color = BossBar.Color.PINK;
        private BossBar.Overlay overlay = BossBar.Overlay.PROGRESS;
        private final Set<BossBar.Flag> flags = new HashSet<>();

        private int stay = -1;

        private boolean clearOtherBars = false;

        private Builder(@NotNull Component name) {
            this.name = name;
        }

        @Contract("_ -> this")
        public Builder progress(float progress) {
            if (progress < BossBar.MIN_PROGRESS || progress > BossBar.MAX_PROGRESS) {
                throw new IllegalArgumentException("progress must be between " + BossBar.MIN_PROGRESS + " and " + BossBar.MAX_PROGRESS + ", was " + progress);
            }
            this.progress = progress;
            return this;
        }

        @Contract("_ -> this")
        public Builder color(@NotNull BossBar.Color color) {
            this.color = color;
            return this;
        }

        @Contract("_ -> this")
        public Builder overlay(@NotNull BossBar.Overlay overlay) {
            this.overlay = overlay;
            return this;
        }

        @Contract("_ -> this")
        public Builder addFlag(@NotNull BossBar.Flag flag) {
            this.flags.add(flag);
            return this;
        }

        @Contract("_ -> this")
        public Builder addFlags(@NotNull Collection<BossBar.Flag> flags) {
            this.flags.addAll(flags);
            return this;
        }

        @Contract("_ -> this")
        public Builder stay(int stay) {
            this.stay = stay;
            return this;
        }

        @Contract("_ -> this")
        public Builder clearOtherBars(boolean clearOtherBars) {
            this.clearOtherBars = clearOtherBars;
            return this;
        }

        public @NotNull BossBarHolder build() {
            return new BossBarHolder(this.name, this.progress, this.color, this.overlay, this.flags, this.stay, this.clearOtherBars);
        }

    }

}
