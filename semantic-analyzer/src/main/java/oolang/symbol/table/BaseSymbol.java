/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.lang.invoke.TypeDescriptor;
import java.util.List;
import java.util.stream.Collectors;

public sealed class BaseSymbol permits BaseSymbol.Property, BaseSymbol.Function, OolangKlass {
    public final @NonNull String name;
    public final @NonNull Visibility visibility;
    public final boolean isStatic;
    public final boolean isAbstract;
    private final boolean isFinal;

    BaseSymbol(final @NonNull BaseSymbol baseSymbol) {
        assert baseSymbol != null;
        this(baseSymbol.name, baseSymbol.visibility, baseSymbol.isStatic, baseSymbol.isAbstract, baseSymbol.isFinal);
    }

    BaseSymbol(final @NonNull String name,
               final @NonNull Visibility visibility,
               final boolean isStatic,
               final boolean isAbstract,
               final boolean isFinal) {
        assert name != null;
        assert visibility != null;

        this.name = name;
        this.visibility = visibility;
        this.isStatic = isStatic;
        this.isAbstract = isAbstract;
        this.isFinal = isFinal;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public static final class Property extends BaseSymbol implements Variable, TypeDescriptor {
        private final @Nullable LazyType lazyType;
        private final @Nullable Type type;

        Property(final @NonNull BaseSymbol baseSymbol,
                 final @NonNull LazyType lazyType) {
            assert baseSymbol != null;
            assert lazyType != null;
            super(baseSymbol);

            this.lazyType = lazyType;
            this.type = null;
        }

        Property(final @NonNull String name,
                 final @NonNull Visibility visibility,
                 final boolean isStatic,
                 final boolean isAbstract,
                 final boolean isFinal,
                 final @NonNull Type type) {
            assert name != null;
            assert visibility != null;
            assert type != null;
            super(name, visibility, isStatic, isAbstract, isFinal);

            this.type = type;
            this.lazyType = null;
        }

        @Override
        public @NonNull Type type() {
            if (type != null) {
                return type;
            }
            assert lazyType != null;
            return lazyType.type();
        }

        @Override
        public String descriptorString() {
            return TypeResolver.getKlassFromType(type()).descriptorString();
        }
    }

    /**
     * An interface representing the most basic function symbol, to allow quick O(log n) range search by name.
     */
    public sealed interface BaseFunction extends Comparable<BaseFunction> permits Function, Klass.SimpleFunction {
        @NonNull String name();

        @Override
        default int compareTo(final @NonNull BaseFunction other) {
            assert other != null;
            return name().compareTo(other.name());
        }
    }

    @SuppressWarnings("preview")
    public static final class Function extends BaseSymbol implements BaseFunction, TypeDescriptor {
        public final @NonNull Klass declaringKlass;
        private final @Nullable LazyType lazyReturnType;
        private final @Nullable Type returnType;
        public final @NonNull List<@NonNull Parameter> parameters;
        private final @NonNull LazyConstant<@NonNull String> descriptorString =
                LazyConstant.of(this::buildDescriptorString);

        Function(final @NonNull Klass declaringKlass,
                 final @NonNull BaseSymbol baseSymbol,
                 final @NonNull LazyType lazyReturnType,
                 final @NonNull List<@NonNull Parameter> parameters) {
            assert declaringKlass != null;
            assert baseSymbol != null;
            assert lazyReturnType != null;
            assert parameters != null;
            super(baseSymbol);

            this.declaringKlass = declaringKlass;
            this.lazyReturnType = lazyReturnType;
            this.returnType = null;
            this.parameters = parameters;
        }

        Function(final @NonNull Klass declaringKlass,
                 final @NonNull String name,
                 final @NonNull Visibility visibility,
                 final boolean isStatic,
                 final boolean isAbstract,
                 final boolean isFinal,
                 final @NonNull Type returnType,
                 final @NonNull List<@NonNull Parameter> parameters) {
            assert declaringKlass != null;
            assert name != null;
            assert visibility != null;
            assert returnType != null;
            assert parameters != null;
            super(name, visibility, isStatic, isAbstract, isFinal);

            this.declaringKlass = declaringKlass;
            this.returnType = returnType;
            this.lazyReturnType = null;
            this.parameters = parameters;
        }

        public @NonNull Type returnType() {
            if (returnType != null) {
                return returnType;
            }
            assert lazyReturnType != null;
            return lazyReturnType.type();
        }

        @Override
        public @NonNull String name() {
            return name;
        }

        private @NonNull String buildDescriptorString() {
            return parameters.stream()
                    .map(parameter -> TypeResolver.getKlassFromType(parameter.type()).descriptorString())
                    .collect(Collectors.joining(
                            /*delimiter*/ ",",
                            /*prefix*/ "(",
                            /*suffix*/ ")" + TypeResolver.getKlassFromType(returnType()).descriptorString()));
        }

        @Override
        public String descriptorString() {
            return descriptorString.get();
        }

        public static final class Parameter {
            public final @NonNull String name;
            private final @Nullable LazyType lazyType;
            private final @Nullable Type type;

            Parameter(final @NonNull String name, final @NonNull LazyType lazyType) {
                assert name != null;
                assert lazyType != null;

                this.name = name;
                this.lazyType = lazyType;
                this.type = null;
            }

            Parameter(final @NonNull String name, final @NonNull Type type) {
                assert name != null;
                assert type != null;

                this.name = name;
                this.type = type;
                this.lazyType = null;
            }

            public @NonNull Type type() {
                if (type != null) {
                    return type;
                }
                assert lazyType != null;
                return lazyType.type();
            }
        }
    }
}
