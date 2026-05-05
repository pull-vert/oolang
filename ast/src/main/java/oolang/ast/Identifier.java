package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public sealed interface Identifier extends Ast permits Identifier.Simple, RealIdentifier {
    @NonNull String identifier();

    default @NonNull String rawName() {
        return identifier();
    }

    @Override
    default @NonNull String description() {
        return "Identifier(" + rawName() + ")";
    }

    record Simple(@NonNull String identifier) implements Identifier {
    }

    final class Builder {
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
            // fast-path for simple identifier
            if (parameters.isEmpty() &&
                    annotations == null &&
                    !nullable &&
                    children.isEmpty()) {
                return new Simple(identifier);
            }

            return new RealIdentifier(
                    identifier,
                    List.copyOf(parameters),
                    (annotations != null) ? List.copyOf(annotations) : List.of(),
                    nullable,
                    List.copyOf(children));
        }
    }
}
