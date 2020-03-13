package com.nouvellesapps.magicthegathering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.Gson
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.card_list_item.view.*
import java.lang.Exception

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

        DetailText.text = CardModel.name
        DetailTextArtist.text = CardModel.artist
        DetailTextContent.text = CardModel.text

        Glide.with(this)
            .load(CardModel.imageUrl)
            .fitCenter()
            .placeholder(R.drawable.ic_loader)
            .transform(RoundedCorners(48))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(DetailImage);

    }
}
