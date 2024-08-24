package dev.peri.yetanothermessageslibrary.message.holder.impl;

import dev.peri.yetanothermessageslibrary.message.holder.SendableHolder;
import dev.peri.yetanothermessageslibrary.replace.Replaceable;
import dev.peri.yetanothermessageslibrary.viewer.Viewer;
import java.util.Locale;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoundHolder extends SendableHolder {

    private final Sound sound;
    private final boolean stopOtherSounds;

    public SoundHolder(@NotNull Sound sound, boolean stopOtherSounds) {
        this.sound = sound;
        this.stopOtherSounds = stopOtherSounds;
    }

    public @NotNull Sound getSound() {
        return this.sound;
    }

    public boolean stopOtherSounds() {
        return this.stopOtherSounds;
    }

    @Override
    public void send(@Nullable Locale locale, @NotNull Viewer viewer, @NotNull Replaceable... replacements) {
        if (this.stopOtherSounds) {
            viewer.stopSounds();
        }
        viewer.sendSound(this.sound);
    }

    @Override
    public @NotNull SendableHolder copy(@NotNull Replaceable... replacements) {
        return new SoundHolder(this.sound, this.stopOtherSounds);
    }

}
