package com.cap.nasapictures

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cap.nasapictures.models.NasaPicture
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStream

class MainActivityRepository(private val application: Application) {

    /*Using MVVM patter and mocking that the data is coming from backend.*/

    fun getNasaPictures(): LiveData<ResponseManager<ArrayList<NasaPicture>>> {

        val nasaPictures = MutableLiveData<ResponseManager<ArrayList<NasaPicture>>>()

        val data: String

        try {

            val ins: InputStream = application.resources.openRawResource(
                application.resources.getIdentifier(
                    "data",
                    "raw", application.packageName
                )
            )

            val stringBuilder = StringBuilder()

            var i: Int

            while (ins.read().also { i = it } != -1) {
                stringBuilder.append(i.toChar())
            }

            data = stringBuilder.toString()

            val modelType = object : TypeToken<List<NasaPicture>>() {}.type

            val pictures = Gson().fromJson<List<NasaPicture>>(data, modelType)

            nasaPictures.value = ResponseManager.Success(pictures as ArrayList<NasaPicture>)

        } catch (e: Exception) {

            nasaPictures.value = ResponseManager.Error("Some error occurred while parsing the data")

        }

        return nasaPictures

    }

}