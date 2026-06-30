/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static oolang.symbol.table.TypeResolver.resolveKlass;
import static oolang.symbol.table.TypeResolver.resolveType;

@SuppressWarnings("preview")
final class ExistingKlass implements Klass {
    private final @NonNull Class<?> javaClass;
    private final @NonNull LazyConstant<@NonNull Set<Class<?>>> parents;

    ExistingKlass(final @NonNull Class<?> javaClass) {
        assert javaClass != null;

        this.javaClass = javaClass;
        this.parents = LazyConstant.of(() -> getAllParents(javaClass));
    }

    /**
     * Recursively finds all superclasses and interfaces {@link #javaClass} extends/implements.
     */
    private static Set<Class<?>> getAllParents(final @NonNull Class<?> clazz) {
        final var parents = new HashSet<@NonNull Class<?>>();
        // Traverse superclasses (recursively)
        processSuperclass(clazz, parents);
        // Traverse interfaces (recursively)
        processInterfaces(clazz, parents);
        return parents;
    }

    // Recursively collect all superclasses
    private static void processSuperclass(final @NonNull Class<?> clazz,
                                          final @NonNull Set<@NonNull Class<?>> parents) {
        assert clazz != null;
        assert parents != null;

        final var superclass = clazz.getSuperclass();
        if (superclass != null) {
            parents.add(superclass); // Add direct superclass
            processSuperclass(superclass, parents); // Recurse on the superclass's superClass
            processInterfaces(superclass, parents); // Recurse on the superclass's superinterfaces
        }
    }

    // Recursively collect all interfaces and their superinterfaces
    private static void processInterfaces(final @NonNull Class<?> clazz,
                                          final @NonNull Set<@NonNull Class<?>> parents) {
        assert clazz != null;
        assert parents != null;

        final var interfaces = clazz.getInterfaces();
        for (final var interface_ : interfaces) {
            // Add the interface only if it's not already in the set (avoids duplicates)
            if (parents.add(interface_)) {
                processInterfaces(interface_, parents); // Recurse on the interface's superinterfaces
            }
        }
    }

    @Override
    public boolean isAssignableFrom(final @NonNull Type otherType) {
        if (!(otherType instanceof ExistingKlass otherKlass)) {
            return false;
        }
        return this.descriptorString().equals(otherKlass.descriptorString()) ||
                this.parents.get().stream().anyMatch(superType ->
                        superType.descriptorString().equals(otherKlass.descriptorString()));
    }

    @Override
    public @Nullable Type accessibleProperty(final @NonNull String name) {
        assert name != null;

        return accessibleProperty(javaClass, name);
    }

    private @Nullable Type accessibleProperty(final @NonNull Class<?> currentClazz, final @NonNull String name) {
        assert currentClazz != null;
        assert name != null;

        try {
            final var field = currentClazz.getDeclaredField(name);
            if (Modifier.isPrivate(field.getModifiers())) {
                throw new NoSuchFieldException();
            }
            if (Modifier.isPublic(field.getModifiers())
                    // protected or package-protected within the same package
                    || javaClass.getPackageName().equals(currentClazz.getPackageName())
                    // protected in a superclass
                    || (Modifier.isProtected(field.getModifiers()) && parents.get().contains(currentClazz))) {
                return resolveType(field.getGenericType());
            }
            throw new NoSuchFieldException();
        } catch (NoSuchFieldException e) {
            // Try superclass, no need to check interfaces since they can't have fields
            final var superClass = currentClazz.getSuperclass();
            if (superClass == null) {
                return null;
            } else {
                return accessibleProperty(superClass, name);
            }
        }
    }

    @Override
    public @NonNull String descriptorString() {
        return javaClass.descriptorString();
    }

    @Override
    public @NonNull Klass arrayType() {
        return resolveKlass(javaClass.arrayType());
    }
}
