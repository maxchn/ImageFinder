package com.imagefinder.core.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

abstract class BaseActivity : AppCompatActivity(), KodeinAware {

    private val _parentKodein by closestKodein()

    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        import(diModule())
    }

    override val kodeinTrigger = KodeinTrigger()

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        kodeinTrigger.trigger()

        super.onCreate(savedInstanceState)
    }

    abstract fun diModule(): Kodein.Module

    fun showToast(value: String) {
        Toast.makeText(applicationContext, value, Toast.LENGTH_LONG).show()
    }
}
