package com.example.flixster

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FlixsterRecyclerViewAdapter(
    private val movies: List<FlixsterMovies>,
    private val mListner: OnListFragmentInteractionListener?
    )
    : RecyclerView.Adapter<FlixsterRecyclerViewAdapter.MovieViewHolder>()
{

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView){
        var mItem: FlixsterMovies? = null
        val mMovieTitle: TextView = mView.findViewById<View>(R.id.movieTitle) as TextView
        val mMovieDescription: TextView = mView.findViewById<View>(R.id.movieDescription) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(R.id.movieImage) as ImageView

        override fun toString(): String {
            return mMovieTitle.toString()
            //+ " '" + mMovieDescription.text + "'"
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_flixster_movies, parent,false)
        return MovieViewHolder(view)
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/"+ movie.bookImageUrl)
            .centerInside()
            .into(holder.mMovieImage)

        holder.mView.setOnClickListener {
            holder.mItem?.let {movie ->
                mListner?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }



}