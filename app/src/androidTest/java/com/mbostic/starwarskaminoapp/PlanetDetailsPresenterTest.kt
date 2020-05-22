package com.mbostic.starwarskaminoapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mbostic.starwarskaminoapp.planetDetails.Planet
import com.mbostic.starwarskaminoapp.planetDetails.PlanetDetailsPresenter

import org.mockito.ArgumentCaptor

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.*

@RunWith(AndroidJUnit4::class)
class PlanetDetailsPresenterTest {

    private lateinit var viewMock: PlanetDetailsPresenter.View
    private lateinit var presenter: PlanetDetailsPresenter

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        viewMock = mock(PlanetDetailsPresenter.View::class.java)
        `when`(viewMock.getContext()).thenReturn(appContext)
        presenter = PlanetDetailsPresenter(viewMock)
    }

    @Test
    fun testInit() {
        val argument: ArgumentCaptor<Planet> = ArgumentCaptor.forClass(Planet::class.java)

        verify(viewMock, timeout(5000)).setData(capture(argument))
        assertEquals("Kamino", argument.value.name)
        assertEquals(27, argument.value.rotationPeriod)
        assertEquals(19720, argument.value.diameter)
        assertEquals("1 standard", argument.value.gravity)
    }

    @Test
    fun testPlanetImageClicked() {
        presenter.planetImageClicked()
        verify(viewMock).setPlanetImgHeight(PlanetDetailsPresenter.PLANET_IMG_BIG_HEIGHT_DP)

        presenter.planetImageClicked()
        verify(viewMock).setPlanetImgHeight(PlanetDetailsPresenter.PLANET_IMG_SMALL_HEIGHT_DP)
    }

    /**
     * Returns ArgumentCaptor.capture() as nullable type to avoid java.lang.IllegalStateException
     * when null is returned.
     */
    private fun <T> capture(argumentCaptor: ArgumentCaptor<T>): T = argumentCaptor.capture()
}
