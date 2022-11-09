package com.asr.codeforcesapi

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class MainActivity : AppCompatActivity() {
    var userInfo: ArrayList<Int> = ArrayList()

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.search_btn)
        btn.setOnClickListener {
            getMyData()
        }
        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
    private fun getMyData() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val find = findViewById<EditText>(R.id.UserHandle)
        val str = find.text.toString()
        find.text.clear()
        val url = "https://codeforces.com/api/user.rating?handle=$str"

        // Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response->
                val arr = response.getJSONArray("result")
                for(i in 0 until arr.length()){
                    val user = arr.getJSONObject(i)
                    val city = user.getInt("newRating")
                    userInfo.add(city)
                }
                val intent = Intent(this@MainActivity,UserProfile::class.java)
                intent.putExtra("key",userInfo)
                startActivity(intent)
            },
            {
                Toast.makeText(this@MainActivity,"ERROR",Toast.LENGTH_LONG).show()
            })

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)
    }

}