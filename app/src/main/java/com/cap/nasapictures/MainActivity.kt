package com.cap.nasapictures

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cap.nasapictures.adapters.NasaPicturesRecyclerViewAdapter
import com.cap.nasapictures.models.NasaPicture
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var rlPictures: RecyclerView

    private var pictureList = arrayListOf<NasaPicture>()

    private lateinit var adapter: NasaPicturesRecyclerViewAdapter

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewsById()

        observers()

    }


    private fun findViewsById() {

        rlPictures = findViewById(R.id.rlPictures)

        rlPictures.layoutManager = GridLayoutManager(
            this,
            2,
            GridLayoutManager.VERTICAL,
            false
        )

        adapter = NasaPicturesRecyclerViewAdapter(pictureList,
            object : NasaPicturesRecyclerViewAdapter.PictureClickListener {
                override fun onPictureClick(picture: NasaPicture) {

                }

            })

        rlPictures.adapter = adapter

    }

    private fun observers() {

        viewModel.getNasaPictures().observe(this, {

            when (it) {
                is ResponseManager.Success -> {
                    pictureList.clear()
                    pictureList.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }

                is ResponseManager.Error -> {
                    pictureList.clear()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}