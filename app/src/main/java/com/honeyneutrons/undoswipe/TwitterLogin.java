package com.honeyneutrons.undoswipe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class TwitterLogin extends AppCompatActivity {
    private static final String TWITTER_KEY = "uc8jbK2QQDuofWoU8gzMy8fIq";
    private static final String TWITTER_SECRET = "FtnkgRDIXB7MdlKOHVWeNfBoq1XSbav46eLSzI4yCLZKOv0v1z";
    private String username;
    public static final String SHAREDPREFFILE = "temp";

    //Tags to send the username and image url to next activity using intent
    public static final String KEY_USERNAME = "username";


    //Twitter Login Button
    TwitterLoginButton twitterLoginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_twitter_login);
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitterLogin);
         if(check())
         {
             Intent intent = new Intent(TwitterLogin.this, MainActivity.class);

             //Adding the values to intent
             intent.putExtra(KEY_USERNAME,username);


             //Starting intent
             startActivity(intent);
             finish();

         }

        //Adding callback to the button
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    //The login function accepting the result object
    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //Getting the username from session
         username = session.getUserName();

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString(KEY_USERNAME, username);
                        editor.commit();

                        //Creating an Intent
                        Intent intent = new Intent(TwitterLogin.this, MainActivity.class);

                        //Adding the values to intent
                        intent.putExtra(KEY_USERNAME,username);


                        //Starting intent
                        startActivity(intent);
                        finish();
                    }
                });


    }
    private boolean check() {
        SharedPreferences prefs = getSharedPreferences(SHAREDPREFFILE, Context.MODE_PRIVATE);
        String userId = prefs.getString(KEY_USERNAME, null);
        if (userId == null)
            return false;
        else {
            username=userId;
            return true;
        }
    }
}
