package com.sst.ngisiyuk.util

import android.content.Context
import android.view.View
import android.view.ViewParent
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.createBalloon
import com.sst.ngisiyuk.R

class PopUpBaloon(context: Context, view:View) {
    val baloon = createBalloon(context){
        setArrowSize(10)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setArrowPosition(0.7f)
        setCornerRadius(4f)
        setPaddingHorizontal(20)
        setBackgroundColorResource(R.color.colorPrimary)
        setBalloonAnimation(BalloonAnimation.FADE)
        setLifecycleOwner(lifecycleOwner)
        setLayout(view)
        arrowSize = 0
    }

    fun showBaloon(view: ViewParent){
        baloon.show(view as View)
    }

}