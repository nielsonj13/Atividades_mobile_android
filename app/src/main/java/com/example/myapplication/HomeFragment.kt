package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
 * =================================================================================
 * EXPLICAÇÃO
 * =================================================================================
 *
 * Como as Coroutines ajudaram a resolver o problema?
 *
 * 1. Evitar Travamentos na UI (ANR):
 * As tarefas de 'buscar hábitos' (3s) e 'calcular estatísticas' (4s) são
 * demoradas. Se fossem feitas na Thread Principal (UI), o aplicativo
 * "congelaria", impedindo o usuário de interagir.
 *
 * 2. Segurança no Ciclo de Vida do Fragment:
 * Em vez de um CoroutineScope manual, usamos `viewLifecycleOwner.lifecycleScope`.
 * Este é o "Gerente de Turno" ideal para Fragments. Ele automaticamente
 * cancela todas as coroutines lançadas (ex: uma busca de hábitos em andamento)
 * quando a view do fragmento é destruída (ex: o usuário navega para outra tela).
 * Isso previne "memory leaks" e "crashes" ao tentar atualizar uma UI que
 * não existe mais.
 *
 * 3. Dispatchers Corretos (Eficiência):
 * Usamos `withContext(Dispatchers.IO)` para a "Cozinha" (tarefas de rede/disco)
 * e `withContext(Dispatchers.Default)` para a "Área de Preparação" (cálculos
 * pesados de CPU). Isso libera a Thread Principal (Main) para continuar
 * responsiva (o "Garçom no Salão").
 *
 * 4. Código Limpo (Suspensão vs. Bloqueio):
 * O código dentro de `launch` parece sequencial. Quando chamamos
 * `val result = withContext(...)`, a coroutine "suspende" (pausa e libera
 * a thread) e só "retoma" quando o resultado está pronto.
 *
 * =================================================================================
 */

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        _binding = FragmentHomeBinding.bind(view)


        binding.btnFetchHabits.setOnClickListener {
            startDataFetchTask()
        }


        binding.btnCalculateStats.setOnClickListener {
            startCalculationTask()
        }
    }

    private fun startDataFetchTask() {

        viewLifecycleOwner.lifecycleScope.launch {

            showLoading(true)
            binding.tvResultDisplay.text = "Buscando hábitos da rede..."


            val result = withContext(Dispatchers.IO) {
                fetchHabitsFromNetwork()
            }


            showLoading(false)
            binding.tvResultDisplay.text = result
        }
    }


    private fun startCalculationTask() {
        viewLifecycleOwner.lifecycleScope.launch {

            showLoading(true)
            binding.tvResultDisplay.text = "Calculando estatísticas..."

            val result = withContext(Dispatchers.Default) {
                simulateComplexStatsCalculation()
            }

            showLoading(false)
            binding.tvResultDisplay.text = "Total de hábitos concluídos: $result"
        }
    }

    private suspend fun fetchHabitsFromNetwork(): String {
        delay(3000)
        return "Lista de Hábitos: [Ler, Malhar, Estudar]"
    }

    private suspend fun simulateComplexStatsCalculation(): Long {
        delay(4000)
        var i = 0L
        repeat(2_000_000_000) {
            i++
        }
        return i / 100_000_000
    }


    private fun showLoading(isLoading: Boolean) {

        if (_binding == null) return

        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            binding.btnFetchHabits.isEnabled = false
            binding.btnCalculateStats.isEnabled = false
        } else {
            binding.progressBar.visibility = View.GONE
            binding.btnFetchHabits.isEnabled = true
            binding.btnCalculateStats.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
