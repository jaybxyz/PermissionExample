package com.example.sonic.permissionexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PreviewActivity extends AppCompatActivity {

    public static String TAG = "PreviewActivity";

    private final int REQUEST_PERMISSION = 1;

    private RecyclerView mRecyclerView;

    private Button btn_accept;

    int CAMERA_PEMISSION;
    int READ_STORAGE_PERMISSION;
    int WRITE_STORAGE_PERMISSION;
    int LOCATION_PERMISSION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        init();
        expandableList();
    }

    public void init()
    {
        CAMERA_PEMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        READ_STORAGE_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        WRITE_STORAGE_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        LOCATION_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        btn_accept = (Button)findViewById(R.id.btn_accept);
        btn_accept.setOnClickListener(mOnClickListener);
    }

    public void expandableList()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<ExpandableListAdapter.Item> data = new ArrayList<>();

//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Camera"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Camera Permission Needs Permission"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "To Take Photos and Videos"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Storage"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Storage Permission Needs Permission"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "READ_EXTERNAL_STORAGE"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "WRITE_EXTERNAL_STORAGE"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "To Save Photos and Videos"));

        ExpandableListAdapter.Item camera = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Camera");
        camera.invisibleChildren = new ArrayList<>();
        camera.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Camera Permission Needs Permission"));
        camera.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Camera"));
        camera.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "To Take Photos and Videos"));


        ExpandableListAdapter.Item storage = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Storage");
        storage.invisibleChildren = new ArrayList<>();
        storage.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Storage Permission Needs Permission"));
        storage.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "READ_EXTERNAL_STORAGE"));
        storage.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "WRITE_EXTERNAL_STORAGE"));
        storage.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "To Save Photos and Videos"));


        ExpandableListAdapter.Item location = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Location");
        location.invisibleChildren = new ArrayList<>();
        location.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Location Permission Needs Permission"));
        location.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "ACCESS_FINE_LOCATION"));
        location.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "To Locate Your Location"));


        data.add(camera);
        data.add(storage);
        data.add(location);

        mRecyclerView.setAdapter(new ExpandableListAdapter(data));
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick( View v ) {
          if (v == btn_accept)
          {
              // Permission Check
              if (Build.VERSION.SDK_INT >= 23)
              {
                  if (CAMERA_PEMISSION != PackageManager.PERMISSION_GRANTED && READ_STORAGE_PERMISSION != PackageManager.PERMISSION_GRANTED &&
                          WRITE_STORAGE_PERMISSION != PackageManager.PERMISSION_GRANTED && LOCATION_PERMISSION != PackageManager.PERMISSION_GRANTED)
                  {
                      // Permissions are not granted yet
                        grantPermission();
                  }
                  else
                  {
                      // Permissions are already granted
                      // You can implement here to have a custom dialog that explains why your app requires permissions
                  }
              }
          }

        }
    };

    protected void grantPermission()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("TAG", "Permissions are granted");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("TAG", "Permissions are denied");
                }
                return;
        }
    }
}
