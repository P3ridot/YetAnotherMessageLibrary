package org.mythicprojects.yetanothermessageslibrary;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mythicprojects.yetanothermessageslibrary.locale.LocaleProvider;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.replace.StringReplacer;

public interface MessageService<C extends MessageRepository> {

    /**
     * Get message from repository
     *
     * @param entity        entity to find locale for using registered {@link LocaleProvider}
     *                      use default locale if entity is null or no {@link LocaleProvider} is registered
     *                      can be {@link Locale} if you want to use specific locale
     * @param valueSupplier function to get message from repository
     * @param <T>           type of message
     * @return message
     */
    default <T> T get(@Nullable Object entity, @NotNull Function<@NotNull C, @Nullable T> valueSupplier) {
        Locale locale = this.getLocale(entity);
        return valueSupplier.apply(this.getRepository(locale));
    }


    /**
     * Get message from repository with default locale
     *
     * @param valueSupplier function to get message from repository
     * @param <T>           type of message
     * @return message
     */
    default <T> T get(@NotNull Function<@NotNull C, @Nullable T> valueSupplier) {
        return this.get(null, valueSupplier);
    }

    /**
     * Get message from repository and replace placeholders
     *
     * @param entity         entity to find locale for using registered {@link LocaleProvider}
     *                       use default locale if entity is null or no {@link LocaleProvider} is registered
     *                       can be {@link Locale} if you want to use specific locale
     * @param stringSupplier function to get message from repository
     * @param replacements   replacements to replace in message
     * @return message with replaced placeholders
     */
    default String get(@Nullable Object entity, @NotNull Function<@NotNull C, @Nullable String> stringSupplier, @NotNull Replaceable... replacements) {
        String string = this.get(entity, stringSupplier);
        if (string == null) {
            return null;
        }
        Locale locale = this.getLocale(entity);
        return StringReplacer.replace(locale, string, replacements);
    }

    /**
     * Get message from repository with default locale and replace placeholders
     *
     * @param entity       entity to find locale for using registered {@link LocaleProvider}
     *                     use default locale if entity is null or no {@link LocaleProvider} is registered
     *                     can be {@link Locale} if you want to use specific locale
     * @param listSupplier function to get message from repository
     * @param replacements replacements to replace in message
     * @return message with replaced placeholders
     */
    default List<String> getList(@Nullable Object entity, @NotNull Function<@NotNull C, @Nullable List<String>> listSupplier, @NotNull Replaceable... replacements) {
        List<String> stringList = this.get(entity, listSupplier);
        if (stringList == null || stringList.isEmpty()) {
            return stringList;
        }
        Locale locale = this.getLocale(entity);
        return StringReplacer.replace(locale, stringList, replacements);
    }

    /**
     * Set default locale that will be used if:
     * <ul>
     *     <li>entity is null</li>
     *     <li>no {@link LocaleProvider} is registered for entity type</li>
     *     <li>no {@link MessageRepository} is found for entity locale</li>
     * </ul>
     *
     * @return default locale
     */
    @NotNull Locale getDefaultLocale();

    /**
     * Set default locale as fallback - {@link #getDefaultLocale()}
     *
     * @param defaultLocale default locale
     */
    void setDefaultLocale(@NotNull Locale defaultLocale);

    /**
     * Get {@link LocaleProvider} for entity type
     *
     * @param entityType entity type
     * @return {@link LocaleProvider} or null if not found
     */
    @SuppressWarnings("rawtypes")
    @Nullable LocaleProvider getLocaleProvider(@NotNull Class<?> entityType);

    /**
     * Register {@link LocaleProvider} for entity type
     *
     * @param localeProvider {@link LocaleProvider}
     */
    void registerLocaleProvider(@NotNull LocaleProvider<?> localeProvider);

    /**
     * Get locale for entity
     *
     * @param entity entity to find locale for using registered {@link LocaleProvider}
     *               use default locale if entity is null or no {@link LocaleProvider} is registered
     *               can be {@link Locale} if you want to use specific locale
     * @return locale
     */
    @SuppressWarnings("unchecked")
    default @NotNull Locale getLocale(@Nullable Object entity) {
        if (entity == null) {
            return this.getDefaultLocale();
        }

        if (entity instanceof Locale) {
            return (Locale) entity;
        }

        LocaleProvider localeProvider = this.getLocaleProvider(entity.getClass());
        if (localeProvider == null) {
            return this.getDefaultLocale();
        }

        Locale locale = localeProvider.getLocale(entity);
        if (locale == null) {
            return this.getDefaultLocale();
        }
        return locale;
    }

    /**
     * Get all registered message repositories in unmodifiable map
     *
     * @return message repositories
     */
    @NotNull @Unmodifiable Map<Locale, C> getMessageRepositories();

    /**
     * Get message repository for locale
     *
     * @param locale locale
     * @return message repository
     */
    @NotNull C getRepository(@NotNull Locale locale);

    /**
     * Register message repository for locale
     *
     * @param locale            locale
     * @param messageRepository message repository
     */
    void registerRepository(@NotNull Locale locale, @NotNull C messageRepository);

    /**
     * Register message repository for locale and set it as default
     *
     * @param defaultLocale     default locale
     * @param messageRepository message repository
     */
    default void registerDefaultRepository(@NotNull Locale defaultLocale, @NotNull C messageRepository) {
        this.setDefaultLocale(defaultLocale);
        this.registerRepository(defaultLocale, messageRepository);
    }

}
