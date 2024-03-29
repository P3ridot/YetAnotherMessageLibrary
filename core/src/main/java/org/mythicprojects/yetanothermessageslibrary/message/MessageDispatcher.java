package org.mythicprojects.yetanothermessageslibrary.message;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.replace.replacement.Replacement;
import org.mythicprojects.yetanothermessageslibrary.util.TriFunction;

@SuppressWarnings("unchecked")
public interface MessageDispatcher<RECEIVER, DISPATCHER extends MessageDispatcher<RECEIVER, ? extends DISPATCHER>> {

    /**
     * Add a receiver to the dispatcher
     * @param receiver the receiver
     * @return this dispatcher
     */
    @Contract("_ -> this")
    DISPATCHER receiver(@Nullable RECEIVER receiver);

    /**
     * Add multiple receivers to the dispatcher
     * @param receivers the receivers
     * @return this dispatcher
     */
    @Contract("_ -> this")
    default DISPATCHER receivers(@NotNull Collection<? extends @Nullable RECEIVER> receivers) {
        receivers.forEach(this::receiver);
        return (DISPATCHER) this;
    }

    /**
     * Add multiple receivers to the dispatcher
     * @param receivers the receivers
     * @return this dispatcher
     */
    @Contract("_ -> this")
    default DISPATCHER receivers(@NotNull RECEIVER... receivers) {
        for (RECEIVER receiver : receivers) {
            this.receiver(receiver);
        }
        return (DISPATCHER) this;
    }

    /**
     * Add all players to the dispatcher
     * @return this dispatcher
     * @throws UnsupportedOperationException if the operation is not implemented in the current MessageDispatcher
     */
    @Contract(" -> this")
    default DISPATCHER all() throws UnsupportedOperationException {
        this.allPlayers();
        this.console();
        return (DISPATCHER) this;
    }

    /**
     * Add all players to the dispatcher
     * @return this dispatcher
     * @throws UnsupportedOperationException if the operation is not implemented in the current MessageDispatcher
     */
    @Contract(" -> this")
    default DISPATCHER allPlayers() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("allPlayers is not supported by this MessageDispatcher");
    }

    /**
     * Add all viewers to the dispatcher
     * @return this dispatcher
     * @throws UnsupportedOperationException if the operation is not implemented in the current MessageDispatcher
     */
    @Contract(" -> this")
    default DISPATCHER console() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("console is not supported by this MessageDispatcher");
    }

    /**
     * Add a predicate to the dispatcher
     * @param predicate the predicate
     * @return this dispatcher
     */
    @Contract("_ -> this")
    DISPATCHER predicate(@NotNull Predicate<RECEIVER> predicate);

    /**
     * Add a predicate to the dispatcher for a specific receiver class
     * @param receiverClass the class of the receiver
     * @param predicate the predicate
     * @param <T> the type of the receiver
     * @return this dispatcher
     */
    @Contract("_, _ -> this")
    default <T extends DISPATCHER> DISPATCHER predicate(@NotNull Class<T> receiverClass, @NotNull Predicate<T> predicate) {
        return this.predicate(receiver -> {
            if (!receiverClass.isInstance(receiver)) {
                return false;
            }
            return predicate.test(receiverClass.cast(receiver));
        });
    }

    /**
     * Add a field to the dispatcher to use by the replacements
     * @param key the key of the field
     * @param value the value of the field
     * @return this dispatcher
     */
    @Contract("_, _ -> this")
    DISPATCHER field(@NotNull String key, @Nullable Object value);

    /**
     * Add multiple fields to the dispatcher to use by the replacements
     * @param fields the fields
     * @return this dispatcher
     */
    @Contract("_ -> this")
    DISPATCHER fields(@NotNull Map<String, Object> fields);

    @Contract("_ -> this")
    DISPATCHER with(@NotNull Replaceable replacement);

    @Contract("_ -> this")
    DISPATCHER with(@NotNull Replaceable @NotNull ... replacement);

    @Contract("_ -> this")
    DISPATCHER with(@NotNull Iterable<? extends @NotNull Replaceable> replacements);

    default DISPATCHER with(@NotNull String placeholder, @NotNull ComponentLike replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    default DISPATCHER with(@NotNull Pattern placeholder, @NotNull ComponentLike replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    default DISPATCHER with(@NotNull String placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    default DISPATCHER with(@NotNull Pattern placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull ComponentLike> replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    default DISPATCHER with(@NotNull String placeholder, @NotNull Supplier<? extends @NotNull ComponentLike> replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    default DISPATCHER with(@NotNull Pattern placeholder, @NotNull Supplier<? extends @NotNull ComponentLike> replacement) {
        return this.with(Replacement.component(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER with(@NotNull String placeholder, @NotNull Object replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER with(@NotNull Pattern placeholder, @NotNull Object replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER withString(@NotNull String placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull Object> replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER withString(@NotNull Pattern placeholder, @NotNull Function<@Nullable Locale, ? extends @NotNull Object> replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER withString(@NotNull String placeholder, @NotNull Supplier<? extends @NotNull Object> replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _ -> this")
    default DISPATCHER withString(@NotNull Pattern placeholder, @NotNull Supplier<? extends @NotNull Object> replacement) {
        return this.with(Replacement.string(placeholder, replacement));
    }

    @Contract("_, _, _ -> this")
    <T extends RECEIVER> DISPATCHER with(
            @NotNull Class<T> requiredType,
            @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> replacementSupplier,
            @Nullable TriFunction<@NotNull RECEIVER, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier
    );

    @Contract("_, _ -> this")
    default <T extends RECEIVER> DISPATCHER with(
            @NotNull Class<T> requiredType,
            @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> replacementSupplier
    ) {
        return this.with(requiredType, replacementSupplier, null);
    }

    @Contract("_, _, _ -> this")
    default <T extends RECEIVER> DISPATCHER with(
            @NotNull Class<T> requiredType,
            @NotNull Function<@NotNull T, ? extends @NotNull Replaceable> replacementSupplier,
            @Nullable Function<@NotNull RECEIVER, ? extends @NotNull Replaceable> fallbackSupplier
    ) {
        return this.with(
                requiredType,
                (receiver, locale, fields) -> replacementSupplier.apply(receiver),
                fallbackSupplier != null
                        ? (receiver, locale, fields) -> fallbackSupplier.apply(receiver)
                        : null
        );
    }

    @Contract("_, _ -> this")
    default <T extends RECEIVER> DISPATCHER with(
            @NotNull Class<T> requiredType,
            @NotNull Function<@NotNull T, ? extends @NotNull Replaceable> replacementSupplier
    ) {
        return this.with(requiredType, replacementSupplier, null);
    }

    @Contract("_, _ -> this")
    default <T extends RECEIVER> DISPATCHER with(
            @NotNull Class<T> requiredType,
            @NotNull Collection<Function<@NotNull T, ? extends @NotNull Replaceable>> replacementSuppliers
    ) {
        replacementSuppliers.forEach(replacementSupplier -> this.with(requiredType, replacementSupplier));
        return (DISPATCHER) this;
    }

    /**
     * Add an action to the dispatcher that will be executed after the message is sent
     * @param action the action
     * @return this dispatcher
     */
    @Contract("_ -> this")
    DISPATCHER action(@NotNull Consumer<RECEIVER> action);

    /**
     * Send the message to all receivers
     * @return this dispatcher
     */
    @Contract(" -> this")
    DISPATCHER send();

}
