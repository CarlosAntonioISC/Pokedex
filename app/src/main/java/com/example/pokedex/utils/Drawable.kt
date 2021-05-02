package com.example.pokedex.utils

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.annotation.ColorInt

object Drawable {
     fun Drawable.overrideColor(@ColorInt colorInt: Int) {
        when (this) {
            is GradientDrawable -> { setColor(colorInt) }
            is ShapeDrawable -> paint.color = colorInt
            is ColorDrawable -> color = colorInt
        }
    }
}