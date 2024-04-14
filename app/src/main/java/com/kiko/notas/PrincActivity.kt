package com.kiko.notas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.kiko.notas.adapter.NotaAdapter
import com.kiko.notas.dao.NotaDao
import com.kiko.notas.databinding.ActivityPrincBinding
import com.kiko.notas.model.Nota
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PrincActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincBinding
    private lateinit var notaDao: NotaDao
    private lateinit var notaAdapter: NotaAdapter
    private val _listaNotas = MutableLiveData<MutableList<Nota>>()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            getNotas()

            withContext(Dispatchers.Main){

                _listaNotas.observe(this@PrincActivity){listaNotas ->
                    val recyclerViewNotas = binding.recyclerNota
                    recyclerViewNotas.layoutManager = LinearLayoutManager(this@PrincActivity)
                    recyclerViewNotas.setHasFixedSize(true)
                    notaAdapter = NotaAdapter(this@PrincActivity, listaNotas)
                    recyclerViewNotas.adapter = notaAdapter
                    notaAdapter.notifyDataSetChanged()
                }
            }
        }


        binding.btAdd.setOnClickListener{
            val cadNota = Intent(this, CadNotaActivity::class.java)
            startActivity(cadNota)
        }
    }


    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            getNotas()

            withContext(Dispatchers.Main){

                _listaNotas.observe(this@PrincActivity){listaNotas ->
                    val recyclerViewNotas = binding.recyclerNota
                    recyclerViewNotas.layoutManager = LinearLayoutManager(this@PrincActivity)
                    recyclerViewNotas.setHasFixedSize(true)
                    notaAdapter = NotaAdapter(this@PrincActivity, listaNotas)
                    recyclerViewNotas.adapter = notaAdapter
                    notaAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getNotas(){
        notaDao = AppDatabase.getInstance(this).notasDao()
        val listaNotas: MutableList<Nota> = notaDao.get()
        _listaNotas.postValue(listaNotas)
    }
}
