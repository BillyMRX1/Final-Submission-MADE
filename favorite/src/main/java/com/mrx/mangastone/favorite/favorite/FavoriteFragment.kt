package com.mrx.mangastone.favorite.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mrx.mangastone.detail.DetailActivity
import com.mrx.mangastone.favorite.databinding.FragmentFavoriteBinding
import com.mrx.mangastone.favorite.di.favModule
import com.mrx.core.ui.MangaAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(favModule)
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val mangaAdapter = MangaAdapter()
            mangaAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoriteViewModel.favoriteManga.observe(viewLifecycleOwner, { dataFav ->
                mangaAdapter.setData(dataFav)
                binding.viewEmpty.root.visibility =
                    if (dataFav.isNotEmpty()) View.GONE else View.VISIBLE
            })

            with(binding.rvManga) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = mangaAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
