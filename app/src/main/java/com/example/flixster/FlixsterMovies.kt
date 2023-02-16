package com.example.flixster
import com.google.gson.annotations.SerializedName
class FlixsterMovies
{
    @JvmField
    @SerializedName("title")
    var title: String? = null

    @JvmField
    @SerializedName("Description")
    var description: String? = null

    @SerializedName("book_image")
    var bookImageUrl: String? = null
}