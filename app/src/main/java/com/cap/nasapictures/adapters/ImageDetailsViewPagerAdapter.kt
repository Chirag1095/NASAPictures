package com.cap.nasapictures.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.cap.nasapictures.R
import com.cap.nasapictures.models.NasaPicture

class ImageDetailsViewPagerAdapter(
    private val context: Context,
    private val imageList: ArrayList<NasaPicture>,
    private val listener: ImageClickListener
) : PagerAdapter() {

    interface ImageClickListener {
        fun onImageClick()
    }

    private val layoutInflater: LayoutInflater by lazy {
        LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj as (RelativeLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val itemView =
            layoutInflater.inflate(R.layout.item_image_details, container, false)

        var isImageClicked = true

        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvCredit: TextView = itemView.findViewById(R.id.tvCredit)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvExplanation: TextView = itemView.findViewById(R.id.tvExplanation)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val rlImageInfo: RelativeLayout = itemView.findViewById(R.id.rlImageInfo)

        val nasaPicture = imageList[position]

        progressBar.visibility = View.VISIBLE

        rlImageInfo.visibility = View.GONE

        tvCredit.text = nasaPicture.copyright ?: ""

        tvDate.text = nasaPicture.date ?: ""

        tvExplanation.text = nasaPicture.explanation ?: ""

        Glide.with(context)
            .asBitmap()
            .load(nasaPicture.hdurl)
            .centerCrop()
            .into(object : CustomTarget<Bitmap?>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                    //Empty Function
                }

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    progressBar.visibility = View.GONE

                    if (isImageClicked) {
                        rlImageInfo.visibility = View.VISIBLE
                        isImageClicked = false
                    } else {
                        rlImageInfo.visibility = View.GONE
                        isImageClicked = true
                    }
                    imageView.setImageBitmap(resource)
                }

            })

        imageView.setOnClickListener {
            if (isImageClicked) {
                rlImageInfo.visibility = View.VISIBLE
                isImageClicked = false
            } else {
                rlImageInfo.visibility = View.GONE
                isImageClicked = true
            }

            listener.onImageClick()
        }
        container.addView(itemView)

        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as RelativeLayout)
    }

}