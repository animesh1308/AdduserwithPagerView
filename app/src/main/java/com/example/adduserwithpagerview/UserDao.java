package com.example.adduserwithpagerview;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(UserEntity userEntity);

    @Query("select *from user_detail")
    List<UserEntity> getall();

    @Query("delete from user_detail where id= :id")
    void deleteuser(int id);

//    @Query("update user_detail set user_name= :name,user_mobileno= :mnumber,user_dob = :dob, gender= :gender,image= :image")
//    void updateuser(String name,String mnumber,String dob,String gender,int id,byte[] image);
}
