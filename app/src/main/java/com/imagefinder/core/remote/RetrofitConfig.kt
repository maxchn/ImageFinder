package com.imagefinder.core.remote

object RetrofitConfig {
    lateinit var baseUrl: String

    internal var enableLogging = false

    internal var okHttpConfig: OkHttpConfig? = null

    fun enableLogging() {
        enableLogging = true
    }

    fun configureOkHttp(block: OkHttpConfig.() -> Unit) {
        OkHttpConfig().apply(block).let {
            okHttpConfig = it
        }
    }
}
