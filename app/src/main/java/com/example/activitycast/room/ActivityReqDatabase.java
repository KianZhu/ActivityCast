package com.example.activitycast.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.activitycast.model.ActivityReq;

@Database(entities = {ActivityReq.class}, version = 1)
public abstract class ActivityReqDatabase extends RoomDatabase {
    public abstract ActivityReqDao getActivityReqDao();

    private static ActivityReqDatabase dbInstance;

    public static synchronized ActivityReqDatabase getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ActivityReqDatabase.class, "activityreq_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }
}
