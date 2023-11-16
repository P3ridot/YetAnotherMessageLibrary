package org.mythicprojects.yetanothermessageslibrary;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mythicprojects.yetanothermessageslibrary.locale.LocaleProvider;
import org.mythicprojects.yetanothermessageslibrary.util.Validate;

public abstract class SimpleMessageService<C extends MessageRepository> implements MessageService<C> {

    private Locale defaultLocale = Locale.getDefault();
    private final Collection<LocaleProvider<?>> localeProviders = new LinkedHashSet<>();
    private final Map<Locale, C> messageRepositories = new LinkedHashMap<>();

    @Override
    public @NotNull Locale getDefaultLocale() {
        return this.defaultLocale;
    }

    @Override
    public void setDefaultLocale(@NotNull Locale defaultLocale) {
        Validate.notNull(defaultLocale, "Default locale cannot be null");
        this.defaultLocale = defaultLocale;
    }

    @Override
    public @Nullable LocaleProvider getLocaleProvider(@NotNull Class<?> entityType) {
        return this.localeProviders
                .stream()
                .filter(localeProvider -> localeProvider.supports(entityType))
                .findAny()
                .orElse(null);
    }

    @Override
    public void registerLocaleProvider(@NotNull LocaleProvider<?> localeProvider) {
        Validate.notNull(localeProvider, "Locale provider cannot be null");
        this.localeProviders.add(localeProvider);
    }

    @Override
    public @NotNull @Unmodifiable Map<Locale, C> getMessageRepositories() {
        return Collections.unmodifiableMap(this.messageRepositories);
    }

    @Override
    public @NotNull C getRepository(@NotNull Locale locale) {
        C messageRepository = this.messageRepositories.get(locale);
        if (messageRepository == null) {
            // If we can't find message repository for language wariant (for e.g. en_GB) we will try to find message repository for language (for e.g. en)
            messageRepository = this.messageRepositories.get(Locale.forLanguageTag(locale.getLanguage()));
        }

        if (messageRepository == null) {
            messageRepository = this.messageRepositories.get(this.getDefaultLocale());
        }

        if (messageRepository == null) {
            throw new RuntimeException("No message repository found for locale " + locale);
        }

        return messageRepository;
    }

    @Override
    public void registerRepository(@NotNull Locale locale, @NotNull C messageRepository) {
        Validate.notNull(locale, "Locale cannot be null");
        Validate.notNull(messageRepository, "Message repository cannot be null");
        this.messageRepositories.put(locale, messageRepository);
    }

}
