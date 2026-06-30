/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.symbol.table.external.AbstractPrincipalLvll1;

import java.security.Principal;

public class PrincipalImpl extends AbstractPrincipalLvll1 implements Comparable<Principal> {
    public String publicProp = "public";
    protected String protectedProp = "protected";
    String packageProp = "package";
    private String privateProp = "private";

    @Override
    public int compareTo(Principal other) {
        return getName().compareTo(other.getName());
    }

    @Override
    public String getName() {
        return "PrincipalImpl";
    }
}
