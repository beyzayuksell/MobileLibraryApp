package com.beyzayuksel.libraryapp;

// Room Database is Local Database in Android Std.

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Add database entities
@Database(entities = {MainData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    // Create db instance
    // Define For Acceessing Database
    private static RoomDB database;

    // Define db name, ex:libraryDB
    private static String DATABASE_NAME = "database";

    // *Singleton Pattern* - One Object(create only one database)
    public synchronized static RoomDB getInstance(Context context){
        // Check Condition
        if(database == null){
            // When database is null
            // Initialize database
            database = Room.databaseBuilder(context.getApplicationContext()
            ,RoomDB.class, DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        }
        // Return Database
        return database;
    }

    
}
