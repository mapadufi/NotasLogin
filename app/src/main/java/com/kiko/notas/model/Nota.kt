package com.kiko.notas.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_notas")
data class Nota(
    @ColumnInfo(name = "titulo") val titulo: String,
    @ColumnInfo(name = "anotacao") val anotacao: String
){
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
