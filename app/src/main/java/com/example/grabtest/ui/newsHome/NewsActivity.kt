package com.example.grabtest.ui.newsHome

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.grabtest.R
import com.example.grabtest.databinding.NewsActivityBinding
import com.example.grabtest.ui.webview.WebViewFragment

class NewsActivity : AppCompatActivity(), NewsHomeFragment.FragmentInteractionInterface {


    var binding: NewsActivityBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<NewsActivityBinding>(this, R.layout.news_activity)
        addNewsFragment()
    }

    private fun addNewsFragment() {
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, NewsHomeFragment.getInstance()).commit()
    }

    override fun openNewsDetails(webPageUrl: String?) {
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, WebViewFragment.getInstance(webPageUrl)).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}