/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public sealed abstract class Identifier implements Ast {
    public abstract @NonNull String rawName();

    @Override
    public final @NonNull String description() {
        return "Identifier(" + rawName() + ")";
    }

    public static final class SimpleIdentifier extends Identifier {
        public final @NonNull String identifier;

        public SimpleIdentifier(final @NonNull String identifier) {
            assert identifier != null;
            this.identifier = identifier;
        }

        public @NonNull String rawName() {
            return identifier;
        }
    }

    /**
     * Must never contain less than 2 identifiers, else use SimpleIdentifier
     */
    public static final class MultipleIdentifier extends Identifier {
        public final @NonNull List<@NonNull SimpleIdentifier> identifiers = new ArrayList<>();

        public @NonNull String rawName() {
            final var sb = new StringBuilder();
            identifierName(identifiers, sb);
            return sb.toString();
        }
    }

    static void identifierName(final @NonNull List<@NonNull SimpleIdentifier> identifiers,
                               final @NonNull StringBuilder sb) {
        assert identifiers != null;
        assert sb != null;

        for (var i = 0; i < identifiers.size(); i++) {
            if (i > 0) {
                sb.append(".");
            }
            sb.append(identifiers.get(i).rawName());
        }
    }
}
