package com.notes.features.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.notes.MainActivity
import com.notes.R
import com.notes.data.NotesTable
import com.notes.databinding.FragmentHomeBinding
import com.notes.features.home.adapter.NotesAdapter
import com.notes.features.home.vm.HomeViewModel
import com.notes.features.settings.SettingsActivity

class HomeFragment : Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentHomeBinding

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        activity = getActivity() as MainActivity

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        bindingView()
        dbGetAllHistory()

        return binding.root
    }

    private fun bindingView() {
        binding.btnSettings.setOnClickListener {
            activity.startActivity(Intent(activity, SettingsActivity::class.java))
        }
    }

    private fun dbGetAllHistory() {
        homeViewModel.readAllData.observe(activity) {
            binding.rvWorkAndStudy.adapter = null
            adapterPairing(it)
        }
    }

    private fun getLatestThreeItemsPerCategory(notes: List<NotesTable>): Map<Int, List<NotesTable>> {
        return notes.groupBy { it.categoryId }.mapValues { entry ->
            entry.value.sortedByDescending { it.createAt }.take(3)
        }
    }

    private fun adapterPairing(list: List<NotesTable>) {
        activity.runOnUiThread {
            val categorizedNotes = getLatestThreeItemsPerCategory(list)

            if (binding.rvWorkAndStudy.adapter == null) {
                binding.rvWorkAndStudy.adapter = NotesAdapter(categorizedNotes)
            } else {
                Handler().postDelayed({
                    binding.rvWorkAndStudy.adapter?.notifyDataSetChanged()
                }, 100)
            }
        }
    }
}