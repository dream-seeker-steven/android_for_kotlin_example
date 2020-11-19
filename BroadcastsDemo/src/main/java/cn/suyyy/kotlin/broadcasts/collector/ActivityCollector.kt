package cn.suyyy.kotlin.broadcasts.collector

import android.app.Activity

/**
 * 管理Activity
 */
object ActivityCollector {

    private val activities = ArrayList<Activity>()

    fun addActivity(activity : Activity){
        activities.add(activity)
    }

    fun removeActivity(activity : Activity){
        activities.remove(activity)
    }

    /**
     * 清空所有Activity
     */
    fun finishAll(){
        for (activity in activities){
            if (!activity.isFinishing){
                activity.finish()
            }
        }
        activities.clear()
    }
}