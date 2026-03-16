package oo.ast.element;

import oo.ast.Ast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static oo.ast.element.ElementUtils.identifierName;

public record ElementAnnotation(
        @NonNull List<@NonNull ElementIdentifier> identifier,
        @Nullable UseSiteTarget target
) implements ElementNode {
    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("ElementAnnotation(");
        if (target != null) {
            sb.append(target.name().toLowerCase(Locale.US)).append(":");
        }
        sb.append(identifierName(identifier)).append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> children() {
        return List.of();
    }

    public static final class Builder {
        private final @NonNull List<@NonNull ElementIdentifier> identifier;
        private @Nullable UseSiteTarget target;

        public Builder(final @NonNull List<@NonNull ElementIdentifier> identifier) {
            this.identifier = Objects.requireNonNull(identifier);
        }

        public Builder useSiteTarget(final @NonNull UseSiteTarget target) {
            this.target = Objects.requireNonNull(target);
            return this;
        }

        public @NonNull ElementAnnotation build() {
            return new ElementAnnotation(identifier, target);
        }
    }

    public enum UseSiteTarget {
        GET,
        SET,
        PARAM,
    }
}
