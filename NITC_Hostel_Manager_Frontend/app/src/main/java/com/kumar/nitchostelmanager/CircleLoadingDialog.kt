package com.kumar.nitchostelmanager

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.fragment.app.Fragment

interface CircleLoadingDialog {

    fun getLoadingDialog(context: Context, parentFragment: Fragment): Dialog {
        val loadingDialog = Dialog(context)
        loadingDialog.setContentView(parentFragment.requireActivity().layoutInflater.inflate(R.layout.loading_circle_dialog,null))
        loadingDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        if(!profileViewModel.gotProfile)  {
        loadingDialog.create()
        loadingDialog.setCancelable(false)
        return loadingDialog
    }
}