package com.febryituery.componenkotlin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.febryituery.componenkotlin.databinding.ActivityInternalStorageBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InternalStorageActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityInternalStorageBinding
    val array = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityInternalStorageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        createFileInInternalFolder()
    }
    private fun createFileInInternalFolder() {
        val directory = File(this.filesDir, packageName)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val fileName = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date()) + ".json"
        val file = File(directory, fileName)
        val myJson: String = fileName;
        try {
            val outputStream = FileOutputStream(file)
            outputStream.write(myJson.toByteArray())
            outputStream.close()
        } catch (e: IOException) {
            Log.e("Exception", "File write failed: $e")
        }
        val filesss = File(directory, "")
        if (filesss.exists()) {
            filesss.list()?.forEach {
                Log.d("Files", it)
                array.add(it)

            }
            val arrayAdapter: ArrayAdapter<*>
            var mListView = findViewById<ListView>(R.id.list_view)
            arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, array)
            mListView.adapter = arrayAdapter
            mListView.setOnItemClickListener { adapterView, view, i, l -> Toast.makeText(this, "PATH: " + getPathFile(array[i]), Toast.LENGTH_SHORT).show() }
        }
    }

    private fun getPathFile(fileName: String): String {
        return this.filesDir.toString() + "/Miscom/" + fileName
    }
}