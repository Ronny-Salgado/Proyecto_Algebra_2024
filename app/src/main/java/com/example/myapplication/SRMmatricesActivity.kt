package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SRMmatricesActivity : AppCompatActivity() {

    private lateinit var matrixAdapter: MatrixAdapter
    private val matrices = mutableListOf<Array<Array<Int>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_srmmatrices)

        // Obtenemos los EditTexts de la interfaz para la matriz A
        val etMatrixA = arrayOf(
            arrayOf(findViewById<EditText>(R.id.etA00), findViewById<EditText>(R.id.etA01), findViewById<EditText>(R.id.etA02), findViewById<EditText>(R.id.etA03), findViewById<EditText>(R.id.etA04)),
            arrayOf(findViewById<EditText>(R.id.etA10), findViewById<EditText>(R.id.etA11), findViewById<EditText>(R.id.etA12), findViewById<EditText>(R.id.etA13), findViewById<EditText>(R.id.etA14)),
            arrayOf(findViewById<EditText>(R.id.etA20), findViewById<EditText>(R.id.etA21), findViewById<EditText>(R.id.etA22), findViewById<EditText>(R.id.etA23), findViewById<EditText>(R.id.etA24)),
            arrayOf(findViewById<EditText>(R.id.etA30), findViewById<EditText>(R.id.etA31), findViewById<EditText>(R.id.etA32), findViewById<EditText>(R.id.etA33), findViewById<EditText>(R.id.etA34)),
            arrayOf(findViewById<EditText>(R.id.etA40), findViewById<EditText>(R.id.etA41), findViewById<EditText>(R.id.etA42), findViewById<EditText>(R.id.etA43), findViewById<EditText>(R.id.etA44))
        )
        val etMatrixB = findViewById<EditText>(R.id.etMatrixB)
        val tvMatrixResult = findViewById<TextView>(R.id.tvMatrixResult)

        val btnAddMatrix = findViewById<Button>(R.id.btnAddMatrix)
        val btnSum = findViewById<Button>(R.id.btnSum)
        val btnSubtract = findViewById<Button>(R.id.btnSubtract)
        val btnMultiply = findViewById<Button>(R.id.btnMultiply)
        val btnEscalar = findViewById<Button>(R.id.btnEscalar)
        val btnInversa = findViewById<Button>(R.id.btnInversa)

        // Configurar RecyclerView
        val rvMatrices = findViewById<RecyclerView>(R.id.rvMatrices)
        rvMatrices.layoutManager = LinearLayoutManager(this)
        matrixAdapter = MatrixAdapter(matrices)
        rvMatrices.adapter = matrixAdapter

        // Botón para agregar matrices
        btnAddMatrix.setOnClickListener {
            val inputMatrixA = readMatrixInput(etMatrixA)
            if (isValidMatrixInput(inputMatrixA)) {
                val matrixA = parseMatrix(inputMatrixA)
                if (matrixA != null) {
                    matrices.add(matrixA)
                    matrixAdapter.notifyDataSetChanged()
                    clearMatrixInput(etMatrixA)
                } else {
                    Toast.makeText(this, "Error: Matriz inválida", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error: Entrada de matriz no válida", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón de suma
        btnSum.setOnClickListener {
            val selectedMatrices = matrixAdapter.getSelectedMatrices()
            if (selectedMatrices.size == 2) {
                if (selectedMatrices[0].size == selectedMatrices[1].size && selectedMatrices[0][0].size == selectedMatrices[1][0].size) {
                    val (operationSteps, result) = sumMatrices(selectedMatrices[0], selectedMatrices[1])
                    tvMatrixResult.text = "Pasos:\n${formatMatrixi(operationSteps)}\n\nResultado:\n${formatMatrix(result)}"
                } else {
                    tvMatrixResult.text = "Las matrices no son del mismo tamaño"
                }
            } else {
                tvMatrixResult.text = "Error: Seleccione dos matrices"
            }
        }

        // Botón de resta
        btnSubtract.setOnClickListener {
            val selectedMatrices = matrixAdapter.getSelectedMatrices()
            if (selectedMatrices.size == 2) {
                if (selectedMatrices[0].size == selectedMatrices[1].size && selectedMatrices[0][0].size == selectedMatrices[1][0].size) {
                    val (operationSteps, result) = subtractMatrices(selectedMatrices[0], selectedMatrices[1])
                    tvMatrixResult.text = "Pasos:\n${formatMatrixi(operationSteps)}\n\nResultado:\n${formatMatrix(result)}"
                } else {
                    tvMatrixResult.text = "Las matrices no son del mismo tamaño"
                }
            } else {
                tvMatrixResult.text = "Error: Seleccione dos matrices"
            }
        }

        // Botón de multiplicación
        btnMultiply.setOnClickListener {
            try {
                val selectedMatrices = matrixAdapter.getSelectedMatrices()
                if (selectedMatrices.size == 2) {
                    if (areDimensionsCompatible(selectedMatrices[0], selectedMatrices[1])) {
                        val (operationMatrix, resultMatrix) = multiplicarMatrices(selectedMatrices[0], selectedMatrices[1])
                        tvMatrixResult.text = "Operaciones:\n${formatMatrixi(operationMatrix)}\n\nResultado:\n${formatMatrix(resultMatrix)}"
                    } else {
                        tvMatrixResult.text = "Error: Las dimensiones no son compatibles"
                    }
                } else {
                    tvMatrixResult.text = "Error: Seleccione dos matrices"
                }
            } catch (e: Exception) {
                tvMatrixResult.text = "Error al multiplicar matrices"
                e.printStackTrace()
            }
        }

        // Botón de multiplicación por escalar
        btnEscalar.setOnClickListener {
            val selectedMatrices = matrixAdapter.getSelectedMatrices()
            val inputMatrixB = etMatrixB.text.toString()
            if (selectedMatrices.size == 1 && isValidScalarInput(inputMatrixB)) {
                val matrix = selectedMatrices[0]
                val scalar = inputMatrixB.toIntOrNull()
                if (scalar != null) {
                    val (operationSteps, result) = multiplicarPorEscalar(matrix, scalar)
                    tvMatrixResult.text = "Pasos:\n${formatMatrixi(operationSteps)}\n\nResultado:\n${formatMatrix(result)}"
                } else {
                    tvMatrixResult.text = "Error: Escalar inválido"
                }
            } else {
                tvMatrixResult.text = "Error: Seleccione una matriz o entrada de escalar no válida"
            }
        }
        // Botón para calcular la inversa
        btnInversa.setOnClickListener {
            val selectedMatrices = matrixAdapter.getSelectedMatrices()
            if (selectedMatrices.size == 1) {
                val matrix = selectedMatrices[0]
                if (matrix.size == matrix[0].size) { // Verifica si es cuadrada
                    val pasos = mutableListOf<String>()
                    val inversa = calcularInversa(matrix.map { it.map { it.toDouble() }.toTypedArray() }.toTypedArray(), pasos)
                    if (inversa != null) {
                        pasos.add(0, "Matriz Original:\n${formatMatrix(matrix)}")
                        tvMatrixResult.text = pasos.joinToString("\n") + "\n\nMatriz Inversa:\n" + inversa.joinToString("\n") { row -> row.joinToString(" | ") { "%.2f".format(it) } }
                    } else {
                        tvMatrixResult.text = "No se puede calcular la inversa"
                    }
                } else {
                    tvMatrixResult.text = "Error: Matriz no cuadrada"
                }
            } else {
                tvMatrixResult.text = "Seleccione una sola matriz para calcular su inversa"
            }
        }
    }

    // Función para leer la entrada de la matriz A desde los EditTexts
    private fun readMatrixInput(etMatrixA: Array<Array<EditText>>): String {
        return etMatrixA.filter { row ->
            row.any { it.text.isNotBlank() }
        }.joinToString(";") { row ->
            row.filter { it.text.isNotBlank() }
                .joinToString(",") { it.text.toString().trim() }
        }
    }

    // Función para limpiar los EditTexts de la matriz A
    private fun clearMatrixInput(etMatrixA: Array<Array<EditText>>) {
        for (row in etMatrixA) {
            for (et in row) {
                et.text.clear()
            }
        }
    }

    // Función para verificar compatibilidad de dimensiones
    private fun areDimensionsCompatible(a: Array<Array<Int>>, b: Array<Array<Int>>): Boolean {
        return a[0].size == b.size
    }

    // Función para validar entrada de matriz
    private fun isValidMatrixInput(input: String): Boolean {
        val regex = "^-?\\d+(,-?\\d+)*(;\\-?\\d+(,-?\\d+)*)*$".toRegex()
        return input.matches(regex)
    }

    // Función para validar entrada de escalar
    private fun isValidScalarInput(input: String): Boolean {
        val regex = "^-?\\d+$".toRegex()
        return input.matches(regex)
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

// Funciones de operaciones básicas con pasos
    private fun sumMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Pair<Array<Array<String>>, Array<Array<Int>>> {
        val rows = a.size
        val cols = a[0].size
        val operationSteps = Array(rows) { Array(cols) { "" } }
        val resultMatrix = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val step = "${a[i][j]} + ${b[i][j]}"
                operationSteps[i][j] = step
                resultMatrix[i][j] = a[i][j] + b[i][j]
            }
        }
        return Pair(operationSteps, resultMatrix)
    }

    private fun subtractMatrices(a: Array<Array<Int>>, b: Array<Array<Int>>): Pair<Array<Array<String>>, Array<Array<Int>>> {
        val rows = a.size
        val cols = a[0].size
        val operationSteps = Array(rows) { Array(cols) { "" } }
        val resultMatrix = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val step = "${a[i][j]} - ${b[i][j]}"
                operationSteps[i][j] = step
                resultMatrix[i][j] = a[i][j] - b[i][j]
            }
        }
        return Pair(operationSteps, resultMatrix)
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

    private fun multiplicarPorEscalar(a: Array<Array<Int>>, b: Int): Pair<Array<Array<String>>, Array<Array<Int>>> {
        val rows = a.size
        val cols = a[0].size
        val operationSteps = Array(rows) { Array(cols) { "" } }
        val resultMatrix = Array(rows) { Array(cols) { 0 } }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                val step = "${a[i][j]} * $b"
                operationSteps[i][j] = step
                resultMatrix[i][j] = a[i][j] * b
            }
        }
        return Pair(operationSteps, resultMatrix)
    }

    // Función para calcular la inversa utilizando Gauss-Jordan con pasos detallados
    private fun calcularInversa(matrix: Array<Array<Double>>, pasos: MutableList<String>): Array<Array<Double>>? {
        val n = matrix.size
        // Crear matriz aumentada
        val matrizAumentada = Array(n) { i -> Array(2 * n) { j -> if (j < n) matrix[i][j] else if (j == n + i) 1.0 else 0.0 } }

        pasos.add("Matriz aumentada inicial:\n${formatearMatriz(matrizAumentada)} \n")

        for (i in 0 until n) {
            // Verificar y corregir si el elemento diagonal es 0
            if (matrizAumentada[i][i] == 0.0) {
                var encontrado = false
                for (j in i + 1 until n) {
                    if (matrizAumentada[j][i] != 0.0) {
                        val temp = matrizAumentada[i]
                        matrizAumentada[i] = matrizAumentada[j]
                        matrizAumentada[j] = temp
                        pasos.add("F ${i + 1} <-> ${j + 1}:\n${formatearMatriz(matrizAumentada)} \n")
                        encontrado = true
                        break
                    }
                }
                if (!encontrado) return null // No se puede calcular la inversa
            }

            // Normalizar la fila para que el elemento diagonal sea 1
            val elementoDiagonal = matrizAumentada[i][i]
            for (j in 0 until 2 * n) matrizAumentada[i][j] /= elementoDiagonal
            pasos.add(
                "F ${i + 1} / ${"%.2f".format(elementoDiagonal)}:\n${formatearMatriz(matrizAumentada)} \n"
            )

            // Hacer ceros en la columna actual de las demás filas
            for (j in 0 until n) {
                if (i != j) {
                    val ratio = matrizAumentada[j][i]
                    for (k in 0 until 2 * n) matrizAumentada[j][k] -= ratio * matrizAumentada[i][k]
                    pasos.add(
                        "F ${j + 1}  - ${"%.2f".format(ratio)} * F ${i + 1}:\n${formatearMatriz(matrizAumentada)}  \n"
                    )
                }
            }
        }

        // Extraer la matriz inversa de la matriz aumentada
        val matrizInversa = Array(n) { i -> Array(n) { j -> matrizAumentada[i][n + j] } }
        pasos.add("Matriz aumentada final:\n${formatearMatriz(matrizAumentada)} \n")
        pasos.add("Matriz inversa:\n${formatearMatriz(matrizInversa)} \n")
        return matrizInversa
    }

    // Función para formatear matrices con bordes claros y legibles
    private fun formatearMatriz(matriz: Array<Array<Double>>): String {
        return matriz.joinToString("\n") { fila ->
            fila.joinToString(" | ", prefix = "| ", postfix = " |") { "%.2f".format(it) }
        }
    }


    private fun formatMatrix(matrix: Array<Array<Int>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString(", ") }
    }

    private fun formatMatrixi(matrix: Array<Array<String>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString(", ") }
    }
}