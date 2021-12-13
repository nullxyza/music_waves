package com.visualmagix.musicwaves;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class player extends AppCompatActivity {
        MediaPlayer mediaPlayer;
        TextView name,textView;
        SeekBar songseekbar;
        Thread updateseekbar;
        ArrayList<String> mySongs;
        public static String sname;
        public static int pos;
        AudioManager audioManager;
       // Boolean media = true;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_player);
            name = (TextView) findViewById(R.id.sname);
            songseekbar = (SeekBar) findViewById(R.id.seekBar);
            textView = findViewById(R.id.textView2);




           /*updateseekbar = new Thread() {
                @Override
                public void run() {
                    int totalduration = mediaPlayer.getDuration();
                    int currentpostion = 0;
                    while(currentpostion < totalduration){
                    try {
                            sleep(500);
                           currentpostion = mediaPlayer.getCurrentPosition();
                           songseekbar.setProgress(currentpostion);
                        }

                    catch (Exception e) {
                        currentpostion = 0;
                        e.printStackTrace();
                    }

                }}
            };*/




            Intent i = getIntent();
            Bundle bundle = i.getExtras();
            mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
            sname = i.getStringExtra("songname");
            pos = bundle.getInt("pos",0);
            name.setText(sname);

        }
        public void musiccheck(){
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            int musicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if ((musicVolume == 0)) {

                Toast.makeText(this, "Music is Muted/Silent  ", Toast.LENGTH_SHORT).show();

            }
        }


        public void playbtn(View view) {
            musiccheck();
            if(mediaPlayer == null ){
                int ResId = getResources().getIdentifier(mySongs.get(pos),"raw",getPackageName());
                mediaPlayer = MediaPlayer.create(player.this,ResId);
                songseekbar.setMax(mediaPlayer.getDuration());
               // updateseekbar.start();
                songseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    //mediaPlayer.seekTo(i);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                       mediaPlayer.seekTo(seekBar.getProgress());
                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        stopPlayer();
                    }
                });
            }
            mediaPlayer.start();
        }

        public void pausebtn(View view) {
            if(mediaPlayer != null ){
                songseekbar.setMax(mediaPlayer.getDuration());
                mediaPlayer.pause();
            }
        }

        public void stopbtn(View view) {

            stopPlayer();

        }
        private void stopPlayer(){
            if(mediaPlayer!=null ){
                songseekbar.setProgress(0);
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer=null;
                Toast.makeText(this,"Mediaplayer Stopped",Toast.LENGTH_SHORT).show();
            }


        }
        @Override
        protected void onStop(){
            super.onStop();
            stopPlayer();

        }

    }
