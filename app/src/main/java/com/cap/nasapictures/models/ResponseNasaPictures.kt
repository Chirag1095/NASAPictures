package com.cap.nasapictures.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NasaPicture(

    val date: String? = null,

    val copyright: String? = null,

    val mediaType: String? = null,

    val hdurl: String? = null,

    val serviceVersion: String? = null,

    val explanation: String? = null,

    val title: String? = null,

    val url: String? = null,

    var isSelected: Boolean = false

) : Parcelable
