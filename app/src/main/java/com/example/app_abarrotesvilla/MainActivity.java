package com.example.app_abarrotesvilla;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Línea de código para que no se vea la parte de arriba y el splash se mire completo.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        //Animaciones
        Animation animacion = new AnimationUtils().loadAnimation(this, R.anim.animacion_splash);

        //Parte lógica conectado con los componentes del diseño.
        ivLogo = findViewById(R.id.ivLogo);
        //Agregamos a nuetro componente logo una animación.
        ivLogo.setAnimation(animacion);

        //Método para hacer que dure una cierta cantidad nuestro splash y así pasar a la siguiente actividad.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, 4000);

    }
}