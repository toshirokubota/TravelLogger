package edu.susqu.math.kubota.travellogger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.UUID;


public class LogListActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_list, menu);
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
    public static class PlaceholderFragment extends ListFragment {

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            TripContainer tripContainer = TripContainer.get(getActivity());
            ArrayList<Trip> logs = new ArrayList<Trip>();
            for(int i=0; i<tripContainer.numTrips(); ++i){
                UUID id = tripContainer.getId(i);
                logs.add(tripContainer.getTrip(id));
            }
            ArrayAdapter<Trip> adapter = new ArrayAdapter<Trip>(getActivity(),
                    android.R.layout.simple_list_item_1, logs);
            setListAdapter(adapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_log_list, container, false);
            /*ListView listView = (ListView)rootView.findViewById(android.R.id.list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("kubota", "Item #"+id+" clicked.");
                    Intent i = new Intent(getActivity(), TravelLogActivity.class);
                    //i.putExtra(RunMapActivity.EXTRA_RUN_ID, mRun.getId());
                    startActivity(i);
                }
            });*/

            return rootView;
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            //super.onListItemClick(l, v, position, id);
            Log.d("kubota", "Item #" + id + " clicked.");
            Intent i = new Intent(getActivity(), TravelPagerActivity.class);
            TripContainer trips = TripContainer.get(getActivity());

            UUID uuid = trips.getId((int)id);
            i.putExtra(TravelLogActivity.EXTRA_TRIP_ID, uuid);
            startActivity(i);
        }
    }
}
