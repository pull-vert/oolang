/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import java.security.Principal;

public abstract class AbstractPrincipalLvl2 implements Principal {
    public String parentPublicPropLvl2 = "public";
    protected String parentProtectedPropLvl2 = "protected";
    String parentPackagePropLvl2 = "package";
    private String parentPrivatePropLvl2 = "private";
}
