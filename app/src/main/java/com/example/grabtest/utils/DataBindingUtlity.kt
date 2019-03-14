package com.example.grabtest.utils

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.grabtest.ui.application.MainApplication
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*


object DataBindingUtlity {


    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImage(imageView: ImageView, url: String?) {

        Picasso.get().load(url).into(imageView)
//        val cache = ImageCache.getInstance()
//        cache.initializeCache()
//
//        val bm = cache.getImageFromWarehouse(url)
//
//        if (bm != null) {
//            imageView.setImageBitmap(bm)
//        } else {
//            imageView.setImageBitmap(null)
//
//            val imgTask = DownloadImageTask(cache, imageView, 300, 300)//Since you are using it from `Adapter` call first Constructor.
//
//            imgTask.execute(url)
//        }
    }

    @JvmStatic
    @BindingAdapter("app:timeStamp")
    fun setDate(textView: TextView, timeStamp: String?) {


        var time = ""


        try {
            val tz = TimeZone.getTimeZone("Asia/Calcutta");
            val cal = Calendar.getInstance(tz);
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            sdf.calendar = cal;
            cal.time = sdf.parse(timeStamp);
            val date = cal.getTime();

            var sdf1 = SimpleDateFormat("MM/DD/YYYY HH:mm")
            time = sdf1.format(date)
        } catch (e: Exception) {
            time = "00-00-0000 00:00"
        }

        textView.text = MainApplication.context?.getString(com.example.grabtest.R.string.last_update_at) + " " + time
    }

}
