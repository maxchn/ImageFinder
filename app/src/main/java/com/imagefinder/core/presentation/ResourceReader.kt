package com.imagefinder.core.presentation

import androidx.annotation.*
import java.io.InputStream

interface ResourceReader {
    fun getString(@StringRes resId: Int): String
    fun getString(@StringRes resId: Int, vararg args: Any): String
    fun getQuantityString(@PluralsRes pluralResId: Int, quantity: Int, vararg formatArgs: Any): String
    fun getStringArray(@ArrayRes resId: Int): Array<String>

    @ColorInt
    fun getColor(@ColorRes resId: Int): Int

    fun getAsset(path: String): InputStream
}
