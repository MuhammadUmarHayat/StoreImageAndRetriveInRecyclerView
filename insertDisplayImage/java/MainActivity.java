package com.example.restaurant_management_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;
DBMain dbMain;
EditText name;
Button save,dispaly;
ImageView photo;
int id=0;

public static final int CAMERA_REQUEST=100;
    public static final int STORAGE_REQUEST=101;
    String [] cameraPermission;
    String [] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbMain=new DBMain(this);
        findViews();
        insertData();
        imagePick();
        displayProducts();
    }

    private void displayProducts()
    {
        dispaly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent page = new Intent(MainActivity.this, DisplayProducts.class);
                startActivity(page);

            }
        });
    }

    private void imagePick()
    {
        photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {


            }
        });
    }




    private void insertData()
    {
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ContentValues cv = new ContentValues();
                    cv.put("photo", ImageViewToByte(photo));
                    cv.put("name", name.getText().toString());
                    db = dbMain.getWritableDatabase();
                    long inserted = db.insert("products", null, cv);
                    if (inserted > 0) {
                        Toast.makeText(MainActivity.this, "Record is saved", Toast.LENGTH_SHORT).show();
                      //  photo.setImageResource(R.mipmap.ic_launcher);
                     //   name.setText("");
                    }
                }
                catch (Exception exp)
                {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    name.setText(exp.getMessage().toString());
                }
            }
        });
    }

    private byte[] ImageViewToByte(ImageView photo)
    {
        Bitmap bitmap=((BitmapDrawable)photo.getDrawable()).getBitmap();
        ByteArrayOutputStream stream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        byte[] bytes=stream.toByteArray();
        return bytes;
    }


    public void findViews()
    {

        name=findViewById(R.id.etPName);
        save=findViewById(R.id.btn1);
        dispaly=findViewById(R.id.btn2);
        photo=findViewById(R.id.photo1);
    }

}