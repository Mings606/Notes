package com.notes.features.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.notes.R
import com.notes.data.NotesTable
import com.notes.databinding.ItemNoteBinding
import com.notes.databinding.ItemSectionHeaderBinding
import com.notes.features.newNotes.NewNotesActivity

private const val VIEW_TYPE_HEADER = 0
private const val VIEW_TYPE_NOTE = 1

class NotesAdapter(
    categorizedNotes: Map<Int, List<NotesTable>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<Any>()

    init {
        categorizedNotes.forEach { (categoryId, notes) ->
            dataList.add(categoryId)
            dataList.addAll(notes)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position] is Int) VIEW_TYPE_HEADER else VIEW_TYPE_NOTE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemSectionHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SectionHeaderViewHolder(binding)
        } else {
            val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            NoteViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SectionHeaderViewHolder) {
            val categoryId = dataList[position] as Int
            holder.bind(categoryId)
        } else if (holder is NoteViewHolder) {
            val note = dataList[position] as NotesTable
            holder.bind(note)

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, NewNotesActivity::class.java)
                intent.putExtra("category", note.categoryId)
                intent.putExtra("description", note.notesDescription)
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    class SectionHeaderViewHolder(private val binding: ItemSectionHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryId: Int) {
            val sectionTitle = when (categoryId) {
                1 -> {
                    binding.iconTitle.setImageResource(R.mipmap.icon_work_and_study)
                    "Work and study"
                }

                2 -> {
                    binding.iconTitle.setImageResource(R.mipmap.icon_life)
                    "Life"
                }

                3 -> {
                    binding.iconTitle.setImageResource(R.mipmap.icon_health_and_wellness)
                    "Health and wellness"
                }

                else -> "Unknown"
            }
            binding.sectionTitle.text = sectionTitle
        }
    }

    class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: NotesTable) {
            val maxLength = 20
            binding.tvDescription.text = if (note.notesDescription.length > maxLength) {
                note.notesDescription.take(maxLength) + "..."
            } else {
                note.notesDescription
            }
        }
    }
}
