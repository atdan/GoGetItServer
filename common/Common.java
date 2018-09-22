package com.example.root.gogetitserver.common;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.example.root.gogetitserver.model.Request;
import com.example.root.gogetitserver.model.User;
import com.example.root.gogetitserver.remote.APIService;
import com.example.root.gogetitserver.remote.FCMRetrofitClient;
import com.example.root.gogetitserver.remote.IGeoCoordinates;
import com.example.root.gogetitserver.remote.RetrofitClient;

public class Common {

    public static User current_user;
    public static Request current_request;


    public static final String UPDATE = "Update";
    public static final String DELETE = "Delete";

    public static final String baseUrl = "https://maps.googleapis.com";

    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static final int PICK_IMAGE_REQUEST = 71;

    public static String convertCodeToStatus (String code){
        if (code.equals("0")){
            return "Placed";
        }else if (code.equals("1")){
            return "On my way";
        }else {
            return "Shipped";
        }
    }

    public static APIService getFCMClient(){

        return FCMRetrofitClient.getClient(fcmURL).create(APIService.class);
    }
    public static IGeoCoordinates getGeoCodeServices(){
        return RetrofitClient.getClient(baseUrl).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap,int newWidth, int newHeight){


        Bitmap scaledBitmap = Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);


        float scaleX = newWidth/(float)bitmap.getWidth();

        float scaleY = newHeight/(float)bitmap.getHeight();

        float pivotX = 0, pivotY = 0;

        Matrix scaleMatrix = new Matrix();

        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }
}
