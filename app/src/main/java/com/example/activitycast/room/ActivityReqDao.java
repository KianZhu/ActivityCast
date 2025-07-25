package com.example.activitycast.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.activitycast.model.ActivityReq;

import java.util.List;

@Dao
public interface ActivityReqDao {
    @Insert
    void insert(ActivityReq activityReq);

    @Delete
    void delete(ActivityReq activityReq);

    @Query("DELETE FROM activityreq_table")
    void deleteAll();

    @Query("SELECT * FROM activityreq_table")
    LiveData<List<ActivityReq>> getAllActivityReq();
}
