package edu.susqu.math.kubota.travellogger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.UUID;


public class TravelLogActivity extends ActionBarActivity {
    public static String EXTRA_TRIP_ID = "TRAVEL_LOG_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_log);
        if (savedInstanceState == null) {
            UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_TRIP_ID);
            Fragment fragment = PlaceholderFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        private UUID mId;
        public static PlaceholderFragment newInstance(UUID id){
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.mId = id;
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_travel, container, false);
            TripContainer trips = TripContainer.get(getActivity());
            Trip trip = trips.getTrip(mId);
            TextView textView = (TextView)rootView.findViewById(R.id.textView2);
            textView.setText(trip.toString());
            return rootView;
        }
    }
}
