package com.example.spodemy.Application

    import android.app.Application
    import android.app.NotificationChannel
    import android.app.NotificationManager
    import android.content.Context
    import android.os.Build
    import com.example.spodemy.Repository.TodoRepository
    import com.example.spodemy.TodoDatabase.TodoDatabase
    import com.google.firebase.firestore.FirebaseFirestore
    import com.google.firebase.firestore.Source

class HMApplicaton: Application() {
        override fun onCreate() {
            super.onCreate()
            FirebaseFirestore.getInstance().enableNetwork()
            FirebaseFirestore.getInstance().collection("user").get(Source.CACHE)
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O)
            {
                val channel= NotificationChannel( "Stepcount","Stepcount", NotificationManager.IMPORTANCE_LOW)
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    private val database by lazy { TodoDatabase.getDatabase(this@HMApplicaton) }

    val repository by lazy { TodoRepository(database.todoDao()) }
    }
