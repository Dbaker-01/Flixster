package com.example.flixster

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import okhttp3.internal.notifyAll
import org.json.JSONObject


private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlixsterMoviesFragment : Fragment(), OnListFragmentInteractionListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flixster_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context

        recyclerView.layoutManager = LinearLayoutManager(context)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView){
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY

        client[
                "https://api.themoviedb.org/3/movie/now_playing",
                params,
                object : JsonHttpResponseHandler()

                {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers?,
                        json: JSON ){
                        progressBar.hide()

                        val resultsJSON : String = json.jsonObject.get("results").toString()
                        //val moviesRawJSON : String = resultsJSON.get("title").toString()

                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<FlixsterMovies>>() {}.type
                        val models : List<FlixsterMovies> = gson.fromJson(resultsJSON, arrayMovieType)
                        recyclerView.adapter = FlixsterRecyclerViewAdapter(models, this@FlixsterMoviesFragment)

                        //Log.d("FlixsterMoviesFragment", "response successful")
                        Log.i("NowPlayingFragment",models.get(0).bookImageUrl.toString())
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ){
                        progressBar.hide()

                        t?.message.let{
                            Log.e("FlixsterMoviesFragment", errorResponse)
                        }
                    }
                }]
    }
    override fun onItemClick(item: FlixsterMovies){
        Toast.makeText(context, "test " + item.title, Toast.LENGTH_LONG).show()
    }

}