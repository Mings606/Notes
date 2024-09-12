package com.notes.features.newNotes

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.notes.R
import com.notes.data.NotesTable
import com.notes.databinding.ActivityNewNotesBinding
import com.notes.features.newNotes.vm.NewNotesViewModel

class NewNotesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNotesBinding
    private lateinit var notesViewModel: NewNotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.parseColor("#280947")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_notes)

        notesViewModel = ViewModelProvider(this)[NewNotesViewModel::class.java]

        notesViewModel.notesCategory = intent.getIntExtra("category", 0)
        notesViewModel.notesDescription = intent.getStringExtra("description") ?: ""

        bindingView()
    }

    private fun bindingView() {
        binding.etNotes.setText(notesViewModel.notesDescription)
        binding.tvCategory.text = when (notesViewModel.notesCategory) {
            1 -> "Work and study"
            2 -> "Life"
            3 -> "Health and well-being"
            else -> "Choose a Category"
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChooseCategory.setOnClickListener {
            val categories = listOf("Work and Study", "Life", "Health and Well-being")

            val categoryPicker = AlertDialog.Builder(this).setTitle("Choose a Category").setItems(categories.toTypedArray()) { _, which ->
                when (which) {
                    0 -> {
                        binding.tvCategory.text = "Work and study"
                        notesViewModel.notesCategory = 1
                    }

                    1 -> {
                        binding.tvCategory.text = "Life"
                        notesViewModel.notesCategory = 2
                    }

                    2 -> {
                        binding.tvCategory.text = "Health and well-being"
                        notesViewModel.notesCategory = 3
                    }
                }
            }.create()

            categoryPicker.show()
        }

        binding.btnSave.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        var isValid = true
        if (notesViewModel.notesCategory == 0) {
            binding.tvCategoryError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvCategoryError.visibility = View.GONE
        }

        if (binding.etNotes.text.toString().trim().isEmpty()) {
            binding.tvNotesError.visibility = View.VISIBLE
            isValid = false
        } else {
            binding.tvNotesError.visibility = View.GONE
        }
        if (isValid) {
            saveNote()
        }
    }

    private fun saveNote() {
        val notesDescription = binding.etNotes.text.toString()
        val timestamp = System.currentTimeMillis()
        if (notesDescription.isNotEmpty()) {
            val task = NotesTable(null, notesViewModel.notesCategory, notesDescription, timestamp)
            notesViewModel.insert(task)
            binding.etNotes.text.clear()
            Toast.makeText(this, "Notes saved successfully", Toast.LENGTH_LONG).show()
            finish()
        }
    }
}