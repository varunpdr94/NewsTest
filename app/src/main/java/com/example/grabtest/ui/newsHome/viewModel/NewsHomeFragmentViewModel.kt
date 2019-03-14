package com.example.grabtest.ui.newsHome.viewModel

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import com.example.grabtest.R
import com.example.grabtest.network.RestServiceInterface
import com.example.grabtest.ui.application.MainApplication
import com.example.grabtest.ui.newsHome.model.NewsListResponse
import com.example.grabtest.utils.BaseTModel
import com.example.grabtest.utils.NetworkUtils
import com.paytmmoney.ui.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NewsHomeFragmentViewModel @Inject constructor(val restService: RestServiceInterface) : BaseViewModel() {

    var newsHomeLiveData: MutableLiveData<NewsListResponse> = MutableLiveData()

    var errorLiveData: MutableLiveData<String> = MutableLiveData()

    var progressSubject: PublishSubject<Boolean> = PublishSubject.create()

    var bottomProgress: PublishSubject<Boolean> = PublishSubject.create()


    var currentPage = 1
    var pageSize = 20;
    var totalCount = pageSize + 10

    private var loadingBottom: Boolean = false

    @SuppressLint("CheckResult")
    fun getNewsListData(){

        progressSubject.onNext(true)
        restService.getNewsData(NetworkUtils.newsUrl, getQueryParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<Response<NewsListResponse>>() {
                    override fun onError(e: Throwable) {
                        errorLiveData.value = MainApplication.context?.getString(R.string.something_went_wrong)
                        progressSubject.onNext(false)

                    }

                    override fun onNext(t: Response<NewsListResponse>) {
                        val model = t.body()
                        totalCount = model?.totalResults ?: 0
                        progressSubject.onNext(false)
                        newsHomeLiveData.value = t.body()


                    }

                    override fun onComplete() {
                    }
                })
    }

    fun getList(it: NewsListResponse?): List<BaseTModel> {
        var list = ArrayList<BaseTModel>()
        for (item in it?.articles!!)
            list.add(item)

        return list
    }

    fun addRecyclerObservable(booleanObservable: Observable<Boolean>) {

        booleanObservable
                .debounce(150, TimeUnit.MILLISECONDS)
                .filter {
                    if (!loadingBottom && totalCount > pageSize * currentPage) {
                        currentPage++
                        loadingBottom = true
                       bottomProgress.onNext(true)
                        true
                    } else false
                }.concatMap<Response<NewsListResponse>> {
                    restService.getNewsData(NetworkUtils.newsUrl, getQueryParams()).subscribeOn(Schedulers.io())
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<Response<NewsListResponse>>() {
                    override fun onError(e: Throwable) {
                        currentPage--
                        errorLiveData.value = MainApplication.context?.getString(R.string.something_went_wrong)
                        bottomProgress.onNext(false)
                    }

                    override fun onNext(t: Response<NewsListResponse>) {
                        bottomProgress.onNext(false)
                        newsHomeLiveData.value = t.body()
                    }

                    override fun onComplete() {
                    }
                })

    }

     fun getQueryParams(): Map<String, Object> {
        var queryMap = HashMap<String, Object>()
        queryMap.put(NetworkUtils.key_country, NetworkUtils.countryIN as Object);
        if (currentPage > 1) {
            queryMap.put(NetworkUtils.PAGE, currentPage as Object)
            queryMap.put(NetworkUtils.PAGE_SIZE, pageSize as Object)
        }
        return queryMap


    }
}