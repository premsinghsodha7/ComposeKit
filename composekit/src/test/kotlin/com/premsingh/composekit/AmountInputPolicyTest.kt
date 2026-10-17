package com.premsingh.composekit

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AmountInputPolicyTest {
    @Test fun sanitizesInputToOneDecimalAndTwoPlaces() {
        assertEquals("12.34", AmountInputPolicy.sanitize("1a2,3.456"))
        assertEquals("1.23", AmountInputPolicy.sanitize("1,234"))
        assertEquals("", AmountInputPolicy.sanitize("abc"))
    }

    @Test fun validatesPositiveAmountWithAtMostTwoDecimalPlaces() {
        assertTrue(AmountInputPolicy.isValid("12.50"))
        assertTrue(AmountInputPolicy.isValid("0.01"))
        assertFalse(AmountInputPolicy.isValid("0"))
        assertFalse(AmountInputPolicy.isValid("-1"))
        assertFalse(AmountInputPolicy.isValid("1.234"))
    }
}
