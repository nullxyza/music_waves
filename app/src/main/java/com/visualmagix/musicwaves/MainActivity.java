package com.visualmagix.musicwaves;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView ListViewSongs;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListViewSongs = (ListView) findViewById(R.id.songlist);
        arrayList = new ArrayList<String>();
        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {

            arrayList.add(fields[i].getName());
        }
        arrayAdapter = new ArrayAdapter(this, R.layout.row, arrayList);
        ListViewSongs.setAdapter(arrayAdapter);

        ListViewSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String songName = ListViewSongs.getItemAtPosition(i).toString();

                startActivity(new Intent(getApplicationContext(), player.class)
                        .putExtra("pos", i).putExtra("songname", songName).putExtra("songs", arrayList));
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(), "Click twice to close Activity", Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }

}