package com.example.firebasetwo.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.firebasetwo.R
import com.example.firebasetwo.databinding.ItemEachBinding
import com.example.firebasetwo.model.Notes
import com.example.firebasetwo.notes.create_notes

class adapter(val context: Context, val notesList: List<Notes>) :
    RecyclerView.Adapter<adapter.myVH>() {


    class myVH(val bind: ItemEachBinding) : ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myVH {
        return myVH(ItemEachBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: myVH, position: Int) {

        val data = notesList[position]

        holder.bind.titlelist.text = data.title
        holder.bind.subtitlelist.text = data.subtitle
        holder.bind.datelist.text = data.date
        when (data.priority) {

            "1" -> {holder.bind.prioritylist.setBackgroundColor(R.color.green)}
            "2" -> {holder.bind.prioritylist.setBackgroundColor(R.color.yellow)}
            "3" -> {holder.bind.prioritylist.setBackgroundColor(R.color.red)}

        }

            holder.bind.root.setOnClickListener{
              //  val directions

            }


        }

    }
}