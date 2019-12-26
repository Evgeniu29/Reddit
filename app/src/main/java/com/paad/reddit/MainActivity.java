package com.paad.reddit;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import com.paad.reddit.publicationList.PublicationFragment;

public class MainActivity extends AppCompatActivity {
    private static final String PREF_LOGIN = "LOGIN_PREF";
    DrawerLayout drawer;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, new PublicationFragment())
                .commit();



    }

}
