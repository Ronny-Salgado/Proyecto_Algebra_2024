package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VectorsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vectorAdapter: VectorAdapter
    private lateinit var tvResultado: TextView  // TextView para mostrar resultados detallados
    private val calculosVectores = CalculosVectoresActivity()
    private val selectedIndices = mutableListOf<Int>() // Guarda los índices seleccionados

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opvectores)

        // Inicializa el RecyclerView
        recyclerView = findViewById(R.id.rvVectors)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicializa el adaptador con callback para selección y eliminación
        vectorAdapter = VectorAdapter(
            calculosVectores.listavectores,
            { indices -> // Callback para selección de CheckBox
                selectedIndices.clear()
                selectedIndices.addAll(indices)
            },
            { position -> // Callback para eliminación de vectores
                calculosVectores.listavectores.removeAt(position)
                vectorAdapter.notifyDataSetChanged()
            }
        )
        recyclerView.adapter = vectorAdapter

        // Configura el TextView para mostrar los resultados detallados
        tvResultado = findViewById(R.id.tvResultado)

        // Configura el botón para agregar vectores
        val btnAddVector: Button = findViewById(R.id.btnAddVector)
        val etX: EditText = findViewById(R.id.etX)
        val etY: EditText = findViewById(R.id.etY)
        val etZ: EditText = findViewById(R.id.etZ)

        btnAddVector.setOnClickListener {
            val x = etX.text.toString().toIntOrNull()
            val y = etY.text.toString().toIntOrNull()
            val z = etZ.text.toString().toIntOrNull()

            if (x != null && y != null && z != null) {
                calculosVectores.listavectores.add(Vector(x, y, z))
                vectorAdapter.notifyDataSetChanged()
                etX.text.clear()
                etY.text.clear()
                etZ.text.clear()
            } else {
                Toast.makeText(this, "Por favor, introduce valores válidos.", Toast.LENGTH_SHORT).show()
            }
        }

        // Llama a la función para configurar los botones de operaciones
        setupOperationButtons()
    }

    private fun setupOperationButtons() {
        val btnSum: Button = findViewById(R.id.btnSum)
        val btnRest: Button = findViewById(R.id.btnRest)
        val btnMultiply: Button = findViewById(R.id.btnMultiply)
        val btnEscalar: Button = findViewById(R.id.btnEscalar)
        val btnPunto: Button = findViewById(R.id.btnProductoPunto)
        val btnVectorial: Button = findViewById(R.id.btnVectorial)
        val btnNorma: Button = findViewById(R.id.btnNorma)
        val btnAngulo: Button = findViewById(R.id.btnAngulo)
        val btnParalelo: Button = findViewById(R.id.btnParalelo)
        val btnPerpendicular: Button = findViewById(R.id.btnPerpendicular)

        // Operación de suma de vectores
        btnSum.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sumaVectores(selectedIndices[0], selectedIndices[1])
                showResult("Suma", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de resta de vectores
        btnRest.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.restaVectores(selectedIndices[0], selectedIndices[1])
                showResult("Resta", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de multiplicación punto a punto
        btnMultiply.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.multiplicacionPuntoAPunto(selectedIndices[0], selectedIndices[1])
                showResult("Multiplicación Punto a Punto", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de producto punto
        btnPunto.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.productoPunto(selectedIndices[0], selectedIndices[1])
                showResult("Producto Punto", result.toString())
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de producto vectorial
        btnVectorial.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.productoVectorial(selectedIndices[0], selectedIndices[1])
                showResult("Producto Vectorial", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de cálculo de norma de un vector
        btnNorma.setOnClickListener {
            if (selectedIndices.size == 1) {
                val result = calculosVectores.calcularNorma(selectedIndices[0])
                showResult("Norma del Vector", result.toString())
            } else {
                Toast.makeText(this, "Selecciona exactamente 1 vector.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación de cálculo de ángulo entre dos vectores
        btnAngulo.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.calcularAngulo(selectedIndices[0], selectedIndices[1])
                showResult("Ángulo entre Vectores", "$result°")
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        // Operación para verificar si dos vectores son paralelos
        btnParalelo.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sonParalelos(selectedIndices[0], selectedIndices[1])
                showResult("¿Son Paralelos?", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

// Operación para verificar si dos vectores son perpendiculares
        btnPerpendicular.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sonPerpendiculares(selectedIndices[0], selectedIndices[1])
                showResult("¿Son Perpendiculares?", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }


        // Operación para multiplicar un vector por un escalar
        btnEscalar.setOnClickListener {
            val etX: EditText = findViewById(R.id.campo) // Asegúrate de que "campo" sea el ID correcto
            val escalare = etX.text.toString().toIntOrNull()

            if (escalare != null) {
                calculosVectores.escalar = escalare // Asigna el valor a la variable 'escalar' dentro de tu lógica

                if (selectedIndices.size == 1) {
                    val result = calculosVectores.multiplicarPorEscalar(selectedIndices[0])
                    showResult("Multiplicación por Escalar", result)
                } else {
                    Toast.makeText(this, "Selecciona exactamente 1 vector.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, introduce valores válidos.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para mostrar el resultado en el TextView con detalle
    private fun showResult(operation: String, result: Any?) {
        when (result) {
            is Vector -> {
                // Aquí puedes mostrar más detalles sobre el resultado
                tvResultado.text = "$operation: Vector(${result.a}, ${result.b}, ${result.c})"
            }
            is String -> {
                tvResultado.text = "$operation: $result"
            }
            else -> {
                tvResultado.text = "Error al realizar $operation."
            }
        }
    }
}

