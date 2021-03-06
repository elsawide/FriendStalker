package com.naegling.assassins;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.naegling.assassins.lib.UserFunctions;


public class MainActivity extends ActionBarActivity {
    UserFunctions userFunctions;
    private GoogleMap googleMap;
    double latitude = 53.558;
    double longitude = 9.927;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
            setContentView(R.layout.activity_main);

        	try {
                // Loading map
                initilizeMap();
                // showing current location
                googleMap.setMyLocationEnabled(true); // false to disable
     
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            // user is not logged in show login screen
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
        }
    }

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
	    if (googleMap == null) {
	        googleMap = ((MapFragment) getFragmentManager().findFragmentById(
	                R.id.map)).getMap();
	
	        // check if map is created successfully or not
	        if (googleMap == null) {
	            Toast.makeText(getApplicationContext(),
	                    "Sorry! unable to create maps", Toast.LENGTH_SHORT)
	                    .show();
	        }
	    }
	    
	  //my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
         
        // create marker
        MarkerOptions marker = new MarkerOptions()
        		.position(new LatLng(latitude, longitude))
        		.title("I am in Hamburg.")
        		.icon(BitmapDescriptorFactory.fromResource(R.drawable.ninja)); // adding a marker
        
        // adding marker
        googleMap.addMarker(marker);
        
    }
	
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        userFunctions = new UserFunctions();

        if (id == R.id.action_settings) {

            return true;
        }

        if (id == R.id.action_logout) {
            userFunctions.logoutUser(getApplicationContext());
            Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
            // Closing dashboard screen
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
