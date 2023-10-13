package com.kumar.nitchostelmanager.bills.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.kumar.nitchostelmanager.models.Bill
import com.kumar.nitchostelmanager.services.ManageBillService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import com.kumar.nitchostelmanager.viewModel.ProfileViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class BillAccess(var context: Context, private var profileViewModel: ProfileViewModel) {
    suspend fun generateBill(bill: Bill): Boolean{
        return suspendCoroutine { continuation ->
            val billService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = billService.generateBill(profileViewModel.loginToken.toString(), bill)

            requestCall.enqueue(object: Callback<Boolean>{
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful)
                        continuation.resume(true)
                    else{
                        Toast.makeText(context, "Failed to generate bill", Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("generateBill","Error : $t")
                    continuation.resume(false)
                }

            })
        }
    }

    suspend fun getAllOwnBills(): ArrayList<Bill>?{
        return suspendCoroutine { continuation ->
            val billService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = billService.getAllOwnBills(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Bill>>{
                override fun onResponse(
                    call: Call<ArrayList<Bill>>,
                    response: Response<ArrayList<Bill>>
                ) {
                    if(response.isSuccessful) {
                        if(response.body() != null)
                            continuation.resume(response.body()!!)
                        else{
                            Toast.makeText(context, "No bills found", Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Bill>>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getOwnBills","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }


    suspend fun getUnpaidBills(): ArrayList<Bill>?{
        return suspendCoroutine { continuation ->
            val billService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = billService.getUnpaidBills(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Bill>>{
                override fun onResponse(
                    call: Call<ArrayList<Bill>>,
                    response: Response<ArrayList<Bill>>
                ) {
                    if(response.isSuccessful) {
                        if(response.body() != null)
                            continuation.resume(response.body()!!)
                        else{
                            Toast.makeText(context, "No bills found", Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Bill>>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getOwnBills","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun getAllBills(): ArrayList<Bill>?{
        return suspendCoroutine { continuation ->
            val billService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = billService.getAllBills(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<ArrayList<Bill>>{
                override fun onResponse(
                    call: Call<ArrayList<Bill>>,
                    response: Response<ArrayList<Bill>>
                ) {
                    if(response.isSuccessful) {
                        if(response.body() != null)
                            continuation.resume(response.body()!!)
                        else{
                            Toast.makeText(context, "No bills found", Toast.LENGTH_SHORT).show()
                            continuation.resume(null)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<ArrayList<Bill>>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getAllBills","Error : $t")
                    continuation.resume(null)
                }

            })
        }
    }

    suspend fun getBillCount(): Int{
        return suspendCoroutine { continuation ->
            val billService = ServiceBuilder.buildService(ManageBillService::class.java)
            val requestCall = billService.getBillsCount(profileViewModel.loginToken.toString())

            requestCall.enqueue(object: Callback<Int>{
                override fun onResponse(
                    call: Call<Int>,
                    response: Response<Int>
                ) {
                    if(response.isSuccessful) {
                        if(response.body() != null)
                            continuation.resume(response.body()!!)
                        else{
                            Toast.makeText(context, "No bills found", Toast.LENGTH_SHORT).show()
                            continuation.resume(0)
                        }
                    }
                    else{
                        Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show()
                        continuation.resume(0)
                    }
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    Toast.makeText(context,"Error : $t", Toast.LENGTH_SHORT).show()
                    Log.d("getAllBills","Error : $t")
                    continuation.resume(0)
                }

            })
        }
    }
}