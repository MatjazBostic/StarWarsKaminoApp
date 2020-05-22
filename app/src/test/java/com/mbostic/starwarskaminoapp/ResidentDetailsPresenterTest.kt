package com.mbostic.starwarskaminoapp

import com.mbostic.starwarskaminoapp.residentDetails.Resident
import com.mbostic.starwarskaminoapp.residentDetails.ResidentDetailsPresenter
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.timeout


class ResidentDetailsPresenterTest {

    @Test
    fun test(){

        val viewMock = mock<ResidentDetailsPresenter.View>()

        ResidentDetailsPresenter(viewMock, 73)

        val argument: ArgumentCaptor<Resident> = ArgumentCaptor.forClass(Resident::class.java)

        verify(viewMock, timeout(5000)).setData(capture(argument))
        assertEquals("Taun We", argument.value.name)
        assertEquals(213, argument.value.height)
        assertEquals("unknown", argument.value.mass)
        assertEquals("female", argument.value.gender)

        verify(viewMock, timeout(5000)).setHomeWorldName("Kamino")
    }

}