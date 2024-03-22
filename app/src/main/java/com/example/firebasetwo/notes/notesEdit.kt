package com.example.firebasetwo.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.FragmentAllNotesBinding
import com.example.firebasetwo.databinding.FragmentNotesEditBinding
import com.example.firebasetwo.model.Notes

class notesEdit : Fragment() {

    private lateinit var binding: FragmentNotesEditBinding
    //val args:notesEditArgs by navArgs()

    private lateinit var notes:Notes


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNotesEditBinding.inflate(layoutInflater, container, false)


//        notes=args.data
//
//
//        binding.addTitle2.setText(notes.title)
//        binding.AddSubtitle2.setText(args.data.subtitle)
//        binding.AddNote2.setText(args.data.notes)


        return binding.root


    }

}