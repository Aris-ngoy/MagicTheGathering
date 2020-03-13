package com.nouvellesapps.magicthegathering.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.nouvellesapps.magicthegathering.CardListActivity
import com.nouvellesapps.magicthegathering.R
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.android.synthetic.main.set_list_ietm.view.*

class SetListAdapter(private val Sets: List<MtgSet>, private val context: Context) :
    RecyclerView.Adapter<SetListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.set_list_ietm,
                parent, false))
    }

    override fun getItemCount(): Int {
        return Sets.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setTitle.text = this.Sets[position].name
        holder.itemView.releaseDate.text = this.Sets[position].releaseDate.toLocalDate().toString()

        holder.itemView.SetItemCard.setOnClickListener {
            val paramData = Gson().toJson(this.Sets[position])
            var intent = Intent(holder.itemView.SetItemCard.context, CardListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("param", paramData)
            holder.itemView.SetItemCard.context.startActivity(intent)
        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}