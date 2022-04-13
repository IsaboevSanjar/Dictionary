package uz.sanjar.dictionarysql.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import uz.sanjar.dictionarysql.R

class SplashScreen : AppCompatActivity() {
    private var timer: CountDownTimer? = null
    private var EnUz: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val window: Window = this.window
        // Kotlindagi depressed bolgani uchun shu method ishlatiladi ekan
        window.statusBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
        window.navigationBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val animationBook = findViewById<LottieAnimationView>(R.id.book_animation)
        val animation = AnimationUtils.loadAnimation(this@SplashScreen, R.anim.book_animation)
        animationBook.startAnimation(animation)
        timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                Animatoo.animateZoom(this@SplashScreen)
                finish()
            }
        }
        timer!!.start()


        EnUz = findViewById(R.id.splash_en_uz)
        EnUz!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            Animatoo.animateZoom(this@SplashScreen)
            timer!!.cancel()
            finish()
        })

    }

}