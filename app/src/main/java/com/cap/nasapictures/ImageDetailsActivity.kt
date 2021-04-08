package com.cap.nasapictures

import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.cap.nasapictures.adapters.ImageDetailsViewPagerAdapter
import com.cap.nasapictures.models.NasaPicture

class ImageDetailsActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_LIST = "image_list"
    }

    private lateinit var imageViewPager: ViewPager
    private var imageList = arrayListOf<NasaPicture>()

    private lateinit var tvTitle: TextView
    private lateinit var ivBack: ImageView
    private lateinit var rlToolbar: RelativeLayout

    private lateinit var adapter: ImageDetailsViewPagerAdapter

    private var imageClicked = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)

        initData()

        findViewsById()

        clickListeners()
    }

    private fun initData() {

        if (intent != null && intent.hasExtra(IMAGE_LIST)) {
            imageList.clear()
            imageList.addAll(
                intent.getParcelableArrayListExtra(IMAGE_LIST) ?: arrayListOf()
            )
        }
    }

    private fun findViewsById() {

        imageViewPager = findViewById(R.id.imageViewPager)

        rlToolbar = findViewById(R.id.rlToolbar)

        rlToolbar.bringToFront()

        tvTitle = findViewById(R.id.tvTitle)

        ivBack = findViewById(R.id.ivBack)

        adapter = ImageDetailsViewPagerAdapter(
            this@ImageDetailsActivity,
            imageList,
            object : ImageDetailsViewPagerAdapter.ImageClickListener {
                override fun onImageClick() {

                    imageClicked = if (imageClicked) {
                        hideSystemUI()
                        false
                    } else {
                        showSystemUI()
                        true
                    }
                }

            })

        imageViewPager.adapter = adapter

        var position = 0

        for (i in 0 until imageList.size) {
            if (imageList[i].isSelected) {
                position = i
                break
            }
        }

        imageViewPager.currentItem = position

        updateToolbarUI(position)

        imageViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //Empty Function
            }

            override fun onPageSelected(position: Int) {
                updateToolbarUI(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                //Empty Function
            }

        })

    }

    private fun updateToolbarUI(position: Int) {
        tvTitle.text = imageList[position].title ?: ""
    }

    private fun clickListeners() {

        ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun hideSystemUI() {

        rlToolbar.animate().translationY(-rlToolbar.bottom.toFloat())
            .setInterpolator(AccelerateInterpolator()).start()

    }

    private fun showSystemUI() {

        rlToolbar.animate().translationY(0F)
            .setInterpolator(DecelerateInterpolator())
            .start()

    }
}