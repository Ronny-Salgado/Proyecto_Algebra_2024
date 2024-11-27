package com.example.myapplication
import java.util.ArrayList
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.min


class CalculosVectoresActivity {

    var listavectores: ArrayList<Vector> = ArrayList()
    var escalar: Int = 0

    fun validacionIndices(vectoruno: Int, vectordos: Int): Boolean {
        if (vectoruno < 0 || vectoruno >= listavectores.size || vectordos < 0 || vectordos >= listavectores.size) {
            println("Índices inválidos para los vectores.")
            return false
        }
        return true
    }

    fun validacionIndice(indiceVector: Int): Boolean {
        if (indiceVector < 0 || indiceVector >= listavectores.size) {
            println("Índice inválido para el vector.")
            return false
        }
        return true
    }

    fun sumaVectores(indicevectoruno: Int, indicevectordos: Int): Vector? {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return null
        }
        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]
        val a = v1.a + v2.a
        val b = v1.b + v2.b
        val c = v1.c + v2.c
        return Vector(a, b, c)
    }

    fun restaVectores(indicevectoruno: Int, indicevectordos: Int): Vector? {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return null
        }
        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]
        val a = v1.a - v2.a
        val b = v1.b - v2.b
        val c = v1.c - v2.c
        return Vector(a, b, c)
    }

    fun multiplicacionPuntoAPunto(indicevectoruno: Int, indicevectordos: Int): Vector? {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            return null
        }
        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]
        val a = v1.a * v2.a
        val b = v1.b * v2.b
        val c = v1.c * v2.c
        return Vector(a, b, c)
    }

    fun multiplicarPorEscalar(indiceVector: Int): Vector? {
        if (!validacionIndice(indiceVector)) {
            return null
        }
        val v = listavectores[indiceVector]
        val a = v.a * escalar
        val b = v.b * escalar
        val c = v.c * escalar
        return Vector(a, b, c)
    }

    fun productoPunto(indicevectoruno: Int, indicevectordos: Int): Int {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            throw IllegalArgumentException("Índices inválidos para los vectores.")
        }
        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]
        return (v1.a * v2.a) + (v1.b * v2.b) + (v1.c * v2.c)
    }

    fun productoVectorial(indicevectoruno: Int, indicevectordos: Int): Vector {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            throw IllegalArgumentException("Los índices proporcionados no son válidos.")
        }
        val v1 = listavectores[indicevectoruno]
        val v2 = listavectores[indicevectordos]
        val a1 = v1.a
        val b1 = v1.b
        val c1 = v1.c
        val a2 = v2.a
        val b2 = v2.b
        val c2 = v2.c
        val i = (b1 * c2) - (b2 * c1)
        val j = (c1 * a2) - (c2 * a1)
        val k = (a1 * b2) - (a2 * b1)
        return Vector(i, j, k)
    }

    fun calcularNorma(indicevectoruno: Int): Double {
        if (!validacionIndice(indicevectoruno)) {
            throw IllegalArgumentException("Índice inválido para el vector.")
        }

        val vector = listavectores[indicevectoruno]
        val a = vector.a
        val b = vector.b
        val c = vector.c

        val suma = (a * a + b * b + c * c).toDouble()

        return calcularRaizCuadrada(suma)
    }


    fun sonPerpendiculares(indicevectoruno: Int, indicevectordos: Int): Boolean {
        return productoPunto(indicevectoruno, indicevectordos) == 0
    }

    fun sonParalelos(indicevectoruno: Int, indicevectordos: Int): Boolean {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            throw IllegalArgumentException("Índices inválidos para los vectores.")
        }
        val productoCruz = productoVectorial(indicevectoruno, indicevectordos)
        return productoCruz.a == 0 && productoCruz.b == 0 && productoCruz.c == 0
    }

    fun calcularRaizCuadrada(numero: Double): Double {
        if (numero < 0) {
            throw IllegalArgumentException("No se puede calcular la raíz cuadrada de un número negativo.")
        }
        var aproximacion = numero / 2
        val tolerancia = 0.00001
        while ((aproximacion * aproximacion - numero) > tolerancia || (numero - aproximacion * aproximacion) > tolerancia) {
            aproximacion = (aproximacion + numero / aproximacion) / 2
        }
        return aproximacion
    }

    fun valorAbsoluto(numero: Int): Int {
        return if (numero < 0) -numero else numero
    }

    fun calcularAngulo(indicevectoruno: Int, indicevectordos: Int): Double {
        if (!validacionIndices(indicevectoruno, indicevectordos)) {
            throw IllegalArgumentException("Índices inválidos para los vectores.")
        }

        val productoPunto = productoPunto(indicevectoruno, indicevectordos)
        val normaV1 = calcularNorma(indicevectoruno)
        val normaV2 = calcularNorma(indicevectordos)

        // Calcular el coseno del ángulo
        var cosTheta = productoPunto / (normaV1 * normaV2)

        // Ajustar el valor del coseno dentro del rango [-1, 1]
        cosTheta = max(-1.0, min(1.0, cosTheta))

        // Calcular el ángulo en radianes usando acos
        return acos(cosTheta) * (180.0 / Math.PI) // Convertir el resultado a grados
    }

}

