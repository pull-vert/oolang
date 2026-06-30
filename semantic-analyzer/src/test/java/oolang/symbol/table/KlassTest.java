/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.KlassesCacheInterceptor;
import oolang.symbol.table.external.AbstractPrincipalLvll1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(KlassesCacheInterceptor.class)
public class KlassTest {
    @Test
    public void isAssignableFromTest() {
        var type = new ExistingKlass(PrincipalImpl.class);
        assertThat(type.isAssignableFrom(new ExistingKlass(PrincipalImpl.class))).isTrue();
        assertThat(type.isAssignableFrom(new ExistingKlass(AbstractPrincipalLvll1.class))).isTrue();
        assertThat(type.isAssignableFrom(new ExistingKlass(Comparable.class))).isTrue();
        assertThat(type.isAssignableFrom(new ExistingKlass(AbstractPrincipalLvl2.class))).isTrue();
        assertThat(type.isAssignableFrom(new ExistingKlass(Principal.class))).isTrue();
    }

    @Test
    public void getAccessibleFieldTest() {
        var type = new ExistingKlass(PrincipalImpl.class);

        // properties from the PrincipalImpl class itself
        var property = (ExistingKlass) type.accessibleProperty("publicProp");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("protectedProp");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("packageProp");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("privateProp");
        assertThat(property).isNull();

        // properties from lvl1 parent
        property = (ExistingKlass) type.accessibleProperty("parentPublicPropLvl1");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("parentProtectedPropLvl1");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        // not in the same package
        property = (ExistingKlass) type.accessibleProperty("parentPackagePropLvl1");
        assertThat(property).isNull();
        property = (ExistingKlass) type.accessibleProperty("privatePropLvl1");
        assertThat(property).isNull();

        // properties from lvl2 parent
        property = (ExistingKlass) type.accessibleProperty("parentPublicPropLvl2");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("parentProtectedPropLvl2");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        // in the same package
        property = (ExistingKlass) type.accessibleProperty("parentPackagePropLvl2");
        assertThat(property).isNotNull();
        assertThat(property.descriptorString()).isEqualTo("Ljava/lang/String;");
        property = (ExistingKlass) type.accessibleProperty("privatePropLvl2");
        assertThat(property).isNull();
    }
}
