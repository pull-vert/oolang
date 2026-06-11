/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang;

import oolang.semantic.analyzer.KlassCacheRunner;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

public class KlassesCacheInterceptor implements InvocationInterceptor {
    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                    ReflectiveInvocationContext<Method> invocationContext,
                                    ExtensionContext extensionContext) throws Throwable {

        var throwable = new AtomicReference<Throwable>(null);
        KlassCacheRunner.run(() -> {
            try {
                invocation.proceed();
            } catch (Throwable e) {
                throwable.setPlain(e);
            }
        });
        var thrown = throwable.get();
        if (thrown != null) {
            throw thrown;
        }
    }
}
