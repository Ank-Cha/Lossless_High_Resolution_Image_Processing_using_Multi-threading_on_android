package com.osproject.ankush.osproject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import static java.util.logging.Logger.global;

public class SelectMultiActivity extends AppCompatActivity {
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_multi);

        Bitmap bitImage = null;
        final TextView txm = findViewById(R.id.textViewM);
        String f_path = getIntent().getStringExtra("imgData");
        Uri imgData = Uri.fromFile(new File(f_path));
        txm.setText("Bitmap Uploading Successful");

        try {
            bitImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgData);

        } catch (IOException e) {
            txm.setText("Uploading Failed");
        }
        final int w=bitImage.getWidth();
        final int h=bitImage.getHeight();
        
        final Bitmap finalBitImage = bitImage;

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                for(int x=1;x<w+1;x++)
                {
                    for(int y=1;y<h/2;y++)
                    {
                        int colour = finalBitImage.getPixel(x-1, y-1);

                        int red = Color.red(colour);
                        int blue = Color.blue(colour);
                        int green = Color.green(colour);
                        int alpha = Color.alpha(colour);
                        SelectMulti_Global.R[x][y] = red;
                        SelectMulti_Global.G[x][y] = green;
                        SelectMulti_Global.B[x][y] = blue;
                        SelectMulti_Global.A[x][y] = alpha;
                    }
                }
          SelectMulti_Global.flag1 = 1;
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int x=1;x<w+1;x++)
                {
                    for(int y=w/2;y<h+1;y++)
                    {
                        int colour = finalBitImage.getPixel(x-1, y-1);

                        int red = Color.red(colour);
                        int blue = Color.blue(colour);
                        int green = Color.green(colour);
                        int alpha = Color.alpha(colour);
                        SelectMulti_Global.R[x][y] = red;
                        SelectMulti_Global.G[x][y] = green;
                        SelectMulti_Global.B[x][y] = blue;
                        SelectMulti_Global.A[x][y] = alpha;
                    }
                }

            SelectMulti_Global.flag2 = 1;
            }
        });
        String x="";

        long t_start = System.currentTimeMillis();
        t1.start();
        t2.start();
        while (SelectMulti_Global.flag1 !=1 && SelectMulti_Global.flag2 !=1) {}
            long t_end = System.currentTimeMillis();


        for (int i = 0;i<100;i++)
        {
            for(int j=0;j<100;j++)
            {
                x=x+String.valueOf(SelectMulti_Global.R[i][j]);
            }
        }

        txm.append("Extracting RGBA Data from Bitmap Successful : Time consumed in extraction: "+String.valueOf(t_end - t_start)+" Milliseconds"+x);

    


    }
}
 class SelectMulti_Global
{

        static int[][] R = new int[5000][5000];
        static int[][] G = new int[5000][5000];
        static int[][] B = new int[5000][5000];
        static int[][] A = new int[5000][5000];
        static int flag1 = 0;
        static int flag2 = 0;

}
