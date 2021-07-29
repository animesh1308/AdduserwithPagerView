package com.example.adduserwithpagerview;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserEntity.class},version = 1)
 abstract class UserRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
