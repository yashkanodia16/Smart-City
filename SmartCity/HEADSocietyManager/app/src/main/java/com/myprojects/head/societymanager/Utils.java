package com.myprojects.head.societymanager;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.storage.StorageReference;
import com.myprojects.head.societymanager.storage.FirebaseImageLoader;
//import com.myprojects.marco.firechat.storage.StorageLocalDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by marco on 13/07/16.
 */

public class Utils {

    public static String getCurrentTimestamp() {
        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormatGmt.format(new Date());
    }

    public static String getTimestamp(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
            Date date = sdf.parse(timestamp);
            long currentDate = date.getTime();

            long millis = TimeZone.getDefault().getOffset(currentDate);
            long hour = (millis / (1000 * 60 * 60)) % 24;
            long minutes = (millis / (1000 * 60)) % 60;

            String[] timestampPart = timestamp.split("/");
            long h = Long.parseLong(timestampPart[3]);
            long m = Long.parseLong(timestampPart[4]);
            h += hour;
            h %= 24;
            m += minutes;
            m %= 60;

            String output = h + ":" + m;
            if (h < 10) {
                if (m < 10) {
                    output = "0" + h + ":0" + m;
                } else {
                    output = "0" + h + ":" + m;
                }
            } else if (m < 10) {
                output = h + ":0" + m;
            }
            return output;

        } catch (ParseException e) {

        }
        return null;
    }

    public static String getDate(String timestamp) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/HH/mm");
            Date date = sdf.parse(timestamp);
            long currentDate = date.getTime();
            currentDate += TimeZone.getDefault().getOffset(currentDate);

            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            return sdfDate.format(currentDate);
        } catch (ParseException e) {

        }
        return null;
    }

    public static void loadImageElseBlack(final String image, final CircleImageView imageView, final Context context) {

        try {
            if (image != null && image.length() > 0) {
                    StorageReference ref = Dependencies.INSTANCE.getStorageService().getProfileImageReference(image);
                    Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(ref)
                            .into(imageView);
            } else {
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_account_circle_black)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        } catch (IllegalArgumentException e) {

        }
    }

    public static void loadImageElseWhite(final String image, final CircleImageView imageView, final Context context) {

        try {
            if (image != null && image.length() > 0) {
                StorageReference ref = Dependencies.INSTANCE.getStorageService().getProfileImageReference(image);
                Glide.with(context)
                        .using(new FirebaseImageLoader())
                        .load(ref)
                        .into(imageView);
            } else {
                Glide.with(context)
                        .load("")
                        .placeholder(R.drawable.ic_account_circle_white)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .into(imageView);
            }
        } catch (IllegalArgumentException e) {

        }

    }

}
