package com.nouvellesapps.magicthegathering
import android.app.Application
import android.content.Context
import android.widget.Toast
import io.magicthegathering.kotlinsdk.model.card.MtgCard

class MyApplication: Application() {

    companion object{
        var mtgCard: MtgCard? = null
        fun showMessage(context: Context,message: String){
            Toast.makeText(context,message,Toast.LENGTH_LONG)
        }
    }

    override fun onCreate() {
        super.onCreate()
    }

}