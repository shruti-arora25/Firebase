package com.example.firebasetwo.notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.firebasetwo.R
import com.example.firebasetwo.ViewModel.notesViewM
import com.example.firebasetwo.adapters.adapter
import com.example.firebasetwo.databinding.FragmentAllNotesBinding
import com.example.firebasetwo.model.Notes

class all_notes : Fragment(), adapter.navigateListener {
    private lateinit var bind: FragmentAllNotesBinding
    private val viewM: notesViewM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bind = FragmentAllNotesBinding.inflate(layoutInflater, container, false)


//
//        arguments?.getBoolean("Create_NEW_user")
//        val data = DataClass()
//
//            Gson().toJson(dataClass())
////        Get Data
//        Gson().fromJson(arguments?.getBoolean("Create_NEW_user") , Data::class.java)
//
//

//                arguments?.getBoolean("Create_NEW_user")
//            )
//        }
//
//        val bundle = Bundle()
//        bundle.putString("","")


        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        bind.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_all_notes_to_create_notes)
        }
    }


    private fun init() {

        viewM.getNotes().observe(
            viewLifecycleOwner
        ) { notesList: List<Notes> ->

            bind.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            bind.recyclerView.adapter = adapter(this,requireContext(), notesList)

        }

    }

    override fun onItemClicked(data: Notes) {

        val directions=all_notesDirections.actionAllNotesToNotesEdit(data)
        findNavController().navigate(directions)




    }


}
