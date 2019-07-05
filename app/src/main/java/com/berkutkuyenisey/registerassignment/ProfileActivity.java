package com.berkutkuyenisey.registerassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Bitmap> userProfilePictureFromParse;
    ArrayList<String> userNameFromParse;
    ArrayList<String> userUsernameFromParse;
    ArrayList<String> userEmailFromParse;
    ArrayList<String> userBirthdateFromParse;
    ArrayList<String> userGenderFromParse;
    ArrayList<String> userTcknFromParse;
    DisplayClass displayClass;
    ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        listView = findViewById(R.id.listView);
        userProfilePictureFromParse = new ArrayList<>();
        userNameFromParse = new ArrayList<>();
        userUsernameFromParse = new ArrayList<>();
        userEmailFromParse = new ArrayList<>();
        userBirthdateFromParse = new ArrayList<>();
        userGenderFromParse = new ArrayList<>();
        userTcknFromParse = new ArrayList<>();

        displayClass = new DisplayClass(userProfilePictureFromParse, userNameFromParse, userUsernameFromParse, userEmailFromParse, userBirthdateFromParse, userGenderFromParse, userTcknFromParse, this);

        listView.setAdapter(displayClass);
        currentUser = ParseUser.getCurrentUser();

        downloadUserData();
    }

    protected void downloadUserData() {

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", currentUser.getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> users, ParseException e) {

                if (e != null) {
                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
                else {
                    if (users.size() > 0) {
                        for (final ParseUser user : users) {
                                ParseFile parseFile = (ParseFile) user.get("profilepicture");
                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if (e == null && data != null) {
                                            Bitmap bitmapProfilePicture = BitmapFactory.decodeByteArray(data, 0, data.length);
                                            userProfilePictureFromParse.add(bitmapProfilePicture);
                                            userNameFromParse.add(user.getString("name"));
                                            userUsernameFromParse.add(user.getString("username"));
                                            userEmailFromParse.add(user.getString("email"));
                                            userBirthdateFromParse.add(user.getString("birthdate"));
                                            userGenderFromParse.add((user.getString("gender")));
                                            userTcknFromParse.add(user.getString("tckn"));

                                            displayClass.notifyDataSetChanged();
                                        }
                                    }
                                });
                        }
                    }
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout) {
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e != null) {
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                    else {
                        Intent signInActivityIntent = new Intent(getApplicationContext(), SignInActivity.class);
                        startActivity(signInActivityIntent);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
}
