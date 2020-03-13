package com.nouvellesapps.magicthegathering

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.nouvellesapps.magicthegathering.Adapters.CardListAdapter
import io.magicthegathering.kotlinsdk.api.MtgCardApiClient
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.card.MtgCard
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.android.synthetic.main.activity_card_list.*
import kotlinx.coroutines.*
import retrofit2.Response


class CardListActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var SetModel : MtgSet
    var gson = Gson()
    var defaultSetCardList = mutableListOf<MtgCard>()
    var boosterSetCardList = mutableListOf<MtgCard>()
    lateinit var menuObject: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_list)

        cardListView.layoutManager = LinearLayoutManager(applicationContext)
        cardListView.setHasFixedSize(true)

        var param = intent.getStringExtra("param")
        SetModel = gson.fromJson(param,MtgSet::class.java)

        toolbar.title = SetModel.name

        setSupportActionBar(toolbar)
        toolbar.setOverflowIcon(resources.getDrawable(R.drawable.ic_filter_list))

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        launch {
            callOnMainThread()
        }

    }

    private fun updateListViewData(result: List<MtgCard>){
        cardListView.adapter = CardListAdapter(result,applicationContext)
        cardListView.refreshDrawableState()
        CardLoadingBar.visibility = View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuObject = menu
        val inflater = menuInflater
        inflater.inflate(R.menu.picker_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var filterBy = menuObject.findItem(R.id.filterBy)
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.defaultOption -> {
                filterBy.title = "Filter By"
                updateListViewData(defaultSetCardList)
                return true
            }
            R.id.boosterOption -> {
                filterBy.title = "Booster"
                if(boosterSetCardList.size == 0){
                    launch {
                        callBoostersOnMainThread()
                    }
                }else
                    updateListViewData(boosterSetCardList)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }


    private suspend fun callBoostersOnMainThread() {
        CardLoadingBar.visibility = View.VISIBLE
        try {
            val result = getBoosters(SetModel.code)
            boosterSetCardList = result as MutableList<MtgCard>
            withContext(Dispatchers.Main) {
                updateListViewData(boosterSetCardList)
            }
        }catch (e: Exception){
            print(e.message)
            CardLoadingBar.visibility = View.GONE
        }
    }

    private suspend fun getBoosters(param:String): List<MtgCard> {
        return withContext(Dispatchers.Default) {
            val cardsResponse: Response<List<MtgCard>> = MtgSetApiClient.generateBoosterPackBySetCode(param)
            val cards = cardsResponse.body()
            return@withContext cards!!
        }
    }



    private suspend fun callOnMainThread() {
        CardLoadingBar.visibility = View.VISIBLE
        try {
            val result = getAllSets(SetModel.code)
            defaultSetCardList = result as MutableList<MtgCard>;

            withContext(Dispatchers.Main) {
                updateListViewData(result)
            }
        }catch (e: Exception){
            print(e.message)
            CardLoadingBar.visibility = View.GONE
        }
    }

    private suspend fun getAllSets(param:String): List<MtgCard> {
        return withContext(Dispatchers.Default) {
            val cardsResponse: Response<List<MtgCard>> = MtgCardApiClient.getAllCardsBySetCode(param)
            val cards = cardsResponse.body()
            return@withContext cards!!
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
