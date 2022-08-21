package com.example.chcmovies.ui.activities.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chcmovies.R
import com.example.chcmovies.databinding.ActivitySplashBinding
import com.example.chcmovies.ui.activities.feed.FeedActivity
import dagger.android.AndroidInjection
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = ViewModelProvider(this, factory).get(SplashViewModel::class.java)
        binding.lifecycleOwner = this@SplashActivity
        binding.viewModel?.launchActivityEvent?.observe(this) { activityName ->
            when (activityName) {
                FeedActivity::class.java.simpleName -> {
                    startActivity(FeedActivity.getIntent(this))
                }
                else -> throw IllegalArgumentException("Undefined activity")
            }
            finish()
        }
    }
}