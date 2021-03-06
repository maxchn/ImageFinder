package com.imagefinder.core.remote

import java.util.concurrent.TimeUnit

class OkHttpConfig {
    var timeout: Timeout? = null

    data class Timeout(
        val time: Long,
        val timeUnit: TimeUnit
    )
}
