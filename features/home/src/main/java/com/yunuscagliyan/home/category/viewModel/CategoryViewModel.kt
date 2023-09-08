package com.yunuscagliyan.home.category.viewModel

import com.yunuscagliyan.core_ui.viewmodel.CoreViewModel
import com.yunuscagliyan.home.category.ui.CategoryEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(

) : CoreViewModel<CategoryState, CategoryEvent>() {
    override fun getInitialState(): CategoryState = CategoryState()

    override fun onEvent(event: CategoryEvent) {

    }

}