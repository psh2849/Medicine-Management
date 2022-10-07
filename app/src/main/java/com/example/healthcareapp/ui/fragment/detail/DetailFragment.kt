package com.example.healthcareapp.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.data.database.entity.FavoriteEntity
import com.example.healthcareapp.databinding.FragmentDetailBinding
import com.example.healthcareapp.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    private lateinit var menuItem: MenuItem
    private var isFavorite = false
    private var favoriteId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initMenu()
    }

    private fun initViews() {

        Glide.with(requireContext())
            .load(args.item.itemImage)
            .error(R.drawable.ic_error_placeholder)
            .placeholder(R.drawable.ic_error_placeholder)
            .into(binding.imageViewDetailMainImage)


        parseDetailText(binding.textViewDetailTitle, args.item.itemName)
        parseDetailText(binding.textViewDetailCompany, args.item.companyName)
        parseDetailText(binding.textViewDetailEffect, args.item.efficacy)
        parseDetailText(binding.textViewDetailUse, args.item.instruction)
        parseDetailText(binding.textViewDetailCaution, args.item.caution)
        parseDetailText(binding.textViewDetailSideEffect, args.item.sideEffect)
        parseDetailText(binding.textViewDetailInteraction, args.item.interaction)
        parseDetailText(binding.textViewDetailStorage, args.item.storageMethod)
    }

    private fun parseDetailText(textView: TextView, text: String?) {
        text?.let {
            textView.text = Jsoup.parse(text).text()
        }
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_menu, menu)
                menuItem = menu.findItem(R.id.favorite_menu_heart)!!

                checkFavoriteStatus()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.favorite_menu_heart -> {
                        if(!isFavorite) {
                            saveFavoriteMedicine()
                        } else {
                            deleteFavoriteMedicine()
                        }
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun checkFavoriteStatus() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.RESUMED) {
                favoriteViewModel.readFavoriteMedicine.observe(viewLifecycleOwner) { favoriteEntity ->
                    try {
                        if (favoriteEntity.isNotEmpty()) {
                            for (medicine in favoriteEntity) {
                                if (medicine.item.itemName == args.item.itemName && medicine.item.itemImage == args.item.itemImage) {
                                    isFavorite = true
                                    favoriteId = medicine.id
                                    changeMenuBackgroundColor(R.color.red)
                                    break
                                }
                            }
                            if (!isFavorite) {
                                changeMenuBackgroundColor(R.color.white)
                            }
                        } else {
                            changeMenuBackgroundColor(R.color.white)
                        }
                    } catch (e: Exception) {
                        Log.d("DetailFragment", e.message.toString())
                    }
                }
            }
        }
    }

    private fun changeMenuBackgroundColor(color: Int) {
        menuItem.icon.setTint(ContextCompat.getColor(requireContext(), color))
    }

    private fun saveFavoriteMedicine() {
        val favoriteEntity = FavoriteEntity(
            favoriteId,
            args.item
        )

        isFavorite = true
        favoriteViewModel.insertFavoriteMedicine(favoriteEntity)
        changeMenuBackgroundColor(R.color.red)
    }

    private fun deleteFavoriteMedicine() {
        val favoriteEntity = FavoriteEntity(
            favoriteId,
            args.item
        )

        isFavorite = false
        favoriteViewModel.deleteFavoriteMedicine(favoriteEntity)
        changeMenuBackgroundColor(R.color.white)
    }
}