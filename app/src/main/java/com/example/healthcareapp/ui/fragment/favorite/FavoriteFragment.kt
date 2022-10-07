package com.example.healthcareapp.ui.fragment.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthcareapp.adapter.FavoriteAdapter
import com.example.healthcareapp.databinding.FragmentFavoriteBinding
import com.example.healthcareapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val mAdapter by lazy { FavoriteAdapter() }
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                favoriteViewModel.readFavoriteMedicine.observe(viewLifecycleOwner) { favoriteEntity ->
                    if (favoriteEntity.isEmpty()) {
                        binding.recyclerViewFavorite.visibility = View.INVISIBLE
                        binding.textViewFavoriteNoData.visibility = View.VISIBLE
                    } else {
                        mAdapter.setData(favoriteEntity)
                        binding.recyclerViewFavorite.adapter = mAdapter
                        binding.recyclerViewFavorite.layoutManager = LinearLayoutManager(requireContext())

                        binding.recyclerViewFavorite.visibility = View.VISIBLE
                        binding.textViewFavoriteNoData.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }
}