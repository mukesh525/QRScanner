package com.example.gurleensethi.barcodedemo

import com.google.gson.annotations.SerializedName

data class ScanData(
        @SerializedName("event")
        val event: String,

        @SerializedName("key")
        val key: String,

        @SerializedName("mettingRoomId")
        val mettingRoomId: String,

        @SerializedName("name")
        val name: String,

        @SerializedName("time")
        val time: String) {

    fun getUrl(): String {
        return "https://maker.ifttt.com/trigger/${this.event}/with/key/${this.key}"
    }

}