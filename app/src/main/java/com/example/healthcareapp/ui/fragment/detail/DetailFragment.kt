package com.example.healthcareapp.ui.fragment.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.healthcareapp.R
import com.example.healthcareapp.databinding.FragmentDetailBinding
import org.jsoup.Jsoup

class DetailFragment : Fragment() {

    private val args by navArgs<DetailFragmentArgs>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
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
}