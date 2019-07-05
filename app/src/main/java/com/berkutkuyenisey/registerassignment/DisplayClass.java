package com.berkutkuyenisey.registerassignment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayClass extends ArrayAdapter<String> {

    private final ArrayList<Bitmap> userProfilePicture;
    private final ArrayList<String> userName;
    private final ArrayList<String> userUsername;
    private final ArrayList<String> userEmail;
    private final ArrayList<String> userBirthdate;
    private final ArrayList<String> userGender;
    private final ArrayList<String> userTckn;
    private final Activity context;

    public DisplayClass(ArrayList<Bitmap> userProfilePicture, ArrayList<String> userName, ArrayList<String> userUsername,
                        ArrayList<String> userEmail, ArrayList<String> userBirthdate, ArrayList<String> userGender, ArrayList<String> userTckn, Activity context) {
        super(context, R.layout.custom_view, userName);
        this.userProfilePicture = userProfilePicture;
        this.userName = userName;
        this.userUsername = userUsername;
        this.userEmail = userEmail;
        this.userBirthdate = userBirthdate;
        this.userGender = userGender;
        this.userTckn = userTckn;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_view,null,true);
        ImageView userProfilePictureView = customView.findViewById(R.id.iVProfilePictureDisplayed);
        TextView userNameText = customView.findViewById(R.id.tVNameDisplayed);
        TextView userUsernameText = customView.findViewById(R.id.tVUsernameDisplayed);
        TextView userEmailText = customView.findViewById(R.id.tVEmailDisplayed);
        TextView userBirthdateText = customView.findViewById(R.id.tVBirthDateDisplayed);
        TextView userGenderText = customView.findViewById(R.id.tVGenderDisplayed);
        TextView userTcknText = customView.findViewById(R.id.tVTCKNDisplayed);

        userProfilePictureView.setImageBitmap(userProfilePicture.get(position));
        userNameText.setText(userName.get(position));
        userUsernameText.setText(userUsername.get(position));
        userEmailText.setText(userEmail.get(position));
        userBirthdateText.setText(userBirthdate.get(position));
        userGenderText.setText(userGender.get(position));
        userTcknText.setText(userTckn.get(position));

        return customView;
    }
}
