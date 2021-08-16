package com.sst.ngisiyuk.util

import android.app.Activity
import android.util.DisplayMetrics
import kotlin.properties.Delegates


open class DisplayMetrics(val activity: Activity) {
    val metrics = DisplayMetrics()

    private var width by Delegates.notNull<Int>()
    private var height by Delegates.notNull<Int>()



    fun width():Int {
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return width
    }

    fun height(): Int{
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return height
    }

}