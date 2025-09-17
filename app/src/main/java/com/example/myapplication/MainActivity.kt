package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Pega as referências dos botões que criamos no XML
        val btnFrag1 = findViewById<Button>(R.id.btnFragmento1)
        val btnFrag2 = findViewById<Button>(R.id.btnFragmento2)
        val btnFrag3 = findViewById<Button>(R.id.btnFragmento3)

        // Define o que acontece quando o Botão 1 é clicado
        btnFrag1.setOnClickListener {
            // Chama nossa função para trocar o fragment, passando uma nova instância do Fragmento1
            trocarFragmento(Fragmento1())
        }

        // Define o que acontece quando o Botão 2 é clicado
        btnFrag2.setOnClickListener {
            trocarFragmento(Fragmento2())
        }

        // Define o que acontece quando o Botão 3 é clicado
        btnFrag3.setOnClickListener {
            trocarFragmento(Fragmento3())
        }

        // Opcional: Carrega um fragmento padrão ao iniciar o app para a tela não ficar vazia
        if (savedInstanceState == null) {
            trocarFragmento(Fragmento1())
        }
    }

    // Função que realiza a troca de Fragments
    private fun trocarFragmento(fragment: Fragment) {
        // Pega o gerenciador de fragments
        val fragmentManager = supportFragmentManager
        // Inicia uma transação
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Substitui o que estiver no container (R.id.fragment_container) pelo novo fragment
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        // Confirma a transação
        fragmentTransaction.commit()
    }
}