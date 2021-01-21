package org.wahlzeit.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class EnvironmentTypeTest {
    @Test
    public void testEnvironmentTypeUniqueness(){
        EnvironmentType one = EnvironmentType.getEnvironmentType("uno");
        EnvironmentType two = EnvironmentType.getEnvironmentType("due");
        EnvironmentType three = EnvironmentType.getEnvironmentType("uno");
        EnvironmentType four = EnvironmentType.getEnvironmentType("due");

        assertEquals(one, three);

        Environment second = two.createEnvironmentInstance(); 
        Environment fourth = two.createEnvironmentInstance(); 

        assertEquals(second.getType(), fourth.getType());

    }

    @Test
    public void testSubtyping(){
        EnvironmentType one = EnvironmentType.getEnvironmentType("uno");
        EnvironmentType two = EnvironmentType.getEnvironmentType("due");
        one.addSubType(two);

        assertTrue("Should be Subtype", two.isSubtype(one));


        EnvironmentType three = EnvironmentType.getEnvironmentType("due");
       
        assertTrue("Should be Subtype", three.isSubtype(one));

        assertEquals(three.getSuperType(), one);

        EnvironmentType four = EnvironmentType.getEnvironmentType("tre");
        one.addSubType(four);
        Iterator<EnvironmentType> iter = one.getSubTypeIterator();
        EnvironmentType five = iter.next();
        assertTrue("subtype should be due or tre", five == two || five == four);
        EnvironmentType six = iter.next();
        assertTrue("subtype should be due or tre", six == two || six == four);
        assertFalse("There shouldnt be any more subtypes", iter.hasNext());



        EnvironmentType seven = EnvironmentType.getEnvironmentType("quattro");
        seven.setSuperType(four);

        Iterator<EnvironmentType> iter2 = four.getSubTypeIterator();
        EnvironmentType eight = iter2.next();
        assertTrue("subtype should be due or tre", seven == eight);




    }
    
}
