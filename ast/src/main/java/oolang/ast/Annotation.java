/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static oolang.ast.AstUtils.identifierName;

public record Annotation(
        @NonNull List<@NonNull Identifier> identifier,
        @Nullable UseSiteTarget target
) implements AstNode {
    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Annotation(");
        if (target != null) {
            sb.append(target.name().toLowerCase(Locale.US)).append(":");
        }
        sb.append(identifierName(identifier)).append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        return List.of();
    }

    public static final class Builder {
        private final @NonNull List<@NonNull Identifier> identifier = new ArrayList<>();
        private @Nullable UseSiteTarget target;

        public Builder addIdentifier(final @NonNull Identifier identifier) {
            this.identifier.add(Objects.requireNonNull(identifier));
            return this;
        }

        public Builder useSiteTarget(final @NonNull UseSiteTarget target) {
            this.target = Objects.requireNonNull(target);
            return this;
        }

        public @NonNull Annotation build() {
            return new Annotation(List.copyOf(identifier), target);
        }
    }

    public enum UseSiteTarget {
        GET,
        SET,
        PARAM,
    }
}
