package org.mythicprojects.yetanothermessageslibrary.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mythicprojects.yetanothermessageslibrary.replace.Replaceable;
import org.mythicprojects.yetanothermessageslibrary.util.TriFunction;
import org.mythicprojects.yetanothermessageslibrary.viewer.Viewer;
import org.mythicprojects.yetanothermessageslibrary.viewer.ViewerService;

@SuppressWarnings("unchecked")
public class SimpleMessageDispatcher<RECEIVER, DISPATCHER extends SimpleMessageDispatcher<RECEIVER, DISPATCHER>>
        implements MessageDispatcher<RECEIVER, DISPATCHER> {

    private final ViewerService<RECEIVER> viewerService;
    private final Function<Object, Locale> localeSupplier;
    private final Function<Object, Sendable> messageSupplier;

    private final Set<RECEIVER> receivers = Collections.newSetFromMap(new WeakHashMap<>());
    private final Set<Predicate<RECEIVER>> predicates = new HashSet<>();
    private final Set<Consumer<RECEIVER>> actions = new HashSet<>();

    private final Map<String, Object> fields = new WeakHashMap<>();
    private final List<Replaceable> replacements = new ArrayList<>();
    @SuppressWarnings("rawtypes")
    private final List<ReplaceableSupplier> replacementSuppliers = new ArrayList<>();

    public SimpleMessageDispatcher(
            @NotNull ViewerService<RECEIVER> viewerService,
            @NotNull Function<@Nullable Object, @NotNull Locale> localeSupplier,
            @NotNull Function<@Nullable Object, @Nullable Sendable> messageSupplier
    ) {
        this.viewerService = viewerService;
        this.localeSupplier = localeSupplier;
        this.messageSupplier = messageSupplier;
    }

    @Contract("_ -> this")
    public DISPATCHER receiver(@Nullable RECEIVER receiver) {
        if (receiver == null) {
            return (DISPATCHER) this;
        }

        this.receivers.add(receiver);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER predicate(@NotNull Predicate<@NotNull RECEIVER> predicate) {
        this.predicates.add(predicate);
        return (DISPATCHER) this;
    }

    @Contract("_, _ -> this")
    public DISPATCHER field(@NotNull String key, @Nullable Object value) {
        this.fields.put(key, value);
        return (DISPATCHER) this;
    }

    @Contract("_, -> this")
    public DISPATCHER fields(@NotNull Map<@NotNull String, @Nullable Object> fields) {
        this.fields.putAll(fields);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER with(@NotNull Replaceable replacement) {
        this.replacements.add(replacement);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER with(@NotNull Replaceable @NotNull ... replacement) {
        Collections.addAll(this.replacements, replacement);
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER with(@NotNull Iterable<? extends @NotNull Replaceable> replacements) {
        replacements.forEach(this.replacements::add);
        return (DISPATCHER) this;
    }

    @Contract("_, _, _ -> this")
    public <T extends RECEIVER> DISPATCHER with(
            @Nullable Class<T> requiredType,
            @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> replacementSupplier,
            @Nullable TriFunction<@NotNull RECEIVER, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier
    ) {
        this.replacementSuppliers.add(new ReplaceableSupplier<>(requiredType, replacementSupplier, fallbackSupplier));
        return (DISPATCHER) this;
    }

    @Contract("_ -> this")
    public DISPATCHER action(@NotNull Consumer<@NotNull RECEIVER> action) {
        this.actions.add(action);
        return (DISPATCHER) this;
    }

    @Contract(" -> this")
    public DISPATCHER send() {
        this.receivers.forEach(this::sendTo);
        return (DISPATCHER) this;
    }

    private void sendTo(@NotNull RECEIVER receiver) {
        Locale locale = this.localeSupplier.apply(receiver);

        Sendable message = this.messageSupplier.apply(locale);
        if (message == null) {
            return;
        }

        if (this.predicates.stream().anyMatch(predicate -> !predicate.test(receiver))) {
            return;
        }

        Viewer viewer = this.viewerService.findOrCreateViewer(receiver);
        List<Replaceable> replaceables = this.prepareReplacements(receiver, locale);
        message.send(locale, viewer, replaceables.toArray(new Replaceable[0]));
        this.actions.forEach(action -> action.accept(receiver));
    }

    protected @NotNull List<Replaceable> prepareReplacements(@NotNull RECEIVER receiver, @NotNull Locale locale) {
        List<Replaceable> replacement = new ArrayList<>(this.replacements);
        this.replacementSuppliers
                .stream()
                .map(supplier -> supplier.supplyReplacement(
                        receiver,
                        locale,
                        this.fields
                ))
                .filter(Objects::nonNull)
                .forEachOrdered(replacement::add);
        return replacement;
    }

    private class ReplaceableSupplier<T extends RECEIVER> {

        private final Class<T> entityType;
        private final TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> supplier;
        private final TriFunction<@NotNull RECEIVER, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier;

        private ReplaceableSupplier(
                @Nullable Class<T> entityType,
                @NotNull TriFunction<@NotNull T, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> supplier,
                @Nullable TriFunction<@NotNull RECEIVER, @NotNull Locale, @NotNull Map<@NotNull String, @Nullable Object>, ? extends @NotNull Replaceable> fallbackSupplier
        ) {
            this.entityType = entityType;
            this.supplier = supplier;
            this.fallbackSupplier = fallbackSupplier;
        }

        public @Nullable Replaceable supplyReplacement(@NotNull RECEIVER receiver, @NotNull Locale locale, @NotNull Map<@NotNull String, @Nullable Object> fields) {
            boolean needCast = this.entityType != null;
            if (needCast && !this.entityType.isInstance(receiver)) {
                return this.fallbackSupplier != null
                        ? this.fallbackSupplier.apply(receiver, locale, fields)
                        : null;
            }

            T entity = needCast
                    ? this.entityType.cast(receiver)
                    : (T) receiver;
            return this.supplier.apply(entity, locale, fields);
        }

    }

}
