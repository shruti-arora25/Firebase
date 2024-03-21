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
import com.example.firebasetwo.R
import com.example.firebasetwo.ViewModel.notesViewM
import com.example.firebasetwo.databinding.FragmentCreateNotesBinding
import com.example.firebasetwo.model.Notes
import java.util.Date


class create_notes : Fragment() {

    private var priority = "1"
    private lateinit var bind: FragmentCreateNotesBinding
    private val viewModel:notesViewM by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)


        bind.saveNote.setOnClickListener {
            createNote(it)
        }


        bind.greendot.setOnClickListener {
            priority = "1"
            bind.greendot.setImageResource(R.drawable.baseline_done_24)
            bind.reddot.setImageResource(0)
            bind.yellowdot.setImageResource(0)
        }

        bind.reddot.setOnClickListener {
            priority = "2"
            bind.reddot.setImageResource(R.drawable.baseline_done_24)
            bind.greendot.setImageResource(0)
            bind.yellowdot.setImageResource(0)
        }

        bind.yellowdot.setOnClickListener {
            priority = "3"
            bind.yellowdot.setImageResource(R.drawable.baseline_done_24)
            bind.reddot.setImageResource(0)
            bind.greendot.setImageResource(0)
        }

        return bind.root
    }

    private fun createNote(it: View) {
        val title = bind.addTitle.text.toString()
        val subtitle = bind.AddSubtitle.text.toString()
        val note = bind.AddNote.text.toString()

        val d = Date()

        var date = android.text.format.DateFormat.format("MMMM d, YYYY ", d.time).toString()

        val data=Notes(id =null,title=title, subtitle=subtitle,notes=note,priority=priority, date = date)
        
        viewModel.addNotes(data)

        Toast.makeText(context,"Note saved!",Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_create_notes_to_all_notes,null,NavOptions.Builder().setPopUpTo(R.id.create_notes,true).build())


    }

}