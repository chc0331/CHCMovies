package com.example.chcmovies.ui.activities.movie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.chcmovies.R
import com.example.chcmovies.data.MovieItem
import com.example.chcmovies.databinding.ActivityMovieBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: ActivityMovieBinding

    companion object {
        const val IMDB_DOT_COM = "https://imdb.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie)

        val intent = intent
        val intentExtra = intent.getSerializableExtra("movie") as MovieItem

        binding.apply {
            vm = ViewModelProvider(this@MovieActivity, factory).get(MovieViewModel::class.java)
            movie = intentExtra
            directorsList = movie?.director
            actorsList = movie?.actors
            genresList = movie?.genres
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = ""
        }
        observeViewModel()
    }

    private fun observeViewModel() {
        binding.vm?.openImdb?.observe(this@MovieActivity) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(IMDB_DOT_COM))
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                finishAfterTransition()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}