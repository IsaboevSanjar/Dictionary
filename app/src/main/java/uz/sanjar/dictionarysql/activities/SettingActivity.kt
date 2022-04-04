package uz.sanjar.dictionarysql.activities

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import uz.sanjar.dictionarysql.R

class SettingActivity : AppCompatActivity() {
    private var backPressed: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        windowBack()
        loadViews()
        backPressed!!.setOnClickListener { onBackPressed() }

    }


    private fun loadViews() {
        backPressed = findViewById(R.id.back_pressed)
    }

    private fun windowBack() {
        val window = this.window
        window.statusBarColor = this.resources.getColor(R.color.dictionary_back_blue)
        window.navigationBarColor = this.resources.getColor(R.color.dictionary_back_blue)
    }
}