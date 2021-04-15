package com.mustafamaden.nproject

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var  access : Boolean = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //systemUI()

        val sharedPreferences = this.getSharedPreferences("com.mustafamaden.nproject", MODE_PRIVATE)

        access = sharedPreferences.getBoolean("access", true)


        if(access){
            sharedPreferences.edit().putBoolean("access", false).apply()
            access = sharedPreferences.getBoolean("access", false)

            val constraints = Constraints.Builder ()
                .setRequiresCharging(false)
                .build ()

            val work = PeriodicWorkRequestBuilder<WorkerClass>(8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build ()

            //val workManager = WorkManager.getInstance(this)
            //workManager.enqueue(PeriodicWorkRequest.Builder(WorkerClass::class.java, 15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES).build())


            WorkManager.getInstance(applicationContext).enqueue(work)
        }
    }

    private fun systemUI(){
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
    }



/*
    private val CHANNEL_ID = "NProject"
    private val notificationId = 101
      fun notification(){
        createNotificationChannel()
        println("sa123")
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name = "Notification Title"
            val descriptionText = "Description Text"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                descriptionText
            }
            val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager



            notificationManager.createNotificationChannel(channel)
        }

    }

    //Oreo altı
    private fun sendNotification(){
        //Resim
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent, 0)

        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.bg)
        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.bg)


        val builder = NotificationCompat.Builder(applicationContext,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tık tık... Bildirim geldi!")
                //.setContentText("Example Text")
                .setLargeIcon(bitmapLargeIcon)
                .setStyle(NotificationCompat.BigTextStyle().bigText("$randomText"))
                //.setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }

    fun send (view : View){
        randomText()

        sendNotification()
    }


    lateinit var randomText : String
    val arrayList = ArrayList<String>()

    fun randomText(){
        arrayList.add("Naber??")
        arrayList.add("Mesaj atmayı düşünür müsün ?")
        arrayList.add("şş napıyorsun:)")
        arrayList.add("Verilerini çalma işlemini başlatıyorum...\n" +
                "| %100 | İşlem tamamlandı.\n" +
                "(Verilerinin paylaşılmaması için bu uygulamanın sahibine mesaj atman gerekiyor.)")
        arrayList.add("Telefonu imha etme işlemi başlatılıyor.\n" +
                "3\n" +
                "2\n" +
                "1\n" +
                "İşlem tamamlanamadı. Detayları için kalbime bakabilirsiniz.")


        val randomInt = Random.nextInt(0,5)
        randomText = arrayList[randomInt]
    }

 */
}