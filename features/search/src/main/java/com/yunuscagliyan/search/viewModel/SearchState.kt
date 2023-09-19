package com.yunuscagliyan.search.viewModel

import androidx.compose.runtime.Stable
import com.yunuscagliyan.core.util.Constant.StringParameter.EMPTY_STRING

@Stable
data class SearchState(
    val search: String = EMPTY_STRING
)
