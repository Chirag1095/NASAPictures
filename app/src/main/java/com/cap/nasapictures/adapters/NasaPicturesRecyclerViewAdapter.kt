package com.cap.nasapictures.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cap.nasapictures.R
import com.cap.nasapictures.models.NasaPicture


class NasaPicturesRecyclerViewAdapter(
    private val imageList: ArrayList<NasaPicture>,
    private val listener: PictureClickListener
) :
    RecyclerView.Adapter<NasaPicturesRecyclerViewAdapter.NasaPicturesViewHolder>() {

    private lateinit var context: Context

    interface PictureClickListener {
        fun onPictureClick(picture: NasaPicture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaPicturesViewHolder {
        context = parent.context
        return NasaPicturesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NasaPicturesViewHolder, position: Int) {
        holder.bind(context, this, imageList[position])
    }

    override fun getItemCount() = imageList.size

    open class NasaPicturesViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val ivPicture: ImageView = item.findViewById(R.id.ivPicture)

        fun bind(
            context: Context,
            adapter: NasaPicturesRecyclerViewAdapter,
            nasaPicture: NasaPicture
        ) {
            Log.d("TESTC", "ImageView")

            Glide.with(context)
                .load(nasaPicture.url)
                .centerCrop()
                .into(ivPicture)


            /*Glide.with(context)
                .load(nasaPicture.url)
                .override(200, 200)
                .into(object : CustomTarget<Drawable?>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable?>?
                    ) {
                        ivPicture.background = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        //Empty Function
                    }

                })*/

            ivPicture.setOnClickListener {
                adapter.listener.onPictureClick(nasaPicture)
            }
        }

        companion object {
            fun from(parent: ViewGroup): NasaPicturesViewHolder {
                return NasaPicturesViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(
                            R.layout.item_nasa_pictures,
                            parent,
                            false
                        )
                )
            }
        }
    }
}