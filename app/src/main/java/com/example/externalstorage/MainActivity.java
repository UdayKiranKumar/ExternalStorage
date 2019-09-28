package com.example.externalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    boolean flag=true;
    EditText e1;
    Button b1,b2;
    TextView tv;
    File f1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button2);
        e1=findViewById(R.id.editText);
        tv=findViewById(R.id.textView);
    }
    public void write(View view)
    {
        permissionchecking();
        if(flag)
        {
            String state = Environment.getExternalStorageState();

            if(state.equals(Environment.MEDIA_MOUNTED))
            {
                Toast.makeText(this, "External storage is available", Toast.LENGTH_SHORT).show();
                File root = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                File dir = new File(root.getAbsolutePath()+"/Uday directory");
                Toast.makeText(MainActivity.this,root.getAbsolutePath(), Toast.LENGTH_SHORT).show();

                if(!dir.exists())
                {
                    dir.mkdirs();
                }
                f1 = new File(dir,"kiran.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(f1);
                    String s = e1.getText().toString();
                    fos.write(s.getBytes());
                    Toast.makeText(this, "written into storage ", Toast.LENGTH_SHORT).show();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(this, "External storage is not available", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void permissionchecking()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                flag = true;
                Toast.makeText(this, "no need twice", Toast.LENGTH_SHORT).show();
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
        else
        {
            Toast.makeText(this, "no need permission", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "given (allowed)", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "press allow", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void Read(View view)
    {
        try{
            FileInputStream fis = new FileInputStream(f1);
            int i = 0;
            String s = "";
            while ((i=fis.read()) != -1)
            {
                s=s+(char)i;
            }
            Toast.makeText(this, "check textview", Toast.LENGTH_SHORT).show();
            tv.setText(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
