package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record Identifier(
        @NonNull String identifier,
        @NonNull List<@NonNull Identifier> parameters,
        @NonNull List<@NonNull Annotation> annotations,
        boolean nullable,
        @Override @NonNull List<@NonNull Ast> content
) implements AstNode {
    public @NonNull String rawName() {
        final var sb = new StringBuilder();
        sb.append(identifier);
        if (!parameters.isEmpty()) {
            sb.append("<");
            sb.append(parameters.stream()
                    .map(Identifier::rawName)
                    .collect(Collectors.joining(", ")));
            sb.append(">");
        }
        if (nullable) {
            sb.append("?");
        }
        return sb.toString();
    }

    @Override
    public @NonNull String description() {
        return "Identifier(" + rawName() + ")";
    }

    public static final class Builder {
        private final @NonNull String identifier;
        private final @NonNull List<@NonNull Identifier> parameters = new ArrayList<>();
        private boolean nullable = false;
        private @Nullable List<@NonNull Annotation> annotations = null;
        private final @NonNull List<@NonNull Ast> children = new ArrayList<>();

        public Builder(final @NonNull String identifier) {
            this.identifier = Objects.requireNonNull(identifier);
        }

        public @NonNull Builder addParameters(final @NonNull List<@NonNull Identifier> parameters) {
            this.parameters.addAll(Objects.requireNonNull(parameters));
            return this;
        }

        public @NonNull Builder annotations(final @NonNull List<@NonNull Annotation> annotations) {
            this.annotations = Objects.requireNonNull(annotations);
            return this;
        }

        public @NonNull Identifier build() {
            return new Identifier(
                    identifier,
                    List.copyOf(parameters),
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    nullable,
                    List.copyOf(children));
        }
    }
}
