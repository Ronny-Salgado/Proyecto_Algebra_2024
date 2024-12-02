package com.example.myapplication

import java.util.ArrayList
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.min

class CalculosVectoresActivity {

    var listavectores: ArrayList<Vector> = ArrayList()
    var escalar: Int = 0

    // Función para mostrar los pasos de una operación
    fun mostrarPasos(pasos: List<String>): String {
        return pasos.joinToString("\n")
    }

    // Validación de índices para operaciones con dos vectores
    fun validacionIndices(vectoruno: Int, vectordos: Int): Boolean {
        if (vectoruno < 0 || vectoruno >= listavectores.size || vectordos < 0 || vectordos >= listavectores.size) {
            println("Índices inválidos para los vectores.")
            return false
        }
        return true
    }

    // Validación de índice para operaciones con un solo vector
    fun validacionIndice(indiceVector: Int): Boolean {
        if (indiceVector < 0 || indiceVector >= listavectores.size) {
            println("Índice inválido para el vector.")
            return false
        }
        return true
    }
    fun sumaVectores(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("\nV1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        val a = v1.a + v2.a
        val b = v1.b + v2.b
        val c = v1.c + v2.c

        pasos.add("Suma componente a: ${v1.a} + ${v2.a} = $a")
        pasos.add("Suma componente b: ${v1.b} + ${v2.b} = $b")
        pasos.add("Suma componente c: ${v1.c} + ${v2.c} = $c")

        val resultado = "Resultado de la suma: ($a, $b, $c)"
        pasos.add(resultado)

        return mostrarPasos(pasos)
    }
    fun restaVectores(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("\nV1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        val a = v1.a - v2.a
        val b = v1.b - v2.b
        val c = v1.c - v2.c

        pasos.add("Resta componente a: ${v1.a} - ${v2.a} = $a")
        pasos.add("Resta componente b: ${v1.b} - ${v2.b} = $b")
        pasos.add("Resta componente c: ${v1.c} - ${v2.c} = $c")

        val resultado = "Resultado de la resta: ($a, $b, $c)"
        pasos.add(resultado)

        return mostrarPasos(pasos)
    }

    // Operación de multiplicación punto a punto
    fun multiplicacionPuntoAPunto(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("\nV1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        // Multiplicación punto a punto
        val a = v1.a * v2.a
        val b = v1.b * v2.b
        val c = v1.c * v2.c

        pasos.add("Multiplicación componente a: ${v1.a} * ${v2.a} = $a")
        pasos.add("Multiplicación componente b: ${v1.b} * ${v2.b} = $b")
        pasos.add("Multiplicación componente c: ${v1.c} * ${v2.c} = $c")

        val resultado = "Resultado de la multiplicación punto a punto: ($a, $b, $c)"
        pasos.add(resultado)

        return mostrarPasos(pasos)
    }


    fun productoPunto(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("\nV1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        val resultado = (v1.a * v2.a) + (v1.b * v2.b) + (v1.c * v2.c)
        pasos.add("Producto punto: (${v1.a} * ${v2.a}) + (${v1.b} * ${v2.b}) + (${v1.c} * ${v2.c}) = $resultado")

        return mostrarPasos(pasos)
    }
    fun productoVectorial(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("\nV1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        val i = (v1.b * v2.c) - (v2.b * v1.c)
        val j = (v1.c * v2.a) - (v2.c * v1.a)
        val k = (v1.a * v2.b) - (v2.a * v1.b)

        pasos.add("Producto vectorial: ($i, $j, $k)")

        return mostrarPasos(pasos)
    }
    fun calcularNorma(indicevectoruno: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndice(indicevectoruno)) {
            return "Índice inválido para el vector."
        }

        val vector = listavectores[indicevectoruno]
        val a = vector.a
        val b = vector.b
        val c = vector.c

        pasos.add("\nV = (${vector.a}, ${vector.b}, ${vector.c})")

        val suma = (a * a + b * b + c * c).toDouble()
        pasos.add("Suma de los cuadrados: $a² + $b² + $c² = $suma")

        val norma = calcularRaizCuadrada(suma)
        pasos.add("Norma del vector: √$suma = $norma")

        return mostrarPasos(pasos)
    }
    fun calcularAngulo(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val productoPunto = productoPunto(indicevectoruno, indicevectordos)
        val normaV1 = calcularNorma(indicevectoruno).split("\n").last().split(" = ")[1].toDouble()
        val normaV2 = calcularNorma(indicevectordos).split("\n").last().split(" = ")[1].toDouble()

        pasos.add("Producto punto: $productoPunto")
        pasos.add("\nNorma de V1: $normaV1")
        pasos.add("Norma de V2: $normaV2")

        val cosTheta = productoPunto.split("\n").last().split(" = ")[1].toDouble() / (normaV1 * normaV2)

        // Ajustar el valor del coseno dentro del rango [-1, 1]
        val cosThetaAjustado = max(-1.0, min(1.0, cosTheta))

        val angulo = acos(cosThetaAjustado) * (180.0 / Math.PI)
        pasos.add("Ángulo entre V1 y V2: $angulo grados")

        return mostrarPasos(pasos)
    }
    fun calcularRaizCuadrada(numero: Double): Double {
        if (numero < 0) {
            throw IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo.")
        }
        var aproximacion = numero / 2
        val tolerancia = 0.00001

        // Iteración para calcular la raíz cuadrada con el método de aproximación
        while ((aproximacion * aproximacion - numero) > tolerancia || (numero - aproximacion * aproximacion) > tolerancia) {
            aproximacion = (aproximacion + numero / aproximacion) / 2
        }

        return aproximacion
    }
    // Operación para verificar si dos vectores son paralelos
    fun sonParalelos(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("V1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        // Calculando el producto vectorial
        val i = (v1.b * v2.c) - (v2.b * v1.c)
        val j = (v1.c * v2.a) - (v2.c * v1.a)
        val k = (v1.a * v2.b) - (v2.a * v1.b)

        pasos.add("Producto vectorial:")
        pasos.add("i = (${v1.b} * ${v2.c}) - (${v2.b} * ${v1.c}) = $i")
        pasos.add("j = (${v1.c} * ${v2.a}) - (${v2.c} * ${v1.a}) = $j")
        pasos.add("k = (${v1.a} * ${v2.b}) - (${v2.a} * ${v1.b}) = $k")

        // Si el producto vectorial es (0, 0, 0), los vectores son paralelos
        val sonParalelos = i.toDouble() == 0.0 && j.toDouble() == 0.0 && k.toDouble() == 0.0
        val resultado = if (sonParalelos) {
            "Los vectores son paralelos."
        } else {
            "Los vectores no son paralelos."
        }

        pasos.add(resultado)

        return mostrarPasos(pasos)
    }


    // Operación para verificar si dos vectores son perpendiculares
    fun sonPerpendiculares(indicevectoruno: Int, indicevectordos: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return "Índices inválidos para los vectores."
        }

        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]

        pasos.add("V1 = (${v1.a}, ${v1.b}, ${v1.c})")
        pasos.add("V2 = (${v2.a}, ${v2.b}, ${v2.c})")

        // Calculando el producto punto
        val productoPunto = (v1.a * v2.a) + (v1.b * v2.b) + (v1.c * v2.c)

        pasos.add("Producto punto:")
        pasos.add("(${v1.a} * ${v2.a}) + (${v1.b} * ${v2.b}) + (${v1.c} * ${v2.c}) = $productoPunto")

        // Si el producto punto es 0, los vectores son perpendiculares
        val resultado = if (productoPunto.toDouble() == 0.0) {
            "Los vectores son perpendiculares."
        } else {
            "Los vectores no son perpendiculares."
        }

        pasos.add(resultado)

        return mostrarPasos(pasos)
    }

    // Operación para multiplicar un vector por un escalar
    fun multiplicarPorEscalar(indicevector: Int): String {
        val pasos = mutableListOf<String>()

        if (!validacionIndice(indicevector)) {
            return "Índice inválido para el vector."
        }

        val vector = listavectores[indicevector]
        val a = vector.a * escalar
        val b = vector.b * escalar
        val c = vector.c * escalar

        pasos.add("V = (${vector.a}, ${vector.b}, ${vector.c})")
        pasos.add("Escalar = $escalar")

        pasos.add("Multiplicación componente a: ${vector.a} * $escalar = $a")
        pasos.add("Multiplicación componente b: ${vector.b} * $escalar = $b")
        pasos.add("Multiplicación componente c: ${vector.c} * $escalar = $c")

        val resultado = "Resultado de la multiplicación por escalar: ($a, $b, $c)"
        pasos.add(resultado)

        return mostrarPasos(pasos)
    }

}



