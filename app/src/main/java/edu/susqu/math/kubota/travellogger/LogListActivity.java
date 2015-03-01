package edu.susqu.math.kubota.travellogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;


public class LogListActivity extends ActionBarActivity {
    private TripAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new TripAdapter(TripContainer.get(this).getTrips());
        setContentView(R.layout.activity_log_list);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = null;//manager.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = PlaceholderFragment.createInstance(this);
            manager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
        /*if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, PlaceholderFragment.createInstance(this))
                    .commit();
        }*/
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends ListFragment {
        Activity parent;

        public PlaceholderFragment() {
       }

        public static PlaceholderFragment createInstance(Activity a) {
            PlaceholderFragment p = new PlaceholderFragment();
            p.parent = a;
            p.setListAdapter(((LogListActivity)a).adapter);
            return p;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            getActivity().setTitle("Hello Trips");
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
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.menu_log_list, menu);
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
            } else if(id == R.id.action_new) {
                Location loc = new Location("NA", "NA", "Selinsgrove", "PA", "USA");
                Trip trip = Trip.createTrip("My workday", loc);
                TripContainer container = TripContainer.get(getActivity());
                container.addTrip(trip);
                ((LogListActivity)getActivity()).adapter.notifyDataSetChanged();
            }

            return super.onOptionsItemSelected(item);
        }

    }

    private class TripAdapter extends ArrayAdapter<Trip> {
        public TripAdapter(ArrayList<Trip> crimes) {
            super(LogListActivity.this, android.R.layout.simple_list_item_1, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // if we weren't given a view, inflate one
            if (null == convertView) {
                convertView = LogListActivity.this.getLayoutInflater()
                        .inflate(android.R.layout.simple_list_item_1, null);
            }

            // configure the view for this Crime
            Trip c = getItem(position);
            TextView textView = (TextView)convertView;
            textView.setText(c.toString());

            /*TextView titleTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getName() + " " + c.getDate());
            TextView dateTextView =
                    (TextView)convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox =
                    (CheckBox)convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());*/

            return convertView;
        }
    }
}

