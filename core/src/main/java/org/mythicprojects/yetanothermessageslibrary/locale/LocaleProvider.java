package org.mythicprojects.yetanothermessageslibrary.locale;

import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LocaleProvider<T> {

    boolean supports(@NotNull Class<?> clazz);

    @Nullable Locale getLocale(@NotNull T entity);

    static <R> LocaleProvider<R> staticProvider(@NotNull Locale locale) {
        return new LocaleProvider<R>() {
            @Override
            public boolean supports(@NotNull Class<?> clazz) {
                return true;
            }

            @Override
            public @NotNull Locale getLocale(@NotNull R entity) {
                return locale;
            }
        };
    }

}
