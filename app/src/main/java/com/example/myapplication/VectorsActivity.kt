package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class VectorsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var vectorAdapter: VectorAdapter
    private val calculosVectores = CalculosVectoresActivity()
    private val selectedIndices = mutableListOf<Int>() // GUARDARA INDICES SELECIONADOS

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

        // Configura botones de operaciones
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




        btnSum.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sumaVectores(selectedIndices[0], selectedIndices[1])
                showResult("Suma", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnRest.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.restaVectores(selectedIndices[0], selectedIndices[1])
                showResult("Resta", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnMultiply.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.multiplicacionPuntoAPunto(selectedIndices[0], selectedIndices[1])
                showResult("Multiplicación Punto a Punto", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnPunto.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.productoPunto(selectedIndices[0], selectedIndices[1])
                showResult("Producto Punto", result.toString())
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnVectorial.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.productoVectorial(selectedIndices[0], selectedIndices[1])
                showResult("Producto Vectorial", result)
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnNorma.setOnClickListener {
            if (selectedIndices.size == 1) {
                val result = calculosVectores.calcularNorma(selectedIndices[0])
                showResult("Norma del Vector", result.toString())
            } else {
                Toast.makeText(this, "Selecciona exactamente 1 vector.", Toast.LENGTH_SHORT).show()
            }
        }

        btnAngulo.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.calcularAngulo(selectedIndices[0], selectedIndices[1])
                showResult("Ángulo entre Vectores", "$result°")
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnParalelo.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sonParalelos(selectedIndices[0], selectedIndices[1])
                showResult("¿Son Paralelos?", if (result) "Sí" else "No")
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

        btnPerpendicular.setOnClickListener {
            if (selectedIndices.size == 2) {
                val result = calculosVectores.sonPerpendiculares(selectedIndices[0], selectedIndices[1])
                showResult("¿Son Perpendiculares?", if (result) "Sí" else "No")
            } else {
                Toast.makeText(this, "Selecciona exactamente 2 vectores.", Toast.LENGTH_SHORT).show()
            }
        }

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
    private fun showResult(operation: String, result: Any?) {
        when (result) {
            is Vector -> {
                Toast.makeText(this, "$operation: (${result.a}, ${result.b}, ${result.c})", Toast.LENGTH_LONG).show()
            }
            is String -> {
                Toast.makeText(this, "$operation: $result", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Error al realizar $operation.", Toast.LENGTH_SHORT).show()
            }
        }
    }









}
