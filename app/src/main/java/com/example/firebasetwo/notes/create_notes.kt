package com.example.firebasetwo.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.FragmentCreateNotesBinding
import java.util.Date


class create_notes : Fragment() {

    var priority = 1

    private lateinit var bind: FragmentCreateNotesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)


        bind.saveNote.setOnClickListener {
            createNote(it)


        }



        bind.greendot.setOnClickListener {
            priority = 1
            bind.greendot.setImageResource(R.drawable.baseline_done_24)
            bind.reddot.setImageResource(0)
            bind.yellowdot.setImageResource(0)
        }

        bind.reddot.setOnClickListener {
            priority = 2
            bind.reddot.setImageResource(R.drawable.baseline_done_24)
            bind.greendot.setImageResource(0)
            bind.yellowdot.setImageResource(0)
        }

        bind.yellowdot.setOnClickListener {
            priority = 3
            bind.yellowdot.setImageResource(R.drawable.baseline_done_24)
            bind.reddot.setImageResource(0)
            bind.greendot.setImageResource(0)
        }




        return bind.root
    }

    private fun createNote(it: View) {
        val title = bind.addTitle.text
        val subtitle = bind.AddSubtitle.text
        val note = bind.AddNote.text

        val d = Date()

        var date = android.text.format.DateFormat.format("MMMM d, YYYY ", d.time)

    }

}