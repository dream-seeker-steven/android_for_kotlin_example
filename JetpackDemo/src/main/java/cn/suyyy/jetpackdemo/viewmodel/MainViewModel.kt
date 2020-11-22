package cn.suyyy.jetpackdemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 因viewModel更新数据无法通知activity所以使用 liveData 观察者
 */
class MainViewModel(countReserved: Int) : ViewModel() {
    var counter = MutableLiveData<Int>()

    init {
        counter.value = countReserved
    }

    fun plusOne() {
        val count = counter.value ?: 0
        counter.value = count + 1
    }

    fun clear(){
        counter.value = 0
    }
}