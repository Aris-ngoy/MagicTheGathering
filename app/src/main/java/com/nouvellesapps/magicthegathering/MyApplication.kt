package com.nouvellesapps.magicthegathering
import android.app.Application
import io.magicthegathering.kotlinsdk.model.card.MtgCard

class MyApplication: Application() {

    companion object{
        var mtgCard: MtgCard? = null
    }

   // var mtgCard: MtgCard? = null

    override fun onCreate() {
        super.onCreate()
    }

}