package com.mustafamaden.picturenote

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add__note.*
import java.io.ByteArrayOutputStream
import java.lang.Exception


class add_Note : Fragment() {

    var selectedPicture : Uri? = null
    var selectedBitmap : Bitmap? = null

    var byteArray : ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_add__note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener { save(it) }
        imageView.setOnClickListener{ selectedPicture(it) }

        arguments?.let {
            val info = add_NoteArgs.fromBundle(it).info
            if(info == "new"){
                noteName.setText("")
                whoseName.setText("")
                yearText.setText("")
                titleName.setText("")

                saveButton.visibility = View.VISIBLE

                val selectedImageBackground = BitmapFactory.decodeResource(context?.resources, R.drawable.ic_launcher_background)
                imageView.setImageBitmap(selectedImageBackground)
            } else {
                saveButton.visibility = View.INVISIBLE

                val selectedId = add_NoteArgs.fromBundle(it).id
                val database = context?.openOrCreateDatabase("Note", Context.MODE_PRIVATE, null)

                val cursor = database!!.rawQuery("SELECT * FROM note WHERE id = ?", arrayOf(selectedId.toString()))

                val titleIx = cursor.getColumnIndex("title")
                val noteNameIx = cursor.getColumnIndex("noteName")
                val whoseNameIx = cursor.getColumnIndex("whoseName")
                val yearIx = cursor.getColumnIndex("year")
                val imageIx = cursor.getColumnIndex("image")

                while (cursor.moveToNext()) {
                    titleName.setText(cursor.getString(titleIx))
                    noteName.setText(cursor.getString(noteNameIx))
                    whoseName.setText(cursor.getString(whoseNameIx))
                    yearText.setText(cursor.getString(yearIx))

                    val byteArray = cursor.getBlob(imageIx)
                    val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                    imageView.setImageBitmap(bitmap)

                }
                cursor.close()

            }
        }

    }
    fun save(view: View){

        if (noteName != null && whoseName != null && yearText != null && titleName != null){
            var noteName = noteName.text.toString()
            var whoseName = whoseName.text.toString()
            var year = yearText.text.toString()
            var title = titleName.text.toString()

            if(selectedBitmap != null){
                val smallBitmap = makeSmallerBitmap(selectedBitmap!!,300)

                val outputStream = ByteArrayOutputStream()
                smallBitmap.compress(Bitmap.CompressFormat.PNG,70,outputStream)
                byteArray = outputStream.toByteArray()
            }


            try {
                val database = context?.openOrCreateDatabase("Note", Context.MODE_PRIVATE, null)
                database?.execSQL("CREATE TABLE IF NOT EXISTS note (id INTEGER PRIMARY KEY,title VARCHAR, noteName VARCHAR, whoseName VARCHAR, year VARCHAR, image BLOB)")

                val sqlString =
                        "INSERT INTO note  (title, noteName, whoseName, year, image) VALUES (?, ? , ?, ?, ?)"
                val statement = database!!.compileStatement(sqlString)

                statement.bindString(1, title)
                statement.bindString(2, noteName)
                statement.bindString(3, whoseName)
                statement.bindString(4, year)
                statement.bindBlob(5, byteArray)


                statement.execute()

            } catch (e: Exception){
                e.printStackTrace()
            }

        }

        val action = add_NoteDirections.actionAddNoteToShowNote()
        view.findNavController().navigate(action)
    }
    fun selectedPicture(view: View){

        if(context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentToGallery, 2)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intentToGallery, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if( requestCode == 2 && resultCode == Activity.RESULT_OK && data != null){
            selectedPicture = data.data

            try {
                if(selectedPicture != null){
                    if(Build.VERSION.SDK_INT >= 28){

                        val source = ImageDecoder.createSource(requireContext().contentResolver!!, selectedPicture!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        imageView.setImageBitmap(selectedBitmap)

                    } else {
                        selectedBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver!!, selectedPicture)
                        imageView.setImageBitmap(selectedBitmap)
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    fun makeSmallerBitmap(image: Bitmap, maximumSize : Int) : Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio : Double = width.toDouble() / height.toDouble()
        if (bitmapRatio > 1) {
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else {
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }

        return Bitmap.createScaledBitmap(image,width,height,true)

    }
}