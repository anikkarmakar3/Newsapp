package com.lock.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.lock.newsapp.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private var page=1
    private var list= mutableListOf<Newsmodelview>()
    lateinit var bining:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bining= ActivityMainBinding.inflate(layoutInflater)
        val view=bining.root
        setContentView(view)
        searchbuttonclick()
        bining.loadmore.setOnClickListener {
            page+= 1
            sendvolleyrequest()
        }
    }

    private fun sendvolleyrequest() {
        val queue= Volley.newRequestQueue(this)
        val url=get_url()
        val stringRequest= StringRequest(
            Request.Method.GET,url,
            {
                    response->
                extradefinationfromjson(response)
            },
            {
                    error->
                Toast.makeText(this,"some thing wrong", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(stringRequest)
    }

    private fun searchbuttonclick() {
        bining.searchButton.setOnClickListener {
            if(!bining.inputtext.text.isEmpty()){
                sendvolleyrequest()
                list= mutableListOf()
            }
            else{
                bining.inputtext.setError("please enter someing which you want to search")
            }
        }

    }

    private fun get_url() :String{
        val word=bining.inputtext.text

        val apikey="2c7ac818-0b9c-4c24-af04-3d0ec701bf74"

        val pagesize=10
        val url="https://content.guardianapis.com/$word?page=$pagesize&api-key=$apikey"
        return url
    }
    private fun extradefinationfromjson(response:String){
        val JSONobject= JSONObject(response)
        val firstindex=JSONobject.getJSONObject("response")
        val getshortdefination=firstindex.getJSONArray("results")
        for (i in 0..9){
            val item=getshortdefination.getJSONObject(i)
            val webtitle=item.getString("webTitle")
            val weburl=item.getString("webUrl")
            val data=Newsmodelview(webtitle,weburl)
            list.add(data)
        }
        val adapter=Newsadapter(list)
        bining.listview.adapter=adapter
    }
}