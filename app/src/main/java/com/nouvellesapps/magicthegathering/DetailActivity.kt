package com.nouvellesapps.magicthegathering

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var gson = Gson()
    lateinit var mtgCard: MtgCard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var param = intent.getStringExtra("param")

        mtgCard = MyApplication.mtgCard!!
        updateUI(mtgCard)

        DetailToolbar.setNavigationOnClickListener {
            onBackPressed()
        }

    }

    private fun updateUI(CardModel:MtgCard){

        DetailToolbar.title = CardModel.name

        if(CardModel.colors?.size!! > 0){
            colorText.visibility = View.VISIBLE
            var color = Color.parseColor(CardModel.colors?.get(0))
            colorText.setBackgroundColor(color)
            colorText.text = "COLOR : " + colorToStrings(CardModel.colors!!).toUpperCase()
            if(CardModel.colors?.get(0)?.toLowerCase() == "white"){
                colorText.setTextColor(Color.BLACK)
            }
        }

        DetailText.text = CardModel.name
        DetailTextArtist.text = CardModel.artist
        DetailTextContent.text = CardModel.text
        creation.text = "TYPE : ${CardModel.type.toUpperCase()}"

        Glide.with(this)
            .load(CardModel.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.ic_loader)
            .transform(RoundedCorners(48))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(DetailImage);


        if(CardModel.power != null){
            powerText.text = "POWER : ${CardModel.power}"
            powerSeeBar.progress = stringToInt(CardModel.power!!)
        }else{
            powerText.visibility = View.GONE
            powerSeeBar.visibility = View.GONE
        }

        if(CardModel.toughness != null){
            toughnessText.text = "TOUGHNESS : ${CardModel.toughness}"
            toughnessSeekBar.progress = stringToInt(CardModel.toughness!!)
        }else{
            toughnessText.visibility = View.GONE
            toughnessSeekBar.visibility = View.GONE
        }

    }

    private fun colorToStrings(colors:List<String>): String {
        var listColors = ""
        for(item in colors){
            listColors += "$item "
        }
        return listColors
    }

    private fun stringToInt(value:String): Int {
        var progress = value.toInt()
        return progress
    }
}
