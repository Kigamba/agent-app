package com.stavros.demo.android.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by Kigamba (nek.eam@gmail.com) on 15-June-2020
 */
public class MyAesthicDialog {

    /**
     * Shows Emotion Dialog.
     *
     *
     * @param activity
     * @param title
     * @param message
     * @param dialogType
     */
    public static void showEmotion(Activity activity, String title, String message, String dialogType, DialogInterface.OnDismissListener onDismissListener){
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(activity);
        View layoutView = activity.getLayoutInflater().inflate(R.layout.dialog_emotion, null);
        ImageView icon = layoutView.findViewById(R.id.img_icon);
        RelativeLayout layoutDialog = layoutView.findViewById(R.id.dialog_layout);
        TextView textTitle = layoutView.findViewById(R.id.dialog_title);
        TextView textMessage = layoutView.findViewById(R.id.dialog_message);
        TextView textHour = layoutView.findViewById(R.id.dialog_hour);

        if(dialogType.equals(AestheticDialog.SUCCESS)){
            icon.setImageResource(R.drawable.smiley_success);
            layoutDialog.setBackgroundResource(R.drawable.background_emotion_success);
        }else {
            icon.setImageResource(R.drawable.smiley_error);
            layoutDialog.setBackgroundResource(R.drawable.background_emotion_error);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String hour = sdf.format(Calendar.getInstance().getTime());
        textMessage.setText(message);
        textTitle.setText(title);
        textHour.setText(hour);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setGravity(Gravity.CENTER);

        if (onDismissListener != null) {
            alertDialog.setOnDismissListener(onDismissListener);
        }
        alertDialog.show();
        int height = activity.getResources().getDimensionPixelSize(R.dimen.popup_height_emotion);
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,height);

    }
}
