package com.mustafamaden.picturenote

import android.graphics.Bitmap
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_add__note.view.*
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerAdapter
(val whoseNameList: ArrayList<String>,
 val titleNameList: ArrayList<String>,
 val imageList: ArrayList<Bitmap>,
 val idList: ArrayList<Int>)
    : RecyclerView.Adapter<RecyclerAdapter.NoteHolder>() {

        class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_row, parent,false)
        return NoteHolder(view)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        holder.itemView.whoseNameText.text = whoseNameList[position]
        holder.itemView.titleNameText.text = titleNameList[position]
        holder.itemView.image.setImageBitmap(imageList[position])
        holder.itemView.setOnClickListener {
            val action = showNoteDirections.actionShowNoteToAddNote("old",idList[position])
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return whoseNameList.size
    }

}