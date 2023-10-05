package com.kumar.nitchostelmanager.warden.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import com.kumar.nitchostelmanager.models.Notice
import com.kumar.nitchostelmanager.services.ManageNoticeService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NoticeAccess(private var profileViewModel: ProfileViewModel, var context: Context) {
    suspend fun getNotices(): Array<Notice>?{
        return suspendCoroutine { continuation ->
            val noticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            val requestCall = noticeService.getNotices(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Array<Notice>?>{
                override fun onResponse(
                    call: Call<Array<Notice>?>,
                    response: Response<Array<Notice>?>
                ) {
                    if(response.isSuccessful)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Array<Notice>?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("getNotices","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun addNotice(notice: Notice): Boolean{
        return suspendCoroutine { continuation ->
            val noticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            val requestCall = noticeService.addNotice(profileViewModel.loginToken.toString(), notice)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful)
                        continuation.resume(true)
                    else{
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("addNotice","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }
}