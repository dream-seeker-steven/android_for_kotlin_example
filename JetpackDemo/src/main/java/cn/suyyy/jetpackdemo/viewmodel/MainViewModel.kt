package cn.suyyy.jetpackdemo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import cn.suyyy.jetpackdemo.data.User
import cn.suyyy.jetpackdemo.singleton.Repository

/**
 * 因viewModel更新数据无法通知activity所以使用 liveData 观察者
 */
class MainViewModel(countReserved: Int) : ViewModel() {

    private val userIdLiveData = MutableLiveData<String>()

    /**
     * 数据更新改后，调用和这个转换
     */
    val user: LiveData<User> = Transformations.switchMap(userIdLiveData) { userId ->
        Log.d("log-MainViewModel", "switchMap")
        Repository.getUser(userId)
    }

    fun getUser(userId: String){
        Log.d("log-MainViewModel", "getUser")
        userIdLiveData.value = userId
    }

/*
    private val userLiveData = MutableLiveData<User>()

    val username: LiveData<String> = Transformations.map(userLiveData) { user ->
        "${user.firstName} ${user.lastName}"
    }
*/


    val counter: LiveData<Int>
        get() = _counter

    private val _counter = MutableLiveData<Int>()

    init {
        _counter.value = countReserved
    }

    fun plusOne() {
        val count = _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }


    /*
    // 错,每次回返回一个新的LiveData实例
    fun getUser(userId: String):LiveData<User> {
        return Repository.getUser(userId)
    }*/
}