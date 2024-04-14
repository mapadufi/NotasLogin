package com.kiko.notas.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kiko.notas.AppDatabase
import com.kiko.notas.EditNotaActivity
import com.kiko.notas.dao.NotaDao
import com.kiko.notas.databinding.NotaItemBinding
import com.kiko.notas.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotaAdapter(private val context: Context, private val listaNotas: MutableList<Nota>):
    RecyclerView.Adapter<NotaAdapter.NotaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val itemLista = NotaItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NotaViewHolder(itemLista)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        holder.txtNotaTitulo.text = listaNotas[position].titulo
        holder.txtNotaAnotacao.text = listaNotas[position].anotacao

        holder.btNotaEdit.setOnClickListener{
            val intent = Intent(context, EditNotaActivity::class.java)
            intent.putExtra("titulo", listaNotas[position].titulo)
            intent.putExtra("anotacao", listaNotas[position].anotacao)
            intent.putExtra("uid", listaNotas[position].uid)
            context.startActivity(intent)
        }

        holder.btNotaDel.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nota = listaNotas[position]
                val notaDao: NotaDao = AppDatabase.getInstance(context).notasDao()
                notaDao.deletar(nota.uid)
                listaNotas.remove(nota)

                withContext(Dispatchers.Main){
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getItemCount() = listaNotas.size

    inner class NotaViewHolder(binding: NotaItemBinding) : RecyclerView.ViewHolder(binding.root){
        val txtNotaTitulo = binding.txtNotaTitulo
        val txtNotaAnotacao = binding.txtNotaAnotacao
        val btNotaEdit = binding.btNotaEdit
        val btNotaDel = binding.btNotaDel

    }

}
