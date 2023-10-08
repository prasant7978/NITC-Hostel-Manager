package com.kumar.nitchostelmanager.notice.access

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
    suspend fun getNoticesCount():Int{
        return suspendCoroutine { continuation ->
            var manageNoticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            var requestCall = manageNoticeService.getNoticesCount(profileViewModel.loginToken.toString())
            requestCall.enqueue(object:Callback<Int>{
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if(response.isSuccessful && response.body() != null){
                        continuation.resume(response.body()!!)
                    }else{
                        Toast.makeText(context,"Error occurred in getting notices count",Toast.LENGTH_SHORT).show()
                        continuation.resume(0)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Log.d("noticesCount","Error: $t")
                    Toast.makeText(context,"Error occurred in getting notices count: $t",Toast.LENGTH_SHORT).show()
                    continuation.resume(0)
                }

            })
        }
    }

    suspend fun getAllNotices(): ArrayList<Notice>?{
        return suspendCoroutine { continuation ->
            val noticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            val requestCall = noticeService.getAllNotices(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Notice>?>{
                override fun onResponse(
                    call: Call<ArrayList<Notice>?>,
                    response: Response<ArrayList<Notice>?>
                ) {
                    if(response.isSuccessful)
                        continuation.resume(response.body())
                    else{
                        Toast.makeText(context,"Some error occurred", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Notice>?>, t: Throwable) {
                    Toast.makeText(context,"Error : $t",Toast.LENGTH_SHORT).show()
                    Log.d("getNotices","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun issueNotice(notice: Notice): Boolean{
        return suspendCoroutine { continuation ->
            val noticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            val requestCall = noticeService.issueNotice(profileViewModel.loginToken.toString(), notice)

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

    suspend fun deleteNotice(noticeId: String): Boolean{
        return suspendCoroutine { continuation ->
            val noticeService = ServiceBuilder.buildService(ManageNoticeService::class.java)
            val requestCall = noticeService.deleteNotice(profileViewModel.loginToken.toString(), noticeId)

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
                    Log.d("deleteNotice","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }
}