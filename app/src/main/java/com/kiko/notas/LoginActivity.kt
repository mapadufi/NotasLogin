package com.kiko.notas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kiko.notas.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btLoginAcessar.setOnClickListener {
            val usuario = binding.edtLoginUsuario.text.toString()
            val senha = binding.edtLoginSenha.text.toString()
            if (usuario == "kiko" && senha == "120408") {
                val intent = Intent(this, PrincActivity::class.java)
                startActivity(intent)
                toastSucesso()
                finish()
            }else {
                binding.edtLoginUsuario.text.clear()
                binding.edtLoginSenha.text.clear()
                toastErro()
            }
        }
    }

    private fun toastSucesso() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.layout_toast_verde, findViewById(R.id.container_sucesso))
        val textView = view.findViewById<TextView>(R.id.txtSucesso)
        textView.text = "Bem vindo!"
        val toast_verde = Toast(this)
        toast_verde.view = view
        toast_verde.duration = Toast.LENGTH_LONG
        toast_verde.show()
    }

    private fun toastErro() {
        val inflater = layoutInflater
        val view = inflater.inflate(R.layout.layout_toast_vermelho, findViewById(R.id.container_erro))
        val textView = view.findViewById<TextView>(R.id.txt_erro)
        textView.text = "Usuario ou senha invalido"
        val toast_vermelho = Toast(this)
        toast_vermelho.view = view
        toast_vermelho.duration = Toast.LENGTH_LONG
        toast_vermelho.show()
    }
}






