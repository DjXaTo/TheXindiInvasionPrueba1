package com.hipotenocha.ilm.thexindiinvasion;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;


public class MenuPrincipal extends ActionBarActivity {

    int AltoPantalla;
    int AnchoPantalla;
    Button botonJuego;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        botonJuego = (Button) findViewById(R.id.btnNuevoJuego);


        CalculaTamañoPantalla();
        AnimacionBoton();
        IniciaMusicaIntro();
        AnimacionInicial();
        findViewById(R.id.btnNuevoJuego).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                MenuPrincipal.this.startActivity(new Intent(MenuPrincipal.this, ActividadJuego.class));
            }
        });
    }

    public void IniciaMusicaIntro() {

        mediaPlayer = MediaPlayer.create(this, R.raw.intro);
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mediaPlayer.start();
    }

    public void AnimacionBoton() {
        if (Build.VERSION.SDK_INT > 10) {
            AnimatorSet animadorBoton = new AnimatorSet();

            ObjectAnimator trasladar = ObjectAnimator.ofFloat(botonJuego, "translationX", -800, 0);
            System.out.println(botonJuego.getWidth() + ":" + AnchoPantalla);
            trasladar.setDuration(5000);
            ObjectAnimator fade = ObjectAnimator.ofFloat(botonJuego, "alpha", 0f, 0.5f);
            fade.setDuration(8000);
            animadorBoton.play(trasladar).with(fade);
            animadorBoton.start();
        }
    }

    public void CalculaTamañoPantalla() {
        if (Build.VERSION.SDK_INT > 13) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            AnchoPantalla = size.x;
            AltoPantalla = size.y;
        } else {
            Display display = getWindowManager().getDefaultDisplay();
            AnchoPantalla = display.getWidth();  // deprecated
            AltoPantalla = display.getHeight();  // deprecated
        }

        Log.i(Juego.class.getSimpleName(), "alto:" + AltoPantalla + "," + "ancho:" + AnchoPantalla);
    }

    public void AnimacionInicial() {
        try {

            /*ANIMACIÓN METEORITO*/
            ImageView meteorito = (ImageView) findViewById(R.id.imgMeteorito);
            meteorito.setVisibility(ImageView.VISIBLE);
            Animation meteoritoAnim = AnimationUtils.loadAnimation(this, R.anim.meteorito);
            meteorito.startAnimation(meteoritoAnim);


            Thread.sleep(2000); // 2 segundos después

            /*ANIMACIÓN PLANETA */
            ImageView planeta = (ImageView) findViewById(R.id.imgPlaneta);
            planeta.setVisibility(ImageView.VISIBLE);
            //(xFrom,xTo, yFrom,yTo)
            TranslateAnimation animation = new TranslateAnimation(-300, AnchoPantalla + 200, 80, 200);
            animation.setDuration(10000);  // duración de la animación
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.RESTART);

            planeta.startAnimation(animation);  // comenzar animación

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);

        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
