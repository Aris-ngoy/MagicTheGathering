package com.nouvellesapps.magicthegathering.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nouvellesapps.magicthegathering.DetailActivity
import com.nouvellesapps.magicthegathering.MyApplication
import com.nouvellesapps.magicthegathering.R
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import kotlinx.android.synthetic.main.card_list_item.view.*
import  com.nouvellesapps.magicthegathering.MyApplication.Companion


class CardListAdapter(private val CardList: List<MtgCard>, private val context: Context) :
    RecyclerView.Adapter<CardListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.card_list_item,
                parent, false))
    }

    override fun getItemCount(): Int {
        return CardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.cardTitle.text = this.CardList[position].name
        holder.itemView.secondText.text = this.CardList[position].artist

        Glide
            .with(context)
            .load(this.CardList[position].imageUrl)
            .fitCenter()
            .placeholder(R.drawable.ic_loader)
            .transform(RoundedCorners(48))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.itemView.cardImage);


        holder.itemView.CardItem.setOnClickListener {
            var passValue = this.CardList[position]
            MyApplication.mtgCard = passValue
            var intent = Intent(holder.itemView.CardItem.context, DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("param", passValue.id)
            holder.itemView.CardItem.context.startActivity(intent)
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}