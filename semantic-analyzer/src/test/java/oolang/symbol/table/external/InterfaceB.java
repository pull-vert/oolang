/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table.external;

public interface InterfaceB extends InterfaceA {
    default void foo() {
        System.out.println("B");
    }
}
