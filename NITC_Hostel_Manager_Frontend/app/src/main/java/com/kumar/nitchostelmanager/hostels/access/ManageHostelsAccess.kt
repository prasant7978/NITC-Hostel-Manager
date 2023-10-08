package com.kumar.nitchostelmanager.hostels.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.models.Hostel
import com.kumar.nitchostelmanager.services.HostelsService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageHostelsAccess(
    var context: Context,
    var loginToken:String,
    var parentFragment: Fragment
) {

    suspend fun addHostel(newHostel: Hostel):Boolean{
        return suspendCoroutine { continuation ->

            var hostelService = ServiceBuilder.buildService(HostelsService::class.java)
            val requestCall = hostelService.addHostel(loginToken,newHostel)
            requestCall.enqueue(object: Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        if(response.body() == null || response.body() == false){
                            Toast.makeText(context,"Hostel is not added",Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                        }else{
                            Toast.makeText(context,"Hostel Added",Toast.LENGTH_SHORT).show()
                            continuation.resume(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("addHostel","Error : $t")
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun getHostels():Array<Hostel>?{
        return suspendCoroutine { continuation ->

            var hostelService = ServiceBuilder.buildService(HostelsService::class.java)
            val requestCall = hostelService.getHostels(loginToken)
            requestCall.enqueue(object: Callback<Array<Hostel>> {
                override fun onResponse(call: Call<Array<Hostel>>, response: Response<Array<Hostel>>) {
                    if(response.isSuccessful){
                        if(response.body() == null){
                            Toast.makeText(context,"Could not get hostels",Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }else{
//                            Toast.makeText(context,"Hostel",Toast.LENGTH_SHORT).show()
                            continuation.resume(response.body()!!)
                        }
                    }
                }

                override fun onFailure(call: Call<Array<Hostel>>, t: Throwable) {
                    Log.d("getHostels","Error : $t")
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    continuation.resume(null)
                }

            })
        }
        }
    }



}