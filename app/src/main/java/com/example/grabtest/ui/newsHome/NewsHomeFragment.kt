package com.example.grabtest.ui.newsHome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.grabtest.R
import com.example.grabtest.databinding.NewsHomeFragmentBinding
import com.example.grabtest.ui.application.MainApplication
import com.example.grabtest.ui.newsHome.model.Article
import com.example.grabtest.ui.newsHome.model.NewsListResponse
import com.example.grabtest.ui.newsHome.viewModel.NewsHomeFragmentViewModel
import com.example.grabtest.utils.BaseAdapter
import com.example.grabtest.utils.BaseHandler
import com.example.grabtest.utils.BaseTModel
import com.example.grabtest.utils.RxViewObservable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject



class NewsHomeFragment : Fragment(), BaseHandler<BaseTModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var listener: FragmentInteractionInterface? = null

    var binding: NewsHomeFragmentBinding? = null
    private var viewModel: NewsHomeFragmentViewModel? = null

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var adapter: BaseAdapter<BaseTModel>? = null


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as FragmentInteractionInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainApplication.appComponent?.inject(this)
        viewModel = getViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<NewsHomeFragmentBinding>(inflater, R.layout.news_home_fragment, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObservingLiveData()
    }

    private fun startObservingLiveData() {
        viewModel?.newsHomeLiveData?.observe(this, Observer {
            setUpAdapter(it)
        })

        viewModel?.getNewsListData()

        viewModel?.errorLiveData?.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })


        viewModel?.progressSubject?.subscribe {
            activity?.runOnUiThread(Runnable {
                binding?.progressBar?.visibility = if (it) View.VISIBLE else View.GONE
                binding?.executePendingBindings()
            }
            )}?.let {
            compositeDisposable.add(it)
        }

        viewModel?.bottomProgress?.subscribe {
            activity?.runOnUiThread(Runnable {
                binding?.progressBarBottom?.visibility = if (it) View.VISIBLE else View.GONE
                binding?.executePendingBindings()
            })
        }?.let {
            compositeDisposable.add(it)
        }
    }

    private fun setUpAdapter(it: NewsListResponse?) {
        if (viewModel?.currentPage == 1) {
            val list: List<BaseTModel>? = viewModel?.getList(it)
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            binding?.recyclerView?.layoutManager = layoutManager
            adapter = BaseAdapter<BaseTModel>(layoutResource = R.layout.news_list_item, handler = this@NewsHomeFragment)
            binding?.recyclerView?.adapter = adapter
            var paginationListener = RxViewObservable.recyclerViewObservable(binding?.recyclerView, layoutManager)
            // disposable = paginationListener?.subscribe()
            viewModel?.addRecyclerObservable(paginationListener!!)
            adapter?.updateList(list)
        } else {
            adapter?.insertIntoBottomList(viewModel?.getList(it))
        }

    }

    private fun getViewModel(): NewsHomeFragmentViewModel {
        return this@NewsHomeFragment.let { ViewModelProviders.of(it, viewModelFactory).get(NewsHomeFragmentViewModel::class.java) }
    }


    companion object {

        fun getInstance(): NewsHomeFragment {
            return NewsHomeFragment()
        }
    }


    override fun onClick(view: View?, data: BaseTModel?) {
        when (view?.id) {

            R.id.news_card_item -> {
                val article = data as Article
                listener?.openNewsDetails(article.url)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    interface FragmentInteractionInterface {
        fun openNewsDetails(webPageUrl: String?)
    }
}
