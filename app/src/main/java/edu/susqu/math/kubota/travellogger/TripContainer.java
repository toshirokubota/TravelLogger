package edu.susqu.math.kubota.travellogger;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by kubota on 2/3/2015.
 */
public class TripContainer {
    private ArrayList<UUID> mIds;
    private HashMap<UUID,Trip> mMap;
    private Context mAppContext;
    private static TripContainer sTripContainer;

    private TripContainer(Context appContext) {
        mAppContext = appContext;
        mIds = new ArrayList<UUID>();
        mMap = new HashMap<UUID,Trip>();
        for(int i=0; i<100; ++i){
            Trip trip = new Trip("Toshiro");
            mIds.add(trip.getId());
            mMap.put(trip.getId(), trip);
        }
    }
    public static TripContainer get(Context c) {
        if (sTripContainer == null) {
            sTripContainer = new TripContainer(c.getApplicationContext());
        }
        return sTripContainer;
    }
    public Trip getTrip(UUID id) {
        return mMap.get(id);
    }

    public UUID getId(int i){
        if(i >= 0 && i < mIds.size()){
            return mIds.get(i);
        }
        else {
            return null;
        }
    }

    public int numTrips() {
        return mIds.size();
    }
}

