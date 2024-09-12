package com.notes.features.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.notes.MainActivity
import com.notes.R
import com.notes.databinding.FragmentSummaryBinding
import com.notes.features.home.vm.HomeViewModel

class SummaryFragment : Fragment() {
    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentSummaryBinding

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_summary, container, false)
        activity = getActivity() as MainActivity
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        dbGetAllHistory()
        bindingView()

        return binding.root
    }

    private fun bindingView() {
        binding.btnDetailWorkAndStudy.setOnClickListener {
            navigateToHomeFragment()
        }
        binding.btnDetailHomeLife.setOnClickListener {
            navigateToHomeFragment()
        }
        binding.btnDetailHealthAndWellness.setOnClickListener {
            navigateToHomeFragment()
        }
    }

    private fun navigateToHomeFragment() {
        (activity as MainActivity).droidButtonMenuClick(HomeFragment::class.java.name)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun dbGetAllHistory() {
        homeViewModel.readAllData.observe(activity) { notesList ->
            val category1Count = notesList.count { it.categoryId == 1 }
            val category2Count = notesList.count { it.categoryId == 2 }
            val category3Count = notesList.count { it.categoryId == 3 }

            binding.tvWorkAndStudy.text = String.format("This topic has a total of %s records.", category1Count)
            binding.tvHomeLife.text = String.format("This topic has a total of %s records.", category2Count)
            binding.tvHealthAndWellness.text = String.format("This topic has a total of %s records.", category3Count)
        }
    }
}