package com.sst.ngisiyuk.util

import android.app.Activity
import android.util.DisplayMetrics

interface DimensionCatcher {

    fun setDisplayDimension(metrics: DisplayMetrics, activity: Activity){
        activity.windowManager.defaultDisplay.getMetrics(metrics)
    }
}