package com.berkutkuyenisey.registerassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    EditText eTName, eTUsername, eTEMail, eTBirthdate, eTGender, eTTCKN, eTPassword, eTConfirmPassword;
    ImageView iVProfilePicture;
    Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        eTName = findViewById(R.id.eTName);
        eTUsername = findViewById(R.id.eTUsername);
        eTEMail = findViewById(R.id.eTEMail);
        eTBirthdate = findViewById(R.id.eTBirthDate);
        eTGender = findViewById(R.id.eTGender);
        eTTCKN = findViewById(R.id.eTTCKN);
        eTPassword = findViewById(R.id.eTPassword);
        eTConfirmPassword = findViewById(R.id.eTConfirmPassword);
        iVProfilePicture = findViewById(R.id.iVProfilePicture);

    }

    protected void switchToSignInActivity(View view) {
        Intent signInActivityIntent = new Intent(getApplicationContext(), SignInActivity.class);
        startActivity(signInActivityIntent);
    }

    protected void signUp(View view) {
        if(eTPassword.getText().toString().equals(eTConfirmPassword.getText().toString())) {
            final ParseUser user = new ParseUser();
            user.put("name", eTName.getText().toString());
            user.setUsername(eTUsername.getText().toString());
            user.setPassword(eTPassword.getText().toString());
            user.setEmail(eTEMail.getText().toString());
            user.put("birthdate", eTBirthdate.getText().toString());
            user.put("gender", eTGender.getText().toString());
            user.put("tckn", eTTCKN.getText().toString());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            final ParseFile profilePictureFile = new ParseFile("profilepicture", bytes);
            profilePictureFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null) {
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        user.put("profilepicture",profilePictureFile);
                        user.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e != null) {
                                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "User has been signed up successfully, please sign in!", Toast.LENGTH_LONG).show();
                                    Intent signInActivityIntent = new Intent(getApplicationContext(), SignInActivity.class);
                                    startActivity(signInActivityIntent);
                                }
                            }
                        });
                    }

                }
            });


        }
        else {
            Toast.makeText(getApplicationContext(), "Password has not been confirmed accurately, please try again!", Toast.LENGTH_LONG).show();
        }
    }

    protected void selectImageFromGallery(View view) {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 2); // Remember request code 2
        }
        else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, 1); // Remember request code 1
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 2) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1); // Remember request code 1
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try{
                selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                iVProfilePicture.setImageBitmap(selectedImage);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
