package com.puxiansheng.www.common

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.widget.AppCompatImageView


class DragFloatActionButton : AppCompatImageView {
    private var parentHeight: Int = 0
    private var parentWidth: Int = 0


    constructor(context: Context) : super(context, null)
    constructor(context: Context, attr: AttributeSet?) : super(context, attr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var lastX: Int = 0
    private var lastY: Int = 0

    private var isDrag: Boolean = false
    private var isMove: Boolean = false

    @SuppressLint("LongLogTag")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val rawX = event.rawX.toInt()
        val rawY = event.rawY.toInt()
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                isPressed = true
                isDrag = false
                parent.requestDisallowInterceptTouchEvent(true)
                lastX = rawX
                lastY = rawY
                val parent: ViewGroup
                if (getParent() != null) {
                    parent = getParent() as ViewGroup
                    parentHeight = parent.height
                    parentWidth = parent.width
                }
            }
            MotionEvent.ACTION_MOVE -> {
                isMove = true
                isDrag = !(parentHeight <= 0 || parentWidth === 0)
                val dx = rawX - lastX
                val dy = rawY - lastY
                //这里修复一些华为手机无法触发点击事件
                val distance = Math.sqrt((dx * dx + dy * dy).toDouble()).toInt()
                if (distance == 0) {
                    isDrag = false
                } else {
                    var x = x + dx
                    var y = y + dy
                    //检测是否到达边缘 左上右下
                    x =
                        if (x < 0) 0F else if (x > parentWidth - width) (parentWidth - width).toFloat() else x
                    y =
                        if (getY() < 0) 0F else if (getY() + height > parentHeight) (parentHeight - height).toFloat() else y
                    setX(x)
                    setY(y)
                    lastX = rawX
                    lastY = rawY
//                    Log.i(
//                        "DragFloatActionButton",
//                        "isDrag=" + isDrag + "  getX=" + getX() + ";getY=" + getY() + ";parentWidth=" + parentWidth
//                    )
                }
            }
//                MotionEvent.ACTION_UP -> if (!isNotDrag()) {
//                    //恢复按压效果
//                    isPressed = false
//                    //Log.i("getX="+getX()+"；screenWidthHalf="+screenWidthHalf);
//                    if (rawX >= parentWidth / 2) {
//                        //靠右吸附
//                        animate().setInterpolator(DecelerateInterpolator())
//                                .setDuration(500)
//                                .xBy(parentWidth - width - x)
//                                .start()
//                    } else {
//                        //靠左吸附
//                        val oa = ObjectAnimator.ofFloat(this, "x", x, 0F)
//                        oa.setInterpolator(DecelerateInterpolator())
//                        oa.setDuration(500)
//                        oa.start()
//                    }
//                }
        }
        //如果是拖拽则消s耗事件，否则正常传递即可。
        return !isNotDrag() || super.onTouchEvent(event)
    }

    private fun isNotDrag(): Boolean {
        return !isDrag && (x == 0f || x == (parentWidth - width).toFloat())
    }


    fun rollBack() {
        if (!isNotDrag() && isMove) {
            isPressed = false
            val location = IntArray(2)
            this.getLocationOnScreen(location)
            val viewX = location[0]
//            Log.i(
//                "DragFloatActionButton",
//                "isDrag=" + isDrag + "  left=" + left + ";parentWidth=" + parentWidth
//            )
            if (viewX >= parentWidth / 2) {
                //靠右吸附
                animate().setInterpolator(DecelerateInterpolator())
                    .setDuration(300)
                    .xBy(parentWidth - width - x)
                    .start()
            } else {
                //靠左吸附
                val oa = ObjectAnimator.ofFloat(this, "x", x, 0F)
                oa.interpolator = DecelerateInterpolator()
                oa.duration = 300
                oa.start()
            }
        }
    }
}
