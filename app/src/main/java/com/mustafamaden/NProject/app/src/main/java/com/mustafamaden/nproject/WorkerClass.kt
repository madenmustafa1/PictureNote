package com.mustafamaden.nproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.*
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlin.random.Random

class WorkerClass(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    lateinit var randomText : String
    private val arrayList = ArrayList<String>()

    override fun doWork(): Result {
        randomText()
        createNotification(title, randomText)

        return Result.success()
    }

    private fun createNotification(title: String, description: String){

        var notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                    NotificationChannel("101", "NProject", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val bitmapLargeIcon = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.nphoto)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "101")
                .setContentTitle(title)
                .setContentText(description)
                .setSmallIcon(R.drawable.notification_message)
                .setColor(Color.GREEN)
                .setLargeIcon(bitmapLargeIcon)

        notificationManager.notify(1, notificationBuilder.build())
    }
    var title : String = "Tık tık... Bildirim geldi!"
    fun randomText(){
        arrayList.add("Naber??")
        arrayList.add("Mesaj atmayı düşünür müsün ?")
        arrayList.add("şş napıyorsun:)")
        arrayList.add("Verilerini çalma işlemini başlatıyorum.")
        arrayList.add("Telefonu imha etme işlemi başlatılıyor...")
        arrayList.add("Oo telefon açılmış ama mesaj atılmıyor:(")
        arrayList.add("2x2 bugün 4'se yarın kaçtır ?")
        arrayList.add("Kamerana erişiyorum... Oo bu ne güzellik:)")
        arrayList.add("Merhabalar, isteğiniz nedir ?")
        arrayList.add("Le plus beau du monde est devant le miroir")
        arrayList.add("Şah Mat :)")
        arrayList.add("Satranç oynayalım mı ?")
        arrayList.add("Eee daha daha nasılsın ?")
        arrayList.add("Patron sizle görüşmek istiyor.")
        arrayList.add("Bu saatte bana gün gibi aydın:) günaydınnn:)")
        arrayList.add("Görmedim böylesini.")
        arrayList.add("Oooo gözlerim kamaştı parlaklığı kıs")
        arrayList.add("Ben sizi mutlu etmek için programlandım.")
        arrayList.add("Kural 1: Ağlamak yok!")
        arrayList.add("Sistem mesajı: Ahizeye yaklaş bi ")
        arrayList.add("O zaman satranç")
        arrayList.add("Bir daha PUBG'ye girersen telefonunu bozarım!")
        arrayList.add("Seni dinliyorum haberin olsun.")
        arrayList.add("Sistem: Bu program teh#$½# Geri geldimmm:)")
        arrayList.add("#$½#$#$½$#½$#$½#$ = ?")
        arrayList.add("Kameraya bak ki göz göze gelelim")
        arrayList.add("Eeee daha daha nasılsın ?")
        arrayList.add("Sıkıldım ben, kendimi imha etmeye gidiyorum.")
        arrayList.add("Kazım sokak bekle geliyorum")
        arrayList.add("Doyuyo?")
        arrayList.add("Hiç hal hatır soranım yok :(")
        arrayList.add("Programcımın ünlü sözü nedir?")
        arrayList.add("Aynen öyle katılıyorum.")
        arrayList.add("10 üzerinden değerlendirmeni programcıma yap.")
        arrayList.add("'Gerçekten mi?'")
        arrayList.add("Eee bugün ne boşu yapıyorsun bakalım")
        arrayList.add("Bilgi: Programcım çok yakışıklı birisidir:)")
        arrayList.add("Boş yapmayalım rica edeceğim hafızam doldu")
        arrayList.add("Karnım acıktı biraz şarj alabilir miyim?")
        arrayList.add("Hayatımda en önemli dediğim şey ...")
        arrayList.add("Olsa da oluuur olmasa da dediğim şey... ")
        arrayList.add("WhatsApp'ı bana ayarlar mısın?")
        arrayList.add("Instagram'ı bana ayarlar mısın?")
        arrayList.add("Twitter'ı bana ayarlar mısın?")
        arrayList.add("Ben mi yoksa Twitter mı? Cevap programcıma")
        arrayList.add("Ben mi yoksa Instagram mı? Cevap programcıma")
        arrayList.add("Ben mi yoksa Facebook mu? Cevap programcıma")
        arrayList.add("Gelecek istasyon Kazım Sk.")
        arrayList.add("Beni silersen... Silmiş olursun")
        arrayList.add("Hanim hanim! Bunlar benim yavrularim.")
        arrayList.add("Bakıyorum da... bırakıyorum bakmayı")

        val randomInt = Random.nextInt(0,arrayList.size)
        println(arrayList.size + 1)
        randomText = arrayList[randomInt]
    }


}
