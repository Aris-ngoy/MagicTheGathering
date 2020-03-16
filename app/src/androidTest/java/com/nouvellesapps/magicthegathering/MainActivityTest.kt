package com.nouvellesapps.magicthegathering

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {

    }

    @Test
    fun getSets() = runBlocking {
        val setsResponse: Response<List<MtgSet>> = MtgSetApiClient.getAllSets()
        val sets = setsResponse.body()
        assert(sets?.size!! > 0)
    }


    @After
    fun tearDown() {
    }
}