package com.cap.nasapictures

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cap.nasapictures.models.NasaPicture

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MainActivityRepository(application)

    fun getNasaPictures(): LiveData<ResponseManager<ArrayList<NasaPicture>>> {
        return repository.getNasaPictures()
    }

}