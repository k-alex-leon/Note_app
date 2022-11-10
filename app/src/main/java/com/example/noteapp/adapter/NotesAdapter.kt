package com.example.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.models.Note
import kotlin.random.Random

class NotesAdapter(private val context : Context, private val listener : NotesClickListener):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val mNotesList = arrayListOf<Note>()
    private val mFullList = arrayListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = mNotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true
        holder.description.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor()))

        holder.notes_layout.setOnClickListener {
            listener.onItemClicked(mNotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listener.onLongItemClicked(mNotesList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    override fun getItemCount(): Int {
        return mNotesList.size
    }

    fun updateList(newList: List<Note>){

        mFullList.clear()
        mFullList.addAll(newList)

        mNotesList.clear()
        mNotesList.addAll(mFullList)
        notifyDataSetChanged()
    }

    fun filterList(search : String){
        mNotesList.clear()
        for (item in mFullList){
            if (item.title?.lowercase()?.contains(search) == true ||
                item.note?.lowercase()?.contains(search) == true){

                mNotesList.add(item)
            }
        }

        notifyDataSetChanged()
    }

    private fun randomColor() : Int{

        val list = arrayListOf<Int>()
        list.add(R.color.blue_light)
        list.add(R.color.green_light)
        list.add(R.color.red)
        list.add(R.color.salmon)
        list.add(R.color.purple)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)

        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val notes_layout = itemView.findViewById<CardView>(R.id.cvNote)
        val title = itemView.findViewById<TextView>(R.id.txtVTitleNote)
        val description = itemView.findViewById<TextView>(R.id.txtVTextNote)
        val date = itemView.findViewById<TextView>(R.id.txtVDateNote)
    }

    interface NotesClickListener{
        fun onItemClicked(note : Note)
        fun onLongItemClicked(note : Note, cardView: CardView)
    }
}