package com.kumar.nitchostelmanager.admin.access

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kumar.nitchostelmanager.ProfileViewModel
import com.kumar.nitchostelmanager.models.Student
import com.kumar.nitchostelmanager.models.Warden
import com.kumar.nitchostelmanager.services.ManageStudentsService
import com.kumar.nitchostelmanager.services.ManageWardensService
import com.kumar.nitchostelmanager.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ManageStudentAccess(
    var context:Context,
    var parentFragment:Fragment,
    var profileViewModel: ProfileViewModel
) {

    suspend fun getStudentsCount():HashMap<String,Int>?{
        return suspendCoroutine { continuation ->
            var manageStudentsService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentsService.getStudentsCount(profileViewModel.loginToken.toString())
            requestCall.enqueue(object: Callback<HashMap<String, Int>> {
                override fun onResponse(
                    call: Call<HashMap<String, Int>>,
                    response: Response<HashMap<String, Int>>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<HashMap<String, Int>>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }
    suspend fun getStudents():Array<Student>?{
        return suspendCoroutine { continuation ->
            var manageStudentService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentService.getAllStudents(profileViewModel.loginToken.toString())
            requestCall.enqueue(object: Callback<Array<Student>?> {
                override fun onResponse(
                    call: Call<Array<Student>?>,
                    response: Response<Array<Student>?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Array<Student>?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }

    suspend fun addStudent(newStudent: Student):Student?{
        return suspendCoroutine { continuation ->
            var manageStudentService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentService.addStudent(profileViewModel.loginToken.toString(),newStudent)
            requestCall.enqueue(object: Callback<Student?> {
                override fun onResponse(
                    call: Call<Student?>,
                    response: Response<Student?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Student?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }

    suspend fun updateStudent(studentRoll: String,newStudent: Student):Student?{
        return suspendCoroutine { continuation ->
            var manageStudentService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentService.updateStudent(profileViewModel.loginToken.toString(),studentRoll,newStudent)
            requestCall.enqueue(object: Callback<Student?> {
                override fun onResponse(
                    call: Call<Student?>,
                    response: Response<Student?>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(response.body())
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(null)
                    }
                }

                override fun onFailure(call: Call<Student?>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(null)
                }
            })
        }
    }



    suspend fun deleteStudent(studentRoll:String):Boolean{
        return suspendCoroutine { continuation ->
            var manageStudentService = ServiceBuilder.buildService(ManageStudentsService::class.java)
            var requestCall = manageStudentService.deleteStudent(profileViewModel.loginToken.toString(),studentRoll)
            requestCall.enqueue(object: Callback<Boolean> {
                override fun onResponse(
                    call: Call<Boolean>,
                    response: Response<Boolean>
                ) {
                    if(response.isSuccessful){
                        continuation.resume(true)
                    }else{
                        Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                    }
                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
                    Toast.makeText(context,"Error: $t",Toast.LENGTH_SHORT).show()
                    Log.d("getStudentsCount","Error : $t")
                    continuation.resume(false)
                }
            })
        }
    }


}