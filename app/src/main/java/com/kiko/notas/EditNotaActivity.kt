package com.kiko.notas

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.kiko.notas.dao.NotaDao
import com.kiko.notas.databinding.ActivityEditNotaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditNotaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNotaBinding
    private lateinit var notaDao: NotaDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btEditCan.setOnClickListener {
            binding.edtEditTitulo.text.clear()
            binding.edtEditAnotacao.text.clear()
            startActivity(Intent(this, PrincActivity::class.java))
            finish()
        }

        val tituloRecuperado = intent.extras?.getString("titulo")
        val anotacaoRecuperado = intent.extras?.getString("anotacao")
        val uid = intent.extras!!.getInt("uid")

        binding.edtEditTitulo.setText(tituloRecuperado)
        binding.edtEditAnotacao.setText(anotacaoRecuperado)

        binding.btEditSal.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                val titulo = binding.edtEditTitulo.text.toString()
                val anotacao = binding.edtEditAnotacao.text.toString()
                val mensagem: Boolean

                if (titulo.isEmpty() || anotacao.isEmpty()) {
                    mensagem = false
                } else {
                    mensagem = true
                    atualizarNota(uid, titulo, anotacao)
                }
                withContext(Dispatchers.Main) {
                    if (mensagem) {
                        toastAtualizar()
                        finish()
                    } else {
                        toastErro()
                    }
                }
            }
        }
    }

    private fun atualizarNota(uid: Int, titulo: String, anotacao: String) {
        notaDao = AppDatabase.getInstance(this).notasDao()
        notaDao.atualizar(uid, titulo, anotacao)
    }

    @SuppressLint("MissingInflatedId")
    private fun toastAtualizar() {
        val inflater = layoutInflater
        val view =
            inflater.inflate(R.layout.layout_toast_verde, findViewById(R.id.container_sucesso))
        val textView = view.findViewById<TextView>(R.id.txtSucesso)
        textView.text = "Atualizado com sucesso"
        val toastCadastrar = Toast(this)
        toastCadastrar.view = view
        toastCadastrar.duration = Toast.LENGTH_LONG
        toastCadastrar.show()
    }

    private fun toastErro() {
        val inflater = layoutInflater
        val view =
            inflater.inflate(R.layout.layout_toast_vermelho, findViewById(R.id.container_erro))
        val textView = view.findViewById<TextView>(R.id.txt_erro)
        textView.text = "Preencha todos os ccampos"
        val toastErro = Toast(this)
        toastErro.view = view
        toastErro.duration = Toast.LENGTH_LONG
        toastErro.show()
    }
}
