/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.ast.AstType;
import oolang.symbol.table.Type.GenericType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

import static oolang.ast.Identifier.MultipleIdentifier;
import static oolang.ast.Identifier.SimpleIdentifier;
import static oolang.symbol.table.Klass.KLASSES_CACHE;

final class TypeResolver {
    // un-instantiable
    private TypeResolver() {
    }

    static @NonNull Type resolveType(final java.lang.reflect.@NonNull Type javaType) {
        assert javaType != null;

        return switch (javaType) {
            case Class<?> javaClass -> resolveKlass(javaClass);
            // todo ParameterizedType, array type etc.
            default -> throw new UnsupportedOperationException();
        };
    }

    static @NonNull Klass resolveKlass(final @NonNull Class<?> javaClass) {
        assert javaClass != null;

        // todo fast-path for primitive and most often used classes ?
        return KLASSES_CACHE.get().computeIfAbsent(javaClass.getName(), ignored -> new ExistingKlass(javaClass));
    }

    static @NonNull Type resolveType(final @Nullable AstType astType,
                                     final @NonNull Map<String, String> imports,
                                     final boolean isArray) {
        assert imports != null;

        // no type = void
        if (astType == null) {
            return VOID_KLASS;
        }

        // generic type with parameters
        if (astType.parameters != null) {
            final var klass = resolveKlass(astType, imports, isArray);

            // Array pseudo-class specific case
            if (klass == Array.INSTANCE) {
                if (astType.parameters.size() != 1) {
                    // todo throw a semantic analysis exception
                    throw new RuntimeException("An array must have exactly one parameter");
                }
                return resolveType(astType.parameters.getFirst(), imports, true);
            }

            return new GenericType(
                    klass,
                    astType.parameters.stream()
                            .map(parameterType -> resolveType(parameterType, imports, false))
                            .toArray(Type[]::new)
            );
        }

        // simple type
        return resolveKlass(astType, imports, isArray);
    }

    private static @NonNull Klass resolveKlass(final @NonNull AstType astType,
                                               final @NonNull Map<String, String> imports,
                                               final boolean isArray) {
        assert astType != null;
        assert imports != null;

        final var klass = switch (astType.identifier) {
            // must resolve the class name
            case SimpleIdentifier simpleId -> resolveKlassFromSimpleName(simpleId.identifier, imports);
            // fully qualified class name
            case MultipleIdentifier multipleId -> getKlassFromName(multipleId.rawName());
        };

        return (isArray) ? klass.arrayType() : klass;
    }

    private static @NonNull Klass resolveKlassFromSimpleName(final @NonNull String simpleKlassName,
                                                             final @NonNull Map<String, String> imports) {
        assert simpleKlassName != null;
        assert imports != null;

        // 1) todo first try to resolve sibling class

        // 2) try to get the fully qualified class name from an import
        final var fullKlassName = imports.get(simpleKlassName);

        // 3) fast-path for primitives and most often used classes and for the Array pseudo-class, no cache call
        if (fullKlassName == null) {
            switch (simpleKlassName) {
                case STRING_KLASS_NAME -> {
                    return STRING_KLASS;
                }
                case INT_KLASS_NAME -> {
                    return INT_KLASS;
                }
                case ARRAY_KLASS_NAME -> {
                    return Array.INSTANCE;
                }
                case VOID_KLASS_NAME -> {
                    return VOID_KLASS;
                }
            }
        }

        return getKlassFromName((fullKlassName != null) ? fullKlassName
                // 4) classes from the java.lang package don't need to be imported
                : "java.lang." + simpleKlassName);
    }

    /**
     * <b>Important note:</b> this method requires that the symbol table was built for all Oolang files, and therefore
     * the corresponding {@link OolangKlass} is present in the {@linkplain Klass#KLASSES_CACHE klass cache}.
     */
    private static @NonNull Klass getKlassFromName(final @NonNull String klassName) {
        assert klassName != null;

        final var classType = KLASSES_CACHE.get().get(klassName);
        if (classType != null) {
            return classType;
        }

        try {
            final var existingKlass = Class.forName(klassName);
            return new ExistingKlass(existingKlass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e); // todo throw a semantic analysis exception
        }
    }

    // Array pseudo-class
    private static final @NonNull String ARRAY_KLASS_NAME = "Array";

    // --------------------------------------
    // Primitive and most often used classes.
    // --------------------------------------

    // Void
    private static final @NonNull Klass VOID_KLASS = initKlass(Void.TYPE);
    private static final @NonNull String VOID_KLASS_NAME = "Void";
    // Int
    private static final @NonNull Klass INT_KLASS = initKlass(Integer.TYPE);
    private static final @NonNull String INT_KLASS_NAME = "Int";
    // String
    private static final @NonNull Klass STRING_KLASS = initKlass(String.class);
    private static final @NonNull String STRING_KLASS_NAME = "String";

    private static @NonNull Klass initKlass(final @NonNull Class<?> javaClass) {
        assert javaClass != null;

        final var klass = new ExistingKlass(javaClass);
        KLASSES_CACHE.get().put(javaClass.getName(), klass);
        return klass;
    }
}
