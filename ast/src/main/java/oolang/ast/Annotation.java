/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public record Annotation(
        @NonNull Type type,
        @Nullable UseSiteTarget target
) implements AstNode {
    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Annotation(");
        if (target != null) {
            sb.append(target.name().toLowerCase(Locale.US)).append(":");
        }
        sb.append(type.rawName()).append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        return List.of();
    }

    public static final class Builder {
        private final @NonNull Type type;
        private @Nullable UseSiteTarget target;

        public Builder(final @NonNull Type type) {
            this.type = Objects.requireNonNull(type);
        }

        public Builder useSiteTarget(final @NonNull UseSiteTarget target) {
            this.target = Objects.requireNonNull(target);
            return this;
        }

        public @NonNull Annotation build() {
            return new Annotation(type, target);
        }
    }

    public enum UseSiteTarget {
        GET,
        SET,
        PARAM,
    }
}
