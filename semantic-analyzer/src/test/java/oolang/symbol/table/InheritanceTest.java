/*
 * This is free and unencumbered software released into the public domain, following <https://unlicense.org>
 */

package oolang.symbol.table;

import oolang.KlassesCacheInterceptor;
import oolang.symbol.table.external.AbstractPrincipalLvll1;
import oolang.symbol.table.external.ClassC;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(KlassesCacheInterceptor.class)
public class InheritanceTest {
    @Test
    public void subinterfacesWin() {
        var klass = new ExistingKlass(ClassC.class);
        assertThat(klass.genericSuperInterfaces()).hasSize(2);
        var superInterface1 = (Klass) klass.genericSuperInterfaces().getFirst();
        assertThat(superInterface1.descriptorString()).isEqualTo("Loolang/symbol/table/external/InterfaceB;");
        var superInterface2 = (Klass) klass.genericSuperInterfaces().getLast();
        assertThat(superInterface2.descriptorString()).isEqualTo("Loolang/symbol/table/external/InterfaceA;");
    }

    @Test
    public void isAssignableFromTest() {
        var klass = new ExistingKlass(PrincipalImpl.class);
        assertThat(klass.isAssignableFrom(new ExistingKlass(PrincipalImpl.class))).isTrue();
        assertThat(klass.isAssignableFrom(new ExistingKlass(AbstractPrincipalLvll1.class))).isTrue();
        assertThat(klass.isAssignableFrom(new ExistingKlass(Comparable.class))).isTrue();
        assertThat(klass.isAssignableFrom(new ExistingKlass(AbstractPrincipalLvl2.class))).isTrue();
        assertThat(klass.isAssignableFrom(new ExistingKlass(Principal.class))).isTrue();
    }

    @Test
    public void getAccessiblePropertyIncludeSelfTest() {
        var klass = new ExistingKlass(PrincipalImpl.class);

        // properties from the PrincipalImpl class itself
        var property = klass.accessibleProperty("publicProp", true);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("protectedProp", true);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("packageProp", true);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("privateProp", true);
        assertThat(property).isNull();

        // properties from lvl1 parent
        property = klass.accessibleProperty("parentPublicPropLvl1", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("parentProtectedPropLvl1", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        // not in the same package
        property = klass.accessibleProperty("parentPackagePropLvl1", false);
        assertThat(property).isNull();
        property = klass.accessibleProperty("privatePropLvl1", false);
        assertThat(property).isNull();

        // properties from lvl2 parent
        property = klass.accessibleProperty("parentPublicPropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("parentProtectedPropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        // in the same package
        property = klass.accessibleProperty("parentPackagePropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = klass.accessibleProperty("privatePropLvl2", false);
        assertThat(property).isNull();
    }

    @Test
    public void getAccessiblePropertyForParentsTest() {
        var type = new ExistingKlass(PrincipalImpl.class);

        // properties from the PrincipalImpl class itself
        var property = type.accessibleProperty("publicProp", false);
        assertThat(property).isNull();
        property = type.accessibleProperty("protectedProp", false);
        assertThat(property).isNull();
        property = type.accessibleProperty("packageProp", false);
        assertThat(property).isNull();
        property = type.accessibleProperty("privateProp", false);
        assertThat(property).isNull();

        // properties from lvl1 parent
        property = type.accessibleProperty("parentPublicPropLvl1", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = type.accessibleProperty("parentProtectedPropLvl1", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        // not in the same package
        property = type.accessibleProperty("parentPackagePropLvl1", false);
        assertThat(property).isNull();
        property = type.accessibleProperty("privatePropLvl1", false);
        assertThat(property).isNull();

        // properties from lvl2 parent
        property = type.accessibleProperty("parentPublicPropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = type.accessibleProperty("parentProtectedPropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        // in the same package
        property = type.accessibleProperty("parentPackagePropLvl2", false);
        assertThat(property).isNotNull();
        assertThat(((ExistingKlass) property.type()).descriptorString()).isEqualTo("Ljava/lang/String;");
        property = type.accessibleProperty("privatePropLvl2", false);
        assertThat(property).isNull();
    }
}
