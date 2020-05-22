package com.mbostic.starwarskaminoapp

import org.junit.Assert.assertEquals
import org.junit.Test

class HelperFunctionsTest {

    @Test
    fun testForceHttpsInUrl(){
        assertEquals(HelperFunctions.forceHttpsInUrl("http://example.com"), "https://example.com")
        assertEquals(HelperFunctions.forceHttpsInUrl("https://example.com"), "https://example.com")
    }

    @Test
    fun testFormatDateFromDateString(){
        HelperFunctions.formatDateFromDateString("yyyy-MM-dd'T'HH:mm:ss.FFFFFF'Z'",
            "dd.MM.yyyy", "2014-12-20T21:17:50.474000Z").equals("20.12.2014")
    }
}