package com.osproject.ankush.osproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class SelectActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        final int width;
        final int height;
        Bitmap bitImage = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        TextView tx = findViewById(R.id.textViewS);
        Button lap = (Button) findViewById(R.id.LapS);
        String f_path = getIntent().getStringExtra("imgData");
        Uri imgData = Uri.fromFile(new File(f_path));
        tx.setText("Bitmap Uploading Successful");
        try {
            bitImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgData);
        } catch (IOException e) {
            tx.setText("Uploading Failed");
        }
        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        width = bitImage.getWidth();
        height = bitImage.getHeight();
        String test = "";
        long t_start = System.currentTimeMillis();
        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                int colour = bitImage.getPixel(x, y);

                int red = Color.red(colour);
                int blue = Color.blue(colour);
                int green = Color.green(colour);
                int alpha = Color.alpha(colour);
                Select_Globals.R[x][y] = red;
                Select_Globals.G[x][y] = green;
                Select_Globals.B[x][y] = blue;
                Select_Globals.A[x][y] = alpha;

            }
        }
        long t_end = System.currentTimeMillis();
        String time_s = String.valueOf(t_end - t_start);
        tx.append(" Extracting RGBA Data from Bitmap Successful : Time consumed in extraction: " + time_s + " Milliseconds");


        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i,j,k;
                Bitmap lap = Bitmap.createBitmap(
                        width-1, height-1, Bitmap.Config.ARGB_8888);
                for(i=1;i<width-1;i++)
                {
                    for(j=1;j<height-1;j++)
                    {
                        Select_Globals.nR[i][j] = 9*Select_Globals.R[i][j]-(Select_Globals.R[i-1][j-1]+
                                Select_Globals.R[i-1][j]+Select_Globals.R[i-1][j+1]+Select_Globals.R[i][j-1]+
                                Select_Globals.R[i][j+1]+Select_Globals.R[i+1][j-1]+Select_Globals.R[i+1][j]+Select_Globals.R[i+1][j+1]);
                        if(Select_Globals.nR[i][j]<0)
                            Select_Globals.nR[i][j]=0;
                        if(Select_Globals.nR[i][j]>255)
                            Select_Globals.nR[i][j]=255;

                        Select_Globals.nG[i][j] = 9*Select_Globals.G[i][j]-(Select_Globals.G[i-1][j-1]+
                                Select_Globals.G[i-1][j]+Select_Globals.G[i-1][j+1]+Select_Globals.G[i][j-1]+
                                Select_Globals.G[i][j+1]+Select_Globals.G[i+1][j-1]+Select_Globals.G[i+1][j]+Select_Globals.G[i+1][j+1]);
                        if(Select_Globals.nG[i][j]<0)
                            Select_Globals.nG[i][j]=0;
                        if(Select_Globals.nG[i][j]>255)
                            Select_Globals.nG[i][j]=255;

                        Select_Globals.nB[i][j] = 9*Select_Globals.B[i][j]-(Select_Globals.B[i-1][j-1]+
                                Select_Globals.B[i-1][j]+Select_Globals.B[i-1][j+1]+Select_Globals.B[i][j-1]+
                                Select_Globals.B[i][j+1]+Select_Globals.B[i+1][j-1]+Select_Globals.B[i+1][j]+Select_Globals.B[i+1][j+1]);
                        if(Select_Globals.nB[i][j]<0)
                            Select_Globals.nB[i][j]=0;
                        if(Select_Globals.nB[i][j]>255)
                            Select_Globals.nB[i][j]=255;

                        Select_Globals.nA[i][j] = 9*Select_Globals.A[i][j]-(Select_Globals.A[i-1][j-1]+
                                Select_Globals.A[i-1][j]+Select_Globals.A[i-1][j+1]+Select_Globals.A[i][j-1]+
                                Select_Globals.A[i][j+1]+Select_Globals.A[i+1][j-1]+Select_Globals.A[i+1][j]+Select_Globals.A[i+1][j+1]);
                        if(Select_Globals.nA[i][j]<0)
                            Select_Globals.nA[i][j]=0;
                        if(Select_Globals.nA[i][j]>255)
                            Select_Globals.nA[i][j]=255;


                        lap.setPixel(i,j,Color.argb( Select_Globals.nA[i][j],
                                Select_Globals.nR[i][j],
                                Select_Globals.nG[i][j],
                                Select_Globals.nB[i][j]));

                    }
                }

                setContentView(R.layout.activity_lap_select);
                ImageView imageView = findViewById(R.id.imageView2);
                imageView.setImageBitmap(lap);

            }
        });
    }
}
class Select_Globals {

        static int[][] R = new int[5000][5000];
        static int[][] G = new int[5000][5000];
        static int[][] B = new int[5000][5000];
        static int[][] A = new int[5000][5000];
        static int[][] nR = new int[5000][5000];
        static int[][] nG = new int[5000][5000];
        static int[][] nB = new int[5000][5000];
        static int[][] nA = new int[5000][5000];


    }

