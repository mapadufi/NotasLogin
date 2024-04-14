package com.kiko.notas.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kiko.notas.model.Nota

@Dao
interface NotaDao {

    @Insert
    fun inserir(listaNotas: MutableList<Nota>)

    @Query("SELECT * FROM tabela_notas ORDER BY uid ASC")
    fun get(): MutableList<Nota>

    @Query("UPDATE tabela_notas SET titulo = :novoTitulo, anotacao = :novoAnotacao " +
            "WHERE uid = :uid")
    fun atualizar(uid: Int, novoTitulo: String, novoAnotacao: String)

    @Query("DELETE FROM tabela_notas WHERE uid = :id")
    fun deletar(id: Int)

}
