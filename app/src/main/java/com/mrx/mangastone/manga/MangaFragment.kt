package com.mrx.mangastone.manga

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mrx.mangastone.R
import com.mrx.mangastone.databinding.FragmentMangaBinding
import com.mrx.mangastone.detail.DetailActivity
import com.mrx.core.data.Resource
import com.mrx.core.ui.MangaAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MangaFragment : Fragment() {

    private val viewModel: MangaViewModel by viewModel()
    private var _binding: FragmentMangaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMangaBinding.inflate(layoutInflater, container, false)
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
            viewModel.manga.observe(viewLifecycleOwner, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            mangaAdapter.setData(it.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                it.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
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