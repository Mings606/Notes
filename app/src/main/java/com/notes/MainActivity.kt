package com.notes

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.notes.databinding.ActivityMainBinding
import com.notes.features.home.HomeFragment
import com.notes.features.home.SummaryFragment
import com.notes.features.newNotes.NewNotesActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#280947")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        bindingView()
        binding.btnHome.performClick()
    }

    private fun bindingView() {

        binding.btnHome.setOnClickListener {
            startFragment(HomeFragment::class.java.name)
        }

        binding.btnSummary.setOnClickListener {
            startFragment(SummaryFragment::class.java.name)
        }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, NewNotesActivity::class.java))
        }
    }

    private fun startFragment(fragmentName: String) {
        droidButtonMenuClick(fragmentName)
        val fragment = when (fragmentName) {
            HomeFragment::class.java.name -> HomeFragment()
            SummaryFragment::class.java.name -> SummaryFragment()
            else -> BlankFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    fun droidButtonMenuClick(fragmentName: String) {
        binding.btnHome.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home_off, 0, 0)
        binding.btnSummary.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.summary_off, 0, 0)

        binding.btnHome.setTextColor(resources.getColor(R.color.colorGrey_918DAC))
        binding.btnSummary.setTextColor(resources.getColor(R.color.colorGrey_918DAC))

        when (fragmentName) {
            HomeFragment::class.java.name -> {
                binding.btnHome.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.home_on, 0, 0)
                binding.btnHome.setTextColor(resources.getColor(R.color.colorPink))
            }

            SummaryFragment::class.java.name -> {
                binding.btnSummary.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.summary_on, 0, 0)
                binding.btnSummary.setTextColor(resources.getColor(R.color.colorPink))
            }
        }
    }
}