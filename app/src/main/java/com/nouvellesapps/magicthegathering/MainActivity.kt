package com.nouvellesapps.magicthegathering

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.GridLayoutManager
import com.nouvellesapps.magicthegathering.Adapters.SetListAdapter
import io.magicthegathering.kotlinsdk.api.MtgSetApiClient
import io.magicthegathering.kotlinsdk.model.set.MtgSet
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Response
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity(coroutineContext: CoroutineContext) : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CardLoadingBar.visibility = View.GONE

        launch {
            callOnMainThread()
        }


    }
    private suspend fun callOnMainThread() {
        CardLoadingBar.visibility = View.VISIBLE
        try {
            val result = getAllSets()
            withContext(Dispatchers.Main) {
                SetListView.layoutManager = GridLayoutManager(applicationContext,2)
                SetListView.setHasFixedSize(true)
                SetListView.adapter = SetListAdapter(result,applicationContext)
                SetListView.refreshDrawableState()
                CardLoadingBar.visibility = View.GONE
            }
        }catch (e:Exception){
            print(e.message)
            CardLoadingBar.visibility = View.GONE
        }
    }

    private suspend fun getAllSets(): List<MtgSet> {
        return withContext(Dispatchers.Default) {
            val setsResponse: Response<List<MtgSet>> = MtgSetApiClient.getAllSets()
            val sets = setsResponse.body()
            return@withContext sets!!
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
