package com.example.mvvmsample.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_click")
data class ClickVo(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "aid") val nIdx : Int = 0
)