package com.robert.customer_manager.ui.registration

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.robert.customer_manager.R
import com.robert.customer_manager.databinding.ActivityRegisterStaffBinding
import com.robert.customer_manager.session.Session
import com.robert.customer_manager.ui.Congratulation
import com.robert.customer_manager.ui.login.LoginActivity


class RegisterStaff : AppCompatActivity(),Session{

    private lateinit var binding:ActivityRegisterStaffBinding
    private val imageCode=100
    private lateinit var photoUri:Uri



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.activity_register_staff)

       val  registerViewModel= ViewModelProvider(this)
           .get(RegisterViewModel::class.java)

        registerViewModel.session=this

        ArrayAdapter.createFromResource(this,R.array.departments,
            android.R.layout.simple_list_item_1).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.myDepartment.adapter=it

        }

        ArrayAdapter.createFromResource(this,R.array.job,android.R.layout
            .simple_spinner_dropdown_item).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.job.adapter=it
        }


        binding.profileImage.setOnClickListener {
            imageSelecting()
        }

        binding.register.setOnClickListener {

            when {
                binding.name.text.isNullOrEmpty() -> {
                    binding.name.error = "error please enter the fullName to register"
                }
                binding.selectedPhotoUri.drawable==null -> {
                    Toast.makeText(this,"please insert the profile image",Toast.LENGTH_LONG).show()
                }
                binding.email.text.isNullOrEmpty() -> {
                    binding.email.error = "error please enter email to register"

                }
                binding.endYear.text.isNullOrEmpty() -> {
                    binding.endYear.error = "error please enter the end of contract year"

                }
                else -> {
                    onStarted()
                    registerViewModel.register(
                        binding.email.text.toString(),
                        binding.name.text.toString(),
                        binding.job.selectedItem.toString(),
                        binding.myDepartment.selectedItem.toString(),
                        photoUri,
                        binding.endYear.text.toString()
                    )
                }
            }
        }


        binding.goToSign.setOnClickListener {
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun imageSelecting(){
        val intent=Intent(Intent.ACTION_GET_CONTENT)
        intent.type="image/*"
        startActivityForResult(intent,imageCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==imageCode||resultCode== Activity.RESULT_OK){
            if(data!=null){
                  photoUri=data.data!!
              }
            val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,photoUri)
            binding.selectedPhotoUri.setImageBitmap(bitmap)
            binding.profileImage.alpha=0f
            }
        }

    override fun onStarted() {
        binding.progressBar2.visibility= View.VISIBLE
    }

    override fun onSuccess() {

        binding.progressBar2.visibility= View.GONE
        val intent= Intent(this@RegisterStaff,
        Congratulation::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onFail() {
        binding.progressBar2.visibility= View.GONE
        Toast.makeText(this,"fail to register new Customer",Toast.LENGTH_SHORT).show()
    }

    override fun onLogout() {
    }
    override fun onEmpty() {
    }

}
