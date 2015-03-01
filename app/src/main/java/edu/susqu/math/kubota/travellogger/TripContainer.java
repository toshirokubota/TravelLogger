package edu.susqu.math.kubota.travellogger;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by kubota on 2/3/2015.
 */
public class TripContainer {
    private ArrayList<Trip> mTrips = new ArrayList<Trip>();
    private Context mAppContext;
    private static TripContainer sTripContainer;
    private LocationDb mDb;

    private TripContainer(Context appContext) {
        mAppContext = appContext;
        mDb = new LocationDb(appContext);
        try {
            mTrips = mDb.getTrips();
        } catch(ParseException ex) {
            Log.e("TripContainer: failed to get trips.", ex.toString());
            mTrips.clear();
        }
        /*for(int i=0; i<10; ++i){
            Location loc = new Location("NA", "NA", "Selinsgrove", "PA", "USA");
            Trip trip = Trip.createTrip("Trip "+i, loc);
            mTrips.add(trip);
        }*/
    }
    public void addTrip(Trip t) {
        mTrips.add(t);
        mDb.addTrip(t);
    }
    public void updateTrip(Trip trip) {
        for (int i=0; i<mTrips.size(); ++i) {
            if (mTrips.get(i).getId().equals(trip.getId())) {
                mTrips.set(i, trip);
                mDb.updateTrip(trip);
                break;
            }
        }
    }
    public static TripContainer get(Context c) {
        if (sTripContainer == null) {
            sTripContainer = new TripContainer(c.getApplicationContext());
        }
        return sTripContainer;
    }
    public Trip getTrip(UUID id) {
        for (Trip t : mTrips) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public UUID getId(int i){
        if(i >= 0 && i < mTrips.size()){
            return mTrips.get(i).getId();
        }
        else {
            return null;
        }
    }

    public int numTrips() {
        return mTrips.size();
    }

    public ArrayList<Trip> getTrips() {
        return mTrips;
    }
}

