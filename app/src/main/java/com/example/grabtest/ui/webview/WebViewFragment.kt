package com.example.grabtest.ui.webview

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.grabtest.R
import com.example.grabtest.databinding.WebviewFragmentBinding
import android.webkit.WebViewClient


class WebViewFragment : Fragment() {


    private var url: String? = null

    private var binding: WebviewFragmentBinding? = null


    companion object {
        val extraKey = "extra"

        fun getInstance(webPageUrl: String?): WebViewFragment {
            val bundle = Bundle()
            bundle.putString(extraKey, webPageUrl)
            val fragment = WebViewFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(extraKey)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.webview_fragment, container, false)


        val webSettings = binding?.webview?.settings
        webSettings?.setJavaScriptEnabled(true)
        binding?.webview?.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                binding?.progressBar?.visibility = View.GONE
            }
        }
        binding?.webview?.loadUrl(url)
        return binding?.root
    }
}
