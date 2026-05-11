/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.ast;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public final class Annotation implements AstNode {
    public final @NonNull Type type;
    public @Nullable UseSiteTarget useSiteTarget;

    public Annotation(final @NonNull Type type) {
        this.type = Objects.requireNonNull(type);
    }

    @Override
    public @NonNull String description() {
        final var sb = new StringBuilder();
        sb.append("Annotation(");
        if (useSiteTarget != null) {
            sb.append(useSiteTarget.name().toLowerCase(Locale.US)).append(":");
        }
        sb.append(type.rawName()).append(")");
        return sb.toString();
    }

    @Override
    public @NonNull List<@NonNull Ast> content() {
        return List.of();
    }

    public enum UseSiteTarget {
        GET,
        SET,
        PARAM,
    }
}
