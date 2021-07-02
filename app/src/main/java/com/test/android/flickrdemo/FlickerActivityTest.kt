package com.test.android.flickrdemo

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlickerActivityTest : AppCompatActivity() {

    private var mList: ArrayList<SearchDTO.Photos.Photo>? = null
    private var recyclerHome: RecyclerView? = null
    private var mAdapter: SearchAdapter? = null
    private var edtSearch: EditText? = null
    private var imgSearch: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flicker_test)
        mInit()
        val str = "Hello"
        callApi(str)
        mListener()
    }

    private fun mListener() {
        imgSearch?.setOnClickListener {
            if (edtSearch?.text.toString().isNotEmpty())
                callApi(edtSearch?.text.toString().trim())
        }
    }

    private fun mInit() {
        mList = ArrayList<SearchDTO.Photos.Photo>()
        recyclerHome = findViewById(R.id.recyclerHome)
        edtSearch = findViewById(R.id.edtSearch)
        imgSearch = findViewById(R.id.imgSearch)
        mAdapter = SearchAdapter(this, mList!!)
        recyclerHome?.layoutManager = GridLayoutManager(this, 2)
        recyclerHome?.adapter = mAdapter
    }

    private fun callApi(searchText: String) {
        val request = ServiceBuilder.buildService(ApiInterface::class.java)
        val call = request.searchText(getString(R.string.api_key), searchText)
        Log.e("TAG", call.toString())
        try {
            call.enqueue(object : Callback<SearchDTO> {
                override fun onResponse(call: Call<SearchDTO>, response: Response<SearchDTO>) {
                    if (response.isSuccessful) {
                        mList?.clear()
                        mList?.addAll(response.body().photos.photo)
                        mAdapter?.notifyDataSetChanged()
                        Log.e("responseData", response.toString())
                    } else {
                        Toast.makeText(
                            this@FlickerActivityTest,
                            response.errorBody().toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<SearchDTO>, t: Throwable) {
                    Toast.makeText(this@FlickerActivityTest, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
