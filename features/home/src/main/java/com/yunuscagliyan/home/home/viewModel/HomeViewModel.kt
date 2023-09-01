package com.yunuscagliyan.home.home.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.home.ui.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : CoreViewModel<HomeState, HomeEvent>() {
    override fun getInitialState(): HomeState = HomeState()

    override fun onEvent(event: HomeEvent) {

    }
}