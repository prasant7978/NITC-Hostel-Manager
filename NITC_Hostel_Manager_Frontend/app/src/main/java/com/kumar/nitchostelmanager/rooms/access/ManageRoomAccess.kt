package com.kumar.nitchostelmanager.rooms.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.models.Room
import com.kumar.nitchostelmanager.services.ManageRoomsService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageRoomAccess(
    var context: Context,
    var loginToken: String,
    var parentFragment: Fragment
) {
    suspend fun getAllRooms(hostelId: String): ArrayList<Room>?{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.getAllRooms(loginToken, hostelId)

            requestCall.enqueue(object: Callback<ArrayList<Room>>{
                override fun onResponse(call: Call<ArrayList<Room>>, response: Response<ArrayList<Room>>) {
                    if(response.isSuccessful){
                        if(response.body() != null)
                            continuation.resume(response.body())
                        else{
                            Toast.makeText(context,"No rooms found", Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(context, "Could not get rooms", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Room>>, t: Throwable) {
                    Log.d("getAllRooms","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun getAvailableRooms(hostelId: String): Array<Room>?{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.getAvailableRooms(loginToken, hostelId)

            requestCall.enqueue(object: Callback<Array<Room>>{
                override fun onResponse(call: Call<Array<Room>>, response: Response<Array<Room>>) {
                    if(response.isSuccessful){
                        if(response.body() != null)
                            continuation.resume(response.body())
                        else{
                            Toast.makeText(context,"No available rooms are found", Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(context, "Could not get rooms", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Array<Room>>, t: Throwable) {
                    Log.d("getAvailableRooms","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun allocateRoom(roomId: String, hostelId: String): Boolean{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.allocateRoom(loginToken, roomId, hostelId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        if(response.body() != null) {
                            Toast.makeText(context,"Room has been allocated", Toast.LENGTH_SHORT).show()
                            continuation.resume(true)
                        }
                        else{
                            Toast.makeText(context,"Can't allocate room", Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("allocateRoom","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun deallocateRoom(roomId: String, hostelId: String): Boolean{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.deallocateRoom(loginToken, roomId, hostelId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        if(response.body() != null) {
                            Toast.makeText(context,"Room has been deallocated", Toast.LENGTH_SHORT).show()
                            continuation.resume(true)
                        }
                        else{
                            Toast.makeText(context,"Can't deallocate room", Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("deallocateRoom","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun deleteRoom(roomId: String, hostelId: String): Boolean{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.deleteRoom(loginToken, roomId, hostelId)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        Toast.makeText(context,"Room has been deleted", Toast.LENGTH_SHORT).show()
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Room couldn't be deleted", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("deleteRoom","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun addRoom(room: Room): Boolean{
        return suspendCoroutine { continuation ->
            val roomServices = ServiceBuilder.buildService(ManageRoomsService::class.java)
            val requestCall = roomServices.addRoom(loginToken, room)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    if(response.isSuccessful){
                        Toast.makeText(context,"Room has been created", Toast.LENGTH_SHORT).show()
                        continuation.resume(true)
                    }
                    else{
                        Toast.makeText(context, "Room couldn't be created", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Log.d("addRoom","Error : $t")
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    continuation.resume(false)
                }

            })
        }
    }
}