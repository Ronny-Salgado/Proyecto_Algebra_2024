package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MatrizInversaActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_matriz_inversa)

                    val etMatriz = findViewById<EditText>(R.id.etMatriz)
                    val tvResultadoMatriz = findViewById<TextView>(R.id.tvResultadoMatriz)
                    val btnCalcularInversa = findViewById<Button>(R.id.btnCalcularInversa)

                    btnCalcularInversa.setOnClickListener {
                        val matriz = parsearMatriz(etMatriz.text.toString())
                        if (matriz != null && matriz.isNotEmpty() && matriz.size == matriz[0].size) {
                            val pasos = mutableListOf<String>()
                            val inversa = metodoGaussJordan(matriz, pasos)
                            if (inversa != null) {
                                pasos.add(0, "Matriz Original:\n${formatearMatriz(matriz)}")
                                tvResultadoMatriz.text = formatearPasos(pasos) + "\n\nMatriz Inversa:\n" + formatearMatriz(inversa)
                            } else {
                                tvResultadoMatriz.text = "No se puede calcular la inversa"
                            }
                        } else {
                            tvResultadoMatriz.text = "Error: Matriz inválida o no cuadrada"
                        }
                    }
                }

                // Función para parsear matrices de texto a listas
                private fun parsearMatriz(entrada: String): Array<Array<Double>>? {
                    return try {
                        entrada.split(";").map { fila ->
                            fila.split(",").map { it.trim().toDouble() }.toTypedArray()
                        }.toTypedArray()
                    } catch (e: Exception) {
                        null
                    }
                }

                // Método de Gauss-Jordan para calcular la inversa de una matriz con pasos
                private fun metodoGaussJordan(matriz: Array<Array<Double>>, pasos: MutableList<String>): Array<Array<Double>>? {
                    val n = matriz.size
                    val matrizAumentada = Array(n) { i -> Array(2 * n) { j -> if (j < n) matriz[i][j] else if (j == n + i) 1.0 else 0.0 } }

                    pasos.add("Matriz aumentada inicial:\n${formatearMatriz(matrizAumentada)}\n")

                    for (i in 0 until n) {
                        if (matrizAumentada[i][i] == 0.0) {
                            var encontrado = false
                            for (j in i + 1 until n) {
                                if (matrizAumentada[j][i] != 0.0) {
                                    val temp = matrizAumentada[i]
                                    matrizAumentada[i] = matrizAumentada[j]
                                    matrizAumentada[j] = temp
                                    pasos.add("Intercambio de fila ${i + 1} con fila ${j + 1}:\n${formatearMatriz(matrizAumentada)}\n")
                                    encontrado = true
                                    break
                                }
                            }
                            if (!encontrado) return null
                        }

                        val elementoDiagonal = matrizAumentada[i][i]
                        for (j in 0 until 2 * n) matrizAumentada[i][j] /= elementoDiagonal
                        pasos.add("Normalización de fila ${i + 1} dividiendo por ${elementoDiagonal}:\n${formatearMatriz(matrizAumentada)}\n")

                        for (j in 0 until n) {
                            if (i != j) {
                                val ratio = matrizAumentada[j][i]
                                for (k in 0 until 2 * n) matrizAumentada[j][k] -= ratio * matrizAumentada[i][k]
                                pasos.add("Eliminación de elemento en fila ${j + 1}, columna ${i + 1} utilizando fila ${i + 1} y multiplicando por ${ratio}:\n${formatearMatriz(matrizAumentada)}\n")
                            }
                        }
                    }

                    val matrizInversa = Array(n) { i -> Array(n) { j -> matrizAumentada[i][n + j] } }
                    pasos.add("Matriz aumentada final:\n${formatearMatriz(matrizAumentada)}")
                    return matrizInversa
                }

                private fun formatearMatriz(matriz: Array<Array<Double>>): String {
                    return matriz.joinToString("\n") { fila -> fila.joinToString(" | ") { "%.2f".format(it) } }
                }

                private fun formatearPasos(pasos: List<String>): String {
                    return pasos.joinToString("\n")
                }
            }
