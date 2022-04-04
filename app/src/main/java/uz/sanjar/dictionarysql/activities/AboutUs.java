package uz.sanjar.dictionarysql.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import uz.sanjar.dictionarysql.R;

public class AboutUs extends AppCompatActivity {
    private LinearLayout byGmail, byInstagram, byTelegram, byYoutube;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        loadViews();
        windowBack();
        loadActions();

    }

    private void loadActions() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        byTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://t.me/sanjar_isaboev");
            }
        });
        byInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://instagram.com/sanjar_isaboev");
            }
        });
        byYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("https://www.youtube.com/channel/UC5QRaLLvGmDYWBx59GEG6-g/featured");
            }
        });
        byGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUrl("mailto:sanjarisaboyev707@gmail.com");
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void loadViews() {
        back = findViewById(R.id.back_btn_info);
        byGmail = findViewById(R.id.by_gmail);
        byTelegram = findViewById(R.id.by_telegram);
        byInstagram = findViewById(R.id.by_instagram);
        byYoutube = findViewById(R.id.by_youtube);
    }

    private void windowBack() {
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.dictionary_back_blue));
        window.setNavigationBarColor(this.getResources().getColor(R.color.dictionary_back_blue));
    }
}