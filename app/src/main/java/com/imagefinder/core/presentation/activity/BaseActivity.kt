package com.imagefinder.core.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.imagefinder.core.presentation.dialog.ProgressDialog
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.KodeinTrigger
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein

private const val PROGRESS_DIALOG_TAG = "progress_dialog"

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

    protected var showModalProgress: Boolean = false
        set(value) {
            if (value == field) return else field = value
            if (value) {
                showModalProgress()
            } else {
                hideModalProgress()
            }
        }

    fun showToast(value: String) {
        Toast.makeText(applicationContext, value, Toast.LENGTH_LONG).show()
    }

    private fun showModalProgress() {
        hideModalProgress()
        ProgressDialog.newInstance().showNow(this.supportFragmentManager, PROGRESS_DIALOG_TAG)
    }

    private fun hideModalProgress() {
        dismissDialogByTag(PROGRESS_DIALOG_TAG)
    }
}

fun AppCompatActivity.dismissDialogByTag(tag: String) {
    (supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismissAllowingStateLoss()
}
