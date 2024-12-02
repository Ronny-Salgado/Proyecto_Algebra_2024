package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Botón para operaciones con matrices: Sume, Resta , multiplicación y inversa
        val btnMatrixOperations1 = findViewById<Button>(R.id.btnSumResMulMatrices)
        btnMatrixOperations1.setOnClickListener {
            val intent = Intent(this, SRMmatricesActivity::class.java)
            startActivity(intent)
        }

        // Botón para resolver sistemas de ecuaciones
        val btnSolveEquations = findViewById<Button>(R.id.btnEcuaciones)
        btnSolveEquations.setOnClickListener {
            val intent = Intent(this, SEcuacionesActivity::class.java)
            startActivity(intent)
        }

//        // Botón para operaciones con vectores
        val btnVectorOperations = findViewById<Button>(R.id.btnVectorOperations)
        btnVectorOperations.setOnClickListener {
        val intent = Intent(this, VectorsActivity::class.java)
          startActivity(intent)
        }
//
//        // Botón para operaciones con rectas
//        val btnLineOperations = findViewById<Button>(R.id.btnLineOperations)
//        btnLineOperations.setOnClickListener {
//            val intent = Intent(this, LineOperationsActivity::class.java)
//            startActivity(intent)
//        }
//
//
    }
}
