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
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
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
        updateAdapter(progressBar, recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView){
        progressBar.show()

        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        client[
                "https://api.themoviedb.org/3/movie/550?api_key="+ API_KEY+ "&callback=test",
                params,
                object : JsonHttpResponseHandler()

                {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers?,
                        json: JsonHttpResponseHandler.JSON ){
                        progressBar.hide()

                        val resultsJSON : JSONObject = json.jsonObject.get("results") as JSONObject
                        val moviesRawJSON : String = resultsJSON.get("title").toString()

                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<FlixsterMovies>>() {}.type
                        val models : List<FlixsterMovies> = gson.fromJson(moviesRawJSON, arrayMovieType)
                        recyclerView.adapter = FlixsterRecyclerViewAdapter(models, this@FlixsterMoviesFragment)


                        Log.d("FlixsterMoviesFragment", "response successful")

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