package com.example.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private var currentimageurl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnnext = findViewById<Button>(R.id.nextmeme)
        val btnshare = findViewById<Button>(R.id.sharememe)

        loadmeme()

        btnnext.setOnClickListener {
            nextmeme()
        }

        btnshare.setOnClickListener {
            sharememe()
        }

        
    }
    private fun loadmeme(){
        val memeimage = findViewById<ImageView>(R.id.memeimage)
        val imageprogress = findViewById<ProgressBar>(R.id.imageprogress)
        memeimage.visibility = View.GONE
        imageprogress.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        currentimageurl = "https://meme-api.herokuapp.com/gimme"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, currentimageurl, null,
            { response ->
                currentimageurl = response.getString("url")
                Glide.with(this).load(currentimageurl).into(memeimage)
                imageprogress.visibility = View.GONE
                memeimage.visibility = View.VISIBLE
            },
            { error ->
                Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT)
            })

        // Add the request to the RequestQueue.
        Mysingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun nextmeme() {
        loadmeme()
    }

    fun sharememe() {

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "$currentimageurl" )
        val chooser = Intent.createChooser(intent, "Share this meme with.....")
        startActivity(chooser)

    }
}