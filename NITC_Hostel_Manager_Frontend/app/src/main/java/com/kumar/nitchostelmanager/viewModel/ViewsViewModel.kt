package com.kumar.nitchostelmanager.viewModel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewsViewModel: ViewModel() {
    private val _isLoading = MutableLiveData(true)
    var visibleView:Int = View.VISIBLE
    var invisibleView:Int = View.INVISIBLE
    var goneView:Int = View.GONE
    val isLoading : LiveData<Boolean> = _isLoading
    fun updateLoadingState(isLoading: Boolean) {
        _isLoading.value = isLoading
    }
}