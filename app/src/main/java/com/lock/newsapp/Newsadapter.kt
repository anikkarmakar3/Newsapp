package com.lock.newsapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

open class Newsadapter(private val listofnews:MutableList<Newsmodelview>): BaseAdapter() {
    override fun getCount(): Int =
        listofnews.size


    override fun getItem(position: Int): Any =
        listofnews[position]

    override fun getItemId(position: Int): Long =
        position.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val contxt=parent?.context
        var rowView=convertView
        val inflater=contxt?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        if (rowView==null){
            rowView=inflater.inflate(R.layout.newsitem,parent,false)
        }
        val item=listofnews[position]
        val text_newstitle=rowView?.findViewById<TextView>(R.id.newstitle)
        text_newstitle?.text=item.title

        text_newstitle?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            contxt.startActivity(intent)
        }
        return rowView!!
    }
}