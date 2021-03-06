/**
 * This file is part of Graylog.
 *
 * Graylog is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Graylog is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Graylog.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.graylog.collector.config.constraints;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class IsOneOfValidatorTest {
    private static class TestObject {
        @IsOneOf({"hello", "world"})
        private final String theValue;

        public TestObject(String theValue) {
            this.theValue = theValue;
        }
    }

    private static Validator validator;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testConstraint() throws Exception {
        assertEquals(0, validate("hello").size());
        assertEquals(0, validate("world").size());
        assertEquals(1, validate("wat?").size());
        assertEquals("\"wat?\" is not one of: hello world", validate("wat?").iterator().next().getMessage());

        // Check different case.
        assertEquals(1, validate("Hello").size());
        assertEquals(1, validate("WORLD").size());
    }

    private Set<ConstraintViolation<TestObject>> validate(String value) {
        return validator.validate(new TestObject(value));
    }
}