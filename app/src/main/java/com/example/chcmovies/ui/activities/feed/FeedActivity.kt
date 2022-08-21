package com.example.chcmovies.ui.activities.feed

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chcmovies.R
import com.example.chcmovies.databinding.ActivityFeedBinding
import com.example.chcmovies.ui.activities.movie.MovieActivity
import com.example.chcmovies.ui.adapters.FeedListAdapter
import dagger.android.AndroidInjection
import javax.inject.Inject

class FeedActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivityFeedBinding
    private lateinit var adapter: FeedListAdapter

    companion object {
        const val GITHUB_URL = "https://github.com/theapache64/topcorn"

        fun getIntent(context: Context): Intent {
            return Intent(context, FeedActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed)
        binding.apply {
            viewModel = ViewModelProvider(this@FeedActivity, factory).get(FeedViewModel::class.java)
            lifecycleOwner = this@FeedActivity
            adapter = FeedListAdapter { movie, view ->
                val intent = Intent(this@FeedActivity, MovieActivity::class.java)
                intent.putExtra("movie", movie)

                val movieThumb = Pair.create(view, view.transitionName)
                val optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this@FeedActivity,
                        movieThumb
                    )
                startActivity(
                    intent,
                    optionsCompat.toBundle()
                )
            }
            movieList.adapter = adapter
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.viewModel?.apply {
            moviesList?.observe(this@FeedActivity) {
                adapter.submitList(it.data)
            }
            openGithub?.observe(this@FeedActivity) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GITHUB_URL))
                startActivity(intent)
            }
        }
    }
}