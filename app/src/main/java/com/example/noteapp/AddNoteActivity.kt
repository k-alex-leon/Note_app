package com.example.noteapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.databinding.ActivityAddNoteBinding
import com.example.noteapp.models.Note
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note : Note
    private lateinit var oldNote : Note
    var isUpdate = false

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.itxtVTitleNote.setText(oldNote.title)
            binding.itxtVNote.setText(oldNote.note)

            isUpdate = true


        }catch (e : Exception){
            e.printStackTrace()
        }

        binding.imgVCheckNote.setOnClickListener {
            val title = binding.itxtVTitleNote.text.toString()
            val note_desc = binding.itxtVNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()){
                val formatter = SimpleDateFormat("d MMM yyyy HH:mm a")

                if (isUpdate){
                    note = Note(
                        oldNote.id, title, note_desc, formatter.format(Date())
                    )
                }else{
                    note = Note(
                        null, title, note_desc, formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            }else{
                Toast.makeText(this@AddNoteActivity, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.imgVgoBack.setOnClickListener {
            onBackPressed()
        }
    }
}