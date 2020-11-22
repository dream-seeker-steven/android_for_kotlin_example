package cn.suyyy.jetpackdemo.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cn.suyyy.jetpackdemo.data.Book

@Dao
interface BookDao {

    @Insert
    fun insertBook(book: Book): Long

    @Query("select * from Book")
    fun loadAllBooks(): List<Book>
}