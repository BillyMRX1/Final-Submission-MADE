package com.mrx.mangastone.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mrx.core.domain.model.Manga
import com.mrx.mangastone.R
import com.mrx.mangastone.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailManga = intent.getParcelableExtra<Manga>(EXTRA_DATA)
        showDetailManga(detailManga)
    }

    private fun showDetailManga(detailManga: Manga?) {
        detailManga?.let {
            supportActionBar?.title = detailManga.title
            binding.apply {
                tvTitle.text = detailManga.title
                tvRank.text = detailManga.rank.toString()
                tvStartDate.text = detailManga.startDate
                tvMember.text = detailManga.members.toString()
                tvScore.text = detailManga.score.toString()
                Glide.with(this@DetailActivity)
                    .load(detailManga.imageUrl)
                    .into(binding.imgManga)

                var statusFavorite = detailManga.isFavorite
                setStatusFavorite(statusFavorite)
                btnFav.setOnClickListener {
                    statusFavorite = !statusFavorite
                    viewModel.setFavoriteManga(detailManga, statusFavorite)
                    setStatusFavorite(statusFavorite)
                }

                btnShare.setOnClickListener {
                    val goShare = Intent()
                    goShare.action = Intent.ACTION_SEND
                    goShare.putExtra(
                        Intent.EXTRA_TEXT,
                        detailManga.url
                    )
                    goShare.type = "text/plain"
                    startActivity(Intent.createChooser(goShare, "Share To:"))
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.btnFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
        } else {
            binding.btnFav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}