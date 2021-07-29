package com.example.adduserwithpagerview;

import android.content.Context;

import androidx.room.Room;

public class UserRoomDatabaseClient {

    private Context context;
    private static UserRoomDatabaseClient dbInstance;
    private  UserRoomDatabase userRoomDatabase;

    private UserRoomDatabaseClient(Context context){
        this.context=context;
        userRoomDatabase= Room.databaseBuilder(context,UserRoomDatabase.class,"userdb").build();

    }

    public static synchronized UserRoomDatabaseClient getInstance(Context context){

    if(dbInstance==null){
        dbInstance=new UserRoomDatabaseClient(context);

    }
    return dbInstance;
    }
    public UserRoomDatabase getUserDatabase() {return userRoomDatabase;}
}
