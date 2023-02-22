package com.example.flixster
import com.google.gson.annotations.SerializedName
class FlixsterMovies
{
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("overview")
    var description: String? = null

    @SerializedName("poster_path")
    var bookImageUrl: String? = null
}