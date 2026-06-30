/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import java.security.Principal;

public abstract class AbstractPrincipalLvl2 implements Principal {
    public String parentPublicPropLvl2 = "publicLvl2";
    protected String parentProtectedPropLvl2 = "protectedLvl2";
    String parentPackagePropLvl2 = "packageLvl2";
    private String parentPrivatePropLvl2 = "privateLvl2";
}
