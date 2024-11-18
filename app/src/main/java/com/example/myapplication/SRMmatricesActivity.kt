package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class SRMmatricesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_srmmatrices)

        val etMatrixA = findViewById<EditText>(R.id.etMatrixA)
        val etMatrixB = findViewById<EditText>(R.id.etMatrixB)
        val tvMatrixResult = findViewById<TextView>(R.id.tvMatrixResult)

        val btnSum = findViewById<Button>(R.id.btnSum)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnEscalar = findViewById<Button>(R.id.btnEscalar)

        // Botón de suma
        btnSum.setOnClickListener {
            val matrixA = parseMatrix(etMatrixA.text.toString())
            val matrixB = parseMatrix(etMatrixB.text.toString())
            if (matrixA != null && matrixB != null) {
                val result = sumMatrices(matrixA, matrixB)
                tvMatrixResult.text = formatMatrix(result)
            } else {
                tvMatrixResult.text = "Error: Matrices inválidas"
            }
        }

        // Botón de resta
        btnSubtract.setOnClickListener {
            val matrixA = parseMatrix(etMatrixA.text.toString())
            val matrixB = parseMatrix(etMatrixB.text.toString())
            if (matrixA != null && matrixB != null) {
                val result = subtractMatrices(matrixA, matrixB)
                tvMatrixResult.text = formatMatrix(result)
            } else {
                tvMatrixResult.text = "Error: Matrices inválidas"
            }
        }

        // Botón de multiplicación
        btnMultiply.setOnClickListener {
            val matrixA = parseMatrix(etMatrixA.text.toString())
            val matrixB = parseMatrix(etMatrixB.text.toString())
            if (matrixA != null && matrixB != null) {
                val (operationMatrix, resultMatrix) = multiplicarMatrices(matrixA, matrixB)
                tvMatrixResult.text = "Operaciones:\n${formatMatrixi(operationMatrix)}\n\nResultado:\n${formatMatrix(resultMatrix)}"
            } else {
                tvMatrixResult.text = "Error: Matrices inválidas"
            }
        }

        // Botón de multiplicación por escalar
        btnEscalar.setOnClickListener {
            val matrixA = parseMatrix(etMatrixA.text.toString())

            val scalarB = etMatrixB.text.toString().toIntOrNull()
            if (matrixA != null && scalarB != null) {
              val result = multiplicarPorEscalar(matrixA, scalarB)
                tvMatrixResult.text = formatMatrix(result)
        }
             else { tvMatrixResult.text = "Error: Entrada inválida"
             }
        }
    }

    // Función para parsear matrices de texto a listas
    private fun parseMatrix(input: String): Array<Array<Int>>? {
        return try {
            input.split(";").map { row ->
                row.split(",").map { it.trim().toInt() }.toTypedArray()
            }.toTypedArray()
        } catch (e: Exception) {
            null
        }
    }

    // Funciones de operaciones básicas
    private fun sumMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
        val rows = a.size
        val cols = a[0].size
        return Array(rows) { i -> Array(cols) { j -> a[i][j] + b[i][j] } }
    }

    private fun subtractMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Array<Array<Int>> {
        val rows = a.size
        val cols = a[0].size
        return Array(rows) { i -> Array(cols) { j -> a[i][j] - b[i][j] } }
    }

    private fun multiplicarMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Pair<Array<Array<String>>, Array<Array<Int>>> {
        val rowsA = a.size
        val colsA = a[0].size
        val colsB = b[0].size
        val operationMatrix = Array(rowsA) { Array(colsB) { "" } }
        val resultMatrix = Array(rowsA) { Array(colsB) { 0 } }
        for (i in 0 until rowsA) {
            for (j in 0 until colsB) {
                val operations = mutableListOf<String>()
                for (k in 0 until colsA) {
                    val operation = " ${a[i][k]}*${b[k][j]} "
                    operations.add(operation)
                    resultMatrix[i][j] += a[i][k] * b[k][j]
                }
                operationMatrix[i][j] = operations.joinToString(" + ")
            }
        }
        return Pair(operationMatrix, resultMatrix)
    }

    private fun multiplicarPorEscalar(a: Array<Array<Int>>, b: Int ): Array<Array<Int>> {
        val rows = a.size
        val cols = a[0].size
        return Array(rows) { i -> Array(cols) { j -> a[i][j] * b } }
    }

    private fun formatMatrix(matrix: Array<Array<Int>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString(", ") }
    }

    private fun formatMatrixi(matrix: Array<Array<String>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString(", ") }
    }
}
