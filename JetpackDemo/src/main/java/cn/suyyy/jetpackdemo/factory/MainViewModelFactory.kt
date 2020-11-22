package cn.suyyy.jetpackdemo.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.suyyy.jetpackdemo.viewmodel.MainViewModel

class MainViewModelFactory(private val countReserved: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(countReserved) as T
    }
}