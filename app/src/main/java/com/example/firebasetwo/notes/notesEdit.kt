package com.example.firebasetwo.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.R
import com.example.firebasetwo.ViewModel.notesViewM
import com.example.firebasetwo.databinding.FragmentAllNotesBinding
import com.example.firebasetwo.databinding.FragmentNotesEditBinding
import com.example.firebasetwo.model.Notes
import java.util.Date

class notesEdit : Fragment() {

    private lateinit var binding: FragmentNotesEditBinding
    val args: notesEditArgs by navArgs()
    val viewModel: notesViewM by viewModels()
    private lateinit var notes: Notes

    private var priority = "1"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesEditBinding.inflate(layoutInflater, container, false)


        notes = args.data


        binding.addTitle2.setText(notes.title)
        binding.AddSubtitle2.setText(args.data.subtitle)
        binding.AddNote2.setText(args.data.notes)

        when (notes.priority) {
            "1" -> {
                priority = "1"
                binding.greendot2.setImageResource(R.drawable.baseline_done_24)
                binding.reddot2.setImageResource(0)
                binding.yellowdot2.setImageResource(0)
            }

            "2" -> {
                priority = "2"
                binding.reddot2.setImageResource(R.drawable.baseline_done_24)
                binding.greendot2.setImageResource(0)
                binding.yellowdot2.setImageResource(0)
            }

            "3" -> {
                priority = "3"
                binding.yellowdot2.setImageResource(R.drawable.baseline_done_24)
                binding.reddot2.setImageResource(0)
                binding.greendot2.setImageResource(0)
            }
        }


        binding.saveNote.setOnClickListener {

            updatedNotes(it)

        }

        return binding.root
    }

    private fun updatedNotes(it: View) {
        val title = binding.addTitle2.text.toString()
        val subtitle = binding.AddSubtitle2.text.toString()
        val note = binding.AddNote2.text.toString()

        val d = Date()
        var date = android.text.format.DateFormat.format("MMMM d, YYYY ", d.time).toString()

        val data = Notes(
            id = null,
            title = title,
            subtitle = subtitle,
            notes = note,
            priority = priority,
            date = date
        )

        viewModel.updateNotes(data)

        Toast.makeText(context, "Note updated!", Toast.LENGTH_SHORT).show()
        findNavController().navigate(
            R.id.action_notesEdit_to_all_notes, null,
            NavOptions.Builder().setPopUpTo(R.id.notesEdit, true).build()
        )

    }

}