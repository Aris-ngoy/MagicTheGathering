package com.nouvellesapps.magicthegathering

import io.magicthegathering.kotlinsdk.api.MtgCardApiClient
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response

class CardListActivityTest {

    @Before
    fun setUp() {
    }

    @Test
    fun getCards() = runBlocking {
        val cardsResponse: Response<List<MtgCard>> = MtgCardApiClient.getAllCardsBySetCode("KTK")
        val cards = cardsResponse.body()
        assert(cards?.size!! > 0)
    }

    @Test
    fun getBoosters() = runBlocking {
        val cardsResponse: Response<List<MtgCard>> = MtgSetApiClient.generateBoosterPackBySetCode("KTK")
        val cards = cardsResponse.body()
        assert(cards?.size!! > 0)
    }

    @After
    fun tearDown() {
    }
}