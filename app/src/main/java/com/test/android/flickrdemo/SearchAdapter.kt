package com.test.android.flickrdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class SearchAdapter(var mContext: Context, var mList: ArrayList<SearchDTO.Photos.Photo>) :
    RecyclerView.Adapter<SearchAdapter.MyHolder>() {
    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgItemView: ImageView? = null
        var crdItemView: CardView? = null

        init {
            imgItemView = view.findViewById(R.id.imgItemView)
            crdItemView = view.findViewById(R.id.crdItemView)
            val displayMetrics = mContext.resources.displayMetrics
            val width = displayMetrics.widthPixels
            crdItemView?.layoutParams?.width = (width / 2)-20
            crdItemView?.layoutParams?.height =( width / 2)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(LayoutInflater.from(mContext).inflate(R.layout.itemview, parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            val imgPath =
                "https://live.staticflickr.com/" + mList[position].server + "/" + mList[position].id + "_" + mList[position].secret + ".jpg"
            Glide.with(mContext).load(imgPath).into(holder.imgItemView!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}