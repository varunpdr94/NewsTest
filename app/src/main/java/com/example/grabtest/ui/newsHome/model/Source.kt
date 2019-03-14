package com.example.grabtest.ui.newsHome.model

import com.example.grabtest.utils.BaseTModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Source(

    @SerializedName("id")
    @Expose
    var id: Any? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null

): BaseTModel()