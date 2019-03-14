package com.example.grabtest

import com.example.grabtest.network.RestServiceInterface
import com.example.grabtest.ui.newsHome.model.Article
import com.example.grabtest.ui.newsHome.model.NewsListResponse
import com.example.grabtest.ui.newsHome.viewModel.NewsHomeFragmentViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsHomeFragmentViewModelTest {

    private var viewModel: NewsHomeFragmentViewModel? = null

    @Mock
    private lateinit var restService: RestServiceInterface

    @Mock
    private lateinit var response: NewsListResponse


    @Before
    fun setUp() {
        viewModel = NewsHomeFragmentViewModel(restService = restService)
    }


    @Test
    fun validateLiveData() {
        assertTrue("test 1", viewModel?.newsHomeLiveData?.value == null)
    }

    @Test
    fun checkPageSize() {
        assertTrue("test 2", viewModel?.pageSize == 20)
    }

    @Test
    fun checkTotalItemCount() {
        assertTrue("test 3", viewModel?.totalCount == 30)
    }

    @Test
    fun checkQueryParamNullCondition() {
        assertFalse("test 4", viewModel?.getQueryParams() == null)
    }

    @Test
    fun checkGetListMethod() {
        response.articles = ArrayList<Article>()
        assertTrue("test 5", viewModel?.getList(response) != null)
    }


}