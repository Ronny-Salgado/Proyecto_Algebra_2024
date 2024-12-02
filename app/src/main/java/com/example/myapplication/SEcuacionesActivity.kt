package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SEcuacionesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secuaciones)

        val xA = findViewById<EditText>(R.id.xA)
        val yA = findViewById<EditText>(R.id.yA)
        val zA = findViewById<EditText>(R.id.zA)
        val dA = findViewById<EditText>(R.id.dA)

        val xB = findViewById<EditText>(R.id.xB)
        val yB = findViewById<EditText>(R.id.yB)
        val zB = findViewById<EditText>(R.id.zB)
        val dB = findViewById<EditText>(R.id.dB)

        val xC = findViewById<EditText>(R.id.xC)
        val yC = findViewById<EditText>(R.id.yC)
        val zC = findViewById<EditText>(R.id.zC)
        val dC = findViewById<EditText>(R.id.dC)

        val calcular = findViewById<Button>(R.id.calcular)
        val resultado = findViewById<TextView>(R.id.tvResultado)

        calcular.setOnClickListener {
            try {
                val pasos = StringBuilder()

                // Crear una lista de ecuaciones con los campos llenos
                val equations = mutableListOf<DoubleArray>()
                val constants = mutableListOf<Double>()

                // Añadir ecuaciones dinámicamente
                if (areAllFieldsFilled(xA, yA, zA, dA)) {
                    equations.add(doubleArrayOf(
                        xA.text.toString().toDouble(),
                        yA.text.toString().toDouble(),
                        zA.text.toString().toDouble()
                    ))
                    constants.add(dA.text.toString().toDouble())
                }
                if (areAllFieldsFilled(xB, yB, zB, dB)) {
                    equations.add(doubleArrayOf(
                        xB.text.toString().toDouble(),
                        yB.text.toString().toDouble(),
                        zB.text.toString().toDouble()
                    ))
                    constants.add(dB.text.toString().toDouble())
                }
                if (areAllFieldsFilled(xC, yC, zC, dC)) {
                    equations.add(doubleArrayOf(
                        xC.text.toString().toDouble(),
                        yC.text.toString().toDouble(),
                        zC.text.toString().toDouble()
                    ))
                    constants.add(dC.text.toString().toDouble())
                }

                // Si hay menos de 2 ecuaciones llenas, mostrar un mensaje
                if (equations.size < 2) {
                    resultado.text = "Debe haber al menos 2 ecuaciones con todos los campos llenos."
                    return@setOnClickListener
                }

                // Determinar el tamaño de la matriz
                val systemSize = equations.size
                val matrix = Array(systemSize) { DoubleArray(systemSize) }
                val constantsArray = DoubleArray(systemSize)

                // Asignar valores a la matriz y al vector de términos independientes
                for (i in equations.indices) {
                    matrix[i] = equations[i].copyOfRange(0, systemSize) // Crear una submatriz 2x2 o 3x3
                    constantsArray[i] = constants[i]  // Asignar el valor del término independiente
                }

                pasos.append("Matriz principal:\n")
                pasos.append(matrizToString(matrix))
                pasos.append("\nTérminos independientes:\n")
                pasos.append(constantsArray.joinToString(prefix = "[", postfix = "]"))
                pasos.append("\n\n")

                // Determinante de la matriz principal
                val determinant = calcularDeterminante(matrix)
                pasos.append("Determinante de la matriz principal: $determinant\n\n")

                if (determinant == 0.0) {
                    pasos.append("El sistema no tiene soluciones únicas (determinante = 0).\n")
                } else {
                    // Calcular determinantes de matrices modificadas
                    val determinantesModificados = mutableListOf<Double>()
                    for (i in 0 until matrix.size) {
                        val modMatrix = reemplazarColumna(matrix, constantsArray, i)
                        val detMod = calcularDeterminante(modMatrix)
                        determinantesModificados.add(detMod)
                        pasos.append("Matriz al reemplazar columna ${i + 1}:\n")
                        pasos.append(matrizToString(modMatrix))
                        pasos.append("\nDeterminante: $detMod\n\n")
                    }

                    // Soluciones
                    val soluciones = Array(systemSize) { i ->
                        determinantesModificados[i] / determinant
                    }

                    pasos.append("Soluciones finales:\n")
                    for (i in soluciones.indices) {
                        pasos.append("${'X' + i} = ${soluciones[i]}\n")
                    }
                }

                resultado.text = pasos.toString()
            } catch (e: Exception) {
                resultado.text = "Error: ${e.message}"
            }
        }
    }

    private fun areAllFieldsFilled(vararg editTexts: EditText): Boolean {
        return editTexts.all { it.text.isNotEmpty() }
    }

    private fun calcularDeterminante(matrix: Array<DoubleArray>): Double {
        if (matrix.size == 3) {
            // Determinante para una matriz 3x3
            return (matrix[0][0] * matrix[1][1] * matrix[2][2]
                    + matrix[0][1] * matrix[1][2] * matrix[2][0]
                    + matrix[0][2] * matrix[1][0] * matrix[2][1]
                    - matrix[0][2] * matrix[1][1] * matrix[2][0]
                    - matrix[0][0] * matrix[1][2] * matrix[2][1]
                    - matrix[0][1] * matrix[1][0] * matrix[2][2])
        } else if (matrix.size == 2) {
            // Determinante para una matriz 2x2
            return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1]
        }
        return 0.0
    }

    private fun reemplazarColumna(matrix: Array<DoubleArray>, constants: DoubleArray, colIndex: Int): Array<DoubleArray> {
        val newMatrix = Array(matrix.size) { matrix[it].clone() }
        for (i in constants.indices) {
            newMatrix[i][colIndex] = constants[i]
        }
        return newMatrix
    }

    private fun matrizToString(matrix: Array<DoubleArray>): String {
        return matrix.joinToString(separator = "\n") { row ->
            row.joinToString(prefix = "[", postfix = "]", separator = ", ")
        }
    }
}
