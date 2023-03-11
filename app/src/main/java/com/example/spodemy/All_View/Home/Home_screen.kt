package com.example.spodemy.All_View.Home

import android.Manifest
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.spodemy.All_View.fragment.Home_fragment
import com.example.spodemy.All_View.fragment.Plans_fragment
import com.example.spodemy.All_View.fragment.profile_fragment
import com.example.spodemy.R
import com.example.spodemy.Service.Jobservice
import com.example.spodemy.Service.MyService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_screen.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import java.time.LocalDate

class Home_screen : AppCompatActivity() {
    private val REQUEST_PERMISSION = 123
    private val permission= arrayOf(Manifest.permission.ACCESS_FINE_LOCATION ,Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.BODY_SENSORS,android.Manifest.permission.BODY_SENSORS_BACKGROUND,Manifest.permission.ACTIVITY_RECOGNITION)
    private var userDitails: DocumentReference =  Firebase.firestore.collection("user").document(
        FirebaseAuth.getInstance().currentUser!!.uid.toString())

    @RequiresApi(VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        checkpermission()
        val Home_fragment=Home_fragment()
        val plansFragment=Plans_fragment()
        val ProfileFragment=profile_fragment()
        val Weight= AddWeight()
        window.decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        val curr_date=LocalDate.now().toString()
        userDitails.collection("Weight track").get().addOnSuccessListener {
                snapshot->
            if (snapshot.isEmpty)
            {
                buttonnav.visibility=View.GONE
                supportFragmentManager.beginTransaction().apply {
                    add(R.id.frg,Weight)
                    commit()
                }
            }else{
                makeCurrentFrag(Home_fragment)
                KeyboardVisibilityEvent.setEventListener(
                    this
                ) { isOpen ->
                    if (isOpen) {
                        buttonnav.visibility = View.INVISIBLE
                    } else {
                        buttonnav.visibility = View.VISIBLE
                    }
                }
                buttonnav.visibility=View.VISIBLE
                buttonnav.setOnNavigationItemSelectedListener {
                    when(it.itemId){
                        R.id.home_t -> makeCurrentFrag(Home_fragment)
                        R.id.plan -> makeCurrentFrag(plansFragment)
                        R.id.profile -> makeCurrentFrag(ProfileFragment)
                    }
                    true
                }
            }
        }


    }
    override fun onStart() {
        super.onStart()
        val jobsh= applicationContext.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val jobinfo= JobInfo.Builder(1, ComponentName(this, Jobservice::class.java)).setMinimumLatency(10000).build()
        jobsh.schedule(jobinfo)
        val intent = Intent(this, MyService::class.java).apply {
            action=MyService.ACTION_START
        }
        startService(intent)
    }

    private fun checkpermission() {
        val requiresPermission=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        }
        val ungrantP=permission.filter {
            ContextCompat.checkSelfPermission(this,it)!=PackageManager.PERMISSION_GRANTED
        }
        if(ungrantP.isNotEmpty())
        {
            Toast.makeText(this, "$ungrantP", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(this,ungrantP.toTypedArray(),REQUEST_PERMISSION)
            requiresPermission.launch(ungrantP.toTypedArray())
        }
    }

    private fun makeCurrentFrag(Fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frg,Fragment)
            commit()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {

            }
        }
    }
}