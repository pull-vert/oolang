/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import org.jspecify.annotations.NonNull;

import java.util.List;

public sealed class BaseSymbol permits BaseSymbol.FunctionSymbol, BaseSymbol.PropertySymbol, OolangKlass {
    public final @NonNull String name;
    final @NonNull Visibility visibility;
    public final boolean isStatic;
    public final boolean isAbstract;
    public final boolean isFinal;

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

    public static final class PropertySymbol extends BaseSymbol {
        private final @NonNull LazyType lazyType;

        PropertySymbol(final @NonNull BaseSymbol baseSymbol,
                       final @NonNull LazyType lazyType) {
            assert baseSymbol != null;
            assert lazyType != null;
            super(baseSymbol);

            this.lazyType = lazyType;
        }

        public @NonNull Type type() {
            return lazyType.type();
        }
    }

    public static final class FunctionSymbol extends BaseSymbol {
        private final @NonNull LazyType lazyReturnType;
        public final @NonNull List<@NonNull Parameter> parameters;

        FunctionSymbol(final @NonNull BaseSymbol baseSymbol,
                       final @NonNull LazyType lazyReturnType,
                       final @NonNull List<@NonNull Parameter> parameters) {
            assert baseSymbol != null;
            assert lazyReturnType != null;
            assert parameters != null;
            super(baseSymbol);

            this.lazyReturnType = lazyReturnType;
            this.parameters = parameters;
        }

        public @NonNull Type returnType() {
            return lazyReturnType.type();
        }

        public static final class Parameter {
            public final @NonNull String name;
            private final @NonNull LazyType lazyType;

            Parameter(final @NonNull String name, final @NonNull LazyType lazyType) {
                assert name != null;
                assert lazyType != null;

                this.name = name;
                this.lazyType = lazyType;
            }

            public @NonNull Type type() {
                return lazyType.type();
            }
        }
    }
}
