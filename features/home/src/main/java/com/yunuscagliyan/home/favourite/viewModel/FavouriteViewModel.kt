package com.yunuscagliyan.home.favourite.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.favourite.ui.FavouriteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(

):CoreViewModel<FavouriteState,FavouriteEvent>() {
    override fun getInitialState(): FavouriteState = FavouriteState()

    override fun onEvent(event: FavouriteEvent) {

    }
}