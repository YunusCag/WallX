package com.yunuscagliyan.home.main.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.main.ui.MainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

):CoreViewModel<MainState,MainEvent>() {
    override fun getInitialState(): MainState  = MainState()

    override fun onEvent(event: MainEvent) {

    }
}