package com.platzi.platzivideos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.platzi.platzivideos.model.VideoState;

/**
 * Created by Rafael on 9/08/16.
 */
public class SqlVideoStatesHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "videosStates";

    // Contacts table name
    private static final String TABLE_STATES = "states";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CURRENT_VIDEO = "current_video";
    private static final String KEY_CURRENT_TIME = "current_video_time";



    public SqlVideoStatesHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATES_TABLE = "CREATE TABLE " + TABLE_STATES + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_CURRENT_VIDEO + " TEXT,"
                + KEY_CURRENT_TIME + " TEXT" + ")";
        db.execSQL(CREATE_STATES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATES);
        onCreate(db);
    }

    public void addVideoState(VideoState state,String videoId) {
        if(hasVideoState(videoId)){
            updateVideoState(state,videoId);
            return;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(KEY_ID, videoId);
            values.put(KEY_CURRENT_VIDEO, state.getCurrentVideo());
            values.put(KEY_CURRENT_TIME, String.valueOf(state.getCurrentTime()));
        long ans=db.insert(TABLE_STATES, null, values);

    }

    public VideoState getVideoState(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STATES, new String[] {
                        KEY_CURRENT_VIDEO, KEY_CURRENT_TIME }, KEY_ID + "=?",
                new String[] {id }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {


                VideoState videoState = new VideoState(cursor.getString(0),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(KEY_CURRENT_TIME))));

            return videoState;
        }
        return null;
    }

    public int updateVideoState(VideoState videoState,String videoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CURRENT_VIDEO, videoState.getCurrentVideo());
        values.put(KEY_CURRENT_TIME, videoState.getCurrentTime());
        return db.update(TABLE_STATES, values, KEY_ID + " = ?",
                new String[] { videoId });
    }

    public boolean hasVideoState(String videoId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + TABLE_STATES + " WHERE "+KEY_ID+"=?", new String[]{videoId});
        return (mCursor != null && mCursor.moveToFirst());
    }
}
