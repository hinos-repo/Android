package com.example.mvvmsample.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
@Database(entities = [ClickVo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun daoClick() : DaoClick

    companion object
    {
        private val DB_NAME = "my_db"

        private var m_instance: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase
        {
            return m_instance ?: synchronized(this)
            {
                m_instance ?: buildDatabase(context).also { m_instance = it }
            }
        }

        private fun buildDatabase(context: Context) : AppDatabase
        {
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(object : RoomDatabase.Callback()
                    {
                        override fun onCreate(db: SupportSQLiteDatabase)
                        {
                            super.onCreate(db)
                        }
                        override fun onOpen(db: SupportSQLiteDatabase)
                        {
                            super.onOpen(db)
                        }
                    }).build()
        }
    }

    @Dao
    interface DaoClick
    {
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insert(vo: ClickVo)

        @Query("SELECT COUNT(*) FROM tb_click")
        fun getCount(): Int
    }
}