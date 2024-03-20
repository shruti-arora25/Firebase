package com.example.firebasetwo.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.firebasetwo.databinding.FragmentCreateNotesBinding


class create_notes : Fragment() {

    private lateinit var bind:FragmentCreateNotesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind= FragmentCreateNotesBinding.inflate(layoutInflater,container,false)
        return bind.root
    }

}