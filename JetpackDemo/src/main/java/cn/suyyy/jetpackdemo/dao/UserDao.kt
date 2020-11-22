package cn.suyyy.jetpackdemo.dao

import androidx.room.*
import cn.suyyy.jetpackdemo.data.User

@Dao
interface UserDao {

    @Insert
    fun insertUser(user: User) : Long

    @Update
    fun updateUser(newUser: User)

    @Query("select * from User")
    fun loadAllUsers(): List<User>

    @Query("select * from User where age > :age")
    fun loadUsersOlderThen(age: Int): List<User>

    @Delete
    fun deleteUser(user: User)

    @Query("delete from User where lastName = :lastName")
    fun deleteUserByLastName(lastName: String): Int
}