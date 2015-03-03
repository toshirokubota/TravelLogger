package edu.susqu.math.kubota.travellogger;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.UUID;


public class TravelLogActivity extends ActionBarActivity {
    public static String EXTRA_TRIP_ID = "TRAVEL_LOG_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_entry);
        //if (savedInstanceState == null) {
            UUID id = (UUID)getIntent().getSerializableExtra(EXTRA_TRIP_ID);
            Fragment fragment = PlaceholderFragment.newInstance(id);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        //}
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
        //private Trip mTrip;
        //private Context mContext;
        public static PlaceholderFragment newInstance(UUID id){
            PlaceholderFragment fragment = new PlaceholderFragment();
            //TripContainer trips = TripContainer.get(context);
            //fragment.mTrip = trips.getTrip(id);
            //fragment.mContext = context;
            Bundle b = new Bundle();
            b.putSerializable("id", id);

            fragment.setArguments(b);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final TripContainer trips = TripContainer.get(getActivity());
            UUID id = (UUID) getArguments().getSerializable("id");
            final Trip trip = trips.getTrip(id);
            View rootView = inflater.inflate(R.layout.fragment_travel_entry, container, false);
            final EditText nickname = (EditText)rootView.findViewById(R.id.editNickname);
            nickname.setText(trip.getName());
            final TextView date = (TextView)rootView.findViewById(R.id.labelDate);
            date.setText(trip.getDateString());
            Location loc = trip.getLocation();
            final EditText city = (EditText)rootView.findViewById(R.id.editCity);
            city.setText(loc.getCity());
            final EditText state = (EditText)rootView.findViewById(R.id.editState);
            state.setText(loc.getState());
            final EditText country = (EditText)rootView.findViewById(R.id.editCountry);
            country.setText(loc.getCountry());
            final EditText desc = (EditText)rootView.findViewById(R.id.editDescription);
            String s = trip.getDescription();
            if(s.isEmpty()) {
                desc.setHint("provide description of the trip.");
            } else {
                desc.setText(s);
            }

            date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(trip.getDate());
                    DatePickerDialog dlg = new DatePickerDialog(PlaceholderFragment.this.getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    calendar.set(Calendar.YEAR, year);
                                    calendar.set(Calendar.MONTH, monthOfYear);
                                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                    trip.setDate(calendar.getTime());
                                    date.setText(trip.getDateString());
                                }
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                    dlg.show();
                }
            });

            Button save = (Button)rootView.findViewById(R.id.saveButton);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trip.setName(nickname.getText().toString());
                    trip.getLocation().setCity(city.getText().toString());
                    trip.getLocation().setState(state.getText().toString());
                    trip.getLocation().setCountry(country.getText().toString());
                    trip.setDescription(desc.getText().toString());
                    trips.updateTrip(trip);
                }
            });

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
        }
    }
}
