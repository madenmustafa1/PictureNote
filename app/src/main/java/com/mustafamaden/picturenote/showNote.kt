package com.mustafamaden.picturenote

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_add__note.*
import kotlinx.android.synthetic.main.fragment_show_note.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class showNote : Fragment() {

    val whoseNameList = ArrayList<String>()
    val titleList = ArrayList<String>()
    val imageList = ArrayList<Bitmap>()

    val idList = ArrayList<Int>()

    private lateinit var noteAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteAdapter = RecyclerAdapter(whoseNameList, titleList, imageList, idList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = noteAdapter

        getFromSQL()
    }

    private fun getFromSQL(){
        try {
            if(activity != null){

                val database = requireActivity().openOrCreateDatabase("Note",Context.MODE_PRIVATE, null)

                val cursor = database.rawQuery("SELECT * FROM note", null)
                val noteNameIx = cursor.getColumnIndex("noteName")

                val whoseNameIx = cursor.getColumnIndex("whoseName")
                val titleIx = cursor.getColumnIndex("title")
                val imageIx = cursor.getColumnIndex("image")
                val idIx = cursor.getColumnIndex("id")

                whoseNameList.clear()
                idList.clear()

                while (cursor.moveToNext()){
                    whoseNameList.add(cursor.getString(whoseNameIx))
                    titleList.add(cursor.getString(titleIx))
                    //imageList.add(cursor.getString(imageIx))
                    val byteArray = cursor.getBlob(imageIx)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageList.add(bitmap)



                    idList.add(cursor.getInt(idIx))
                }
                noteAdapter.notifyDataSetChanged()
                cursor.close()

            }


        } catch (e: Exception){
            e.printStackTrace()
        }
    }



}