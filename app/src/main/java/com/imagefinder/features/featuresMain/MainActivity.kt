package com.imagefinder.features.featuresMain

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.imagefinder.R
import com.imagefinder.core.presentation.activity.BindingActivity
import com.imagefinder.core.presentation.hideKeyboard
import com.imagefinder.databinding.ActivityMainBinding
import com.imagefinder.features.featuresMain.common.ImagesAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.generic.instance

class MainActivity : BindingActivity<ActivityMainBinding>() {

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun diModule() = MainModule.get()

    override val viewModel: MainContract.ViewModel by instance()

    private val adapter: ImagesAdapter by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        rv_search_history.adapter = adapter

        edit_text_query.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener if (actionId == EditorInfo.IME_ACTION_GO) {
                this.hideKeyboard()
                viewModel.searchImage(edit_text_query.text.toString())
                edit_text_query.text = null
                true
            } else {
                false
            }
        }

        viewModel.networkManager.applyDefaultObserver(this)

        viewModel.images.observe(this, {
            if (it.isNotEmpty()) {
                adapter.update(it)
                rv_search_history.scrollToPosition(0)
            }
        })

        viewModel.message.observe(this, {
            it?.let {
                showToast(it)
            }
        })

        viewModel.isLoading.observe(this, {
            showModalProgress = it ?: false
        })
    }
}
