package com.notes.features.settings

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.notes.R
import com.notes.databinding.ActivitySettingsBinding
import com.notes.databinding.DialogClearedNotesBinding
import com.notes.features.home.vm.HomeViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#280947")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        bindingView()
    }

    private fun bindingView() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteAllNotes.setOnClickListener {
            homeViewModel.delete()
            popupDialog(binding.root, getString(R.string.text_all_notes_have_been_cleared))
        }
    }

    private fun popupDialog(view: View, msg: String) {
        view.post {
            val popupBinding = DialogClearedNotesBinding.inflate(layoutInflater)
            val popupView = popupBinding.root

            val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true)
            popupWindow.isClippingEnabled = false
            popupWindow.contentView = popupView

            popupBinding.tvMsg.text = msg

            popupBinding.lyPopupBackground.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            popupWindow.isFocusable = true
        }
    }
}