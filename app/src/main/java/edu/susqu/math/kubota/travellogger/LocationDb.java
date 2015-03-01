package edu.susqu.math.kubota.travellogger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kubota on 2/12/2015.
 */
public class LocationDb {
    private static final String TAG = "TripsDb";
    // database metadata
    private static final String DB_NAME = "trips.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "trips";

    // column names
    private static final String ID = "id";
    private static final String NICKNAME = "nickname";
    private static final String UUID = "uuid";
    private static final String DESCRIPTION = "description";
    private static final String LONGITUDE = "longitude";
    private static final String LATITUDE = "latitude";
    private static final String DATE = "date";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";


    // SQL statements
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " ("+ID+" INTEGER PRIMARY KEY, "+ NICKNAME+" TEXT, "+ UUID +" TEXT, "+
            DESCRIPTION+" TEXT, "+ DATE+" TEXT, "+
            CITY+" TEXT, "+ STATE+" TEXT, "+ COUNTRY+" TEXT, "+
            LONGITUDE+" TEXT, "+ LATITUDE+" TEXT)";
    private static final String INSERT_SQL = "INSERT INTO " + TABLE_NAME +
            " ("+NICKNAME+", "+UUID+", "+DESCRIPTION+", "+DATE+", "+
            CITY+", "+STATE+", "+COUNTRY+", "+
            LONGITUDE+", "+LATITUDE+") "+
            "VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String READ_SQL = "SELECT "+ID+", "+NICKNAME+", " + UUID+", "+
            DESCRIPTION+", " + DATE +", "+
            CITY+", " +STATE+ ", "+COUNTRY+", "+
            LONGITUDE+ ", "+LATITUDE+
            " FROM " +
            TABLE_NAME;
    private static final String UPDATE_SQL = "UPDATE " + TABLE_NAME +
            " SET "+
            NICKNAME+"=?, " +
            DESCRIPTION+"=?, " +
            DATE+"=?, " +
            CITY+"=?, " +
            STATE+"=?, " +
            COUNTRY+"=?, " +
            LONGITUDE+"=?, " +
            LATITUDE+"=? " +
            "WHERE "+UUID+"=?";

    // The Context object that created this StocksDb
    private final Context context;
    private final SQLiteOpenHelper helper;
    private SQLiteStatement insertStmt;
    private  SQLiteStatement updateStmt;
    private final SQLiteDatabase db;

    public LocationDb(Context ctx){
        context = ctx;

        // initialize the database helper
        helper = new SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(CREATE_TABLE);
                Log.d(TAG, "Created table: \n" + CREATE_TABLE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion,
                                  int newVersion) {
                throw new UnsupportedOperationException();
            }
        };

        // open the database
        db = helper.getWritableDatabase();

        // pre-compile statements
        insertStmt = db.compileStatement(INSERT_SQL);
        updateStmt = db.compileStatement(UPDATE_SQL);
    }

    public int addTrip(Trip trip){
        //Log.d(TAG, "Adding stock to db, stock="+stock);
        Location loc = trip.getLocation();
        Date date = trip.getDate();
        insertStmt.bindString(1, trip.getName());
        insertStmt.bindString(2, trip.getId().toString());
        insertStmt.bindString(3, trip.getDescription());
        insertStmt.bindString(4, date.toString());
        insertStmt.bindString(5, loc.getCity());
        insertStmt.bindString(6, loc.getState());
        insertStmt.bindString(7, loc.getCountry());
        insertStmt.bindString(8, loc.getLongitude());
        insertStmt.bindString(9, loc.getLattitude());
        int id = (int) insertStmt.executeInsert();
        return id;
    }

    public void updateTrip(Trip trip){
        Log.d(TAG, "Updating trip description in DB="+trip.toString());
        Location loc = trip.getLocation();
        updateStmt.bindString(1, trip.getName());
        updateStmt.bindString(2, trip.getDescription());
        updateStmt.bindString(3, trip.getDate().toString());
        updateStmt.bindString(4, loc.getCity());
        updateStmt.bindString(5, loc.getState());
        updateStmt.bindString(6, loc.getCountry());
        updateStmt.bindString(7, loc.getLongitude());
        updateStmt.bindString(8, loc.getLattitude());
        updateStmt.bindString(9, trip.getId().toString());
        updateStmt.execute();
    }

    public ArrayList<Trip> getTrips() throws ParseException{
        Log.d(TAG, "Getting trips form DB");
        Cursor results = db.rawQuery(READ_SQL, null);
        ArrayList<Trip> trips = new ArrayList<Trip>(results.getCount());
        if (results.moveToFirst()){
            int idCol = results.getColumnIndex(ID);
            int nameCol = results.getColumnIndex(NICKNAME);
            int uuidCol = results.getColumnIndex(UUID);
            int descCol = results.getColumnIndex(DESCRIPTION);
            int dateCol = results.getColumnIndex(DATE);
            int cityCol = results.getColumnIndex(CITY);
            int stateCol = results.getColumnIndex(STATE);
            int countryCol = results.getColumnIndex(COUNTRY);
            int longCol = results.getColumnIndex(LONGITUDE);
            int latCol = results.getColumnIndex(LATITUDE);
            do {
                Location loc = new Location(results.getString(longCol), results.getString(latCol),
                        results.getString(cityCol), results.getString(stateCol), results.getString(countryCol));

                Trip trip = Trip.recoverTrip(results.getString(nameCol), results.getString(uuidCol),
                        results.getString(uuidCol), results.getString(dateCol), loc);
                Log.d(TAG, "Trip from db = " + trip.toString());
                trips.add(trip);
            } while (results.moveToNext());
        }
        if (!results.isClosed()){
            results.close();
        }
        return trips;
    }

}

