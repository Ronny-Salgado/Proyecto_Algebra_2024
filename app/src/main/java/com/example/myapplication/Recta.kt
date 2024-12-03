package com.example.myapplication

class Recta(
    val vectorial: String? = null,
    val parametrica: String? = null,
    val simetrica: String? = null
) {
    override fun toString(): String {
        return when {
            !vectorial.isNullOrEmpty() -> "Vectorial: $vectorial"
            !parametrica.isNullOrEmpty() -> formatParametric(parametrica)
            !simetrica.isNullOrEmpty() -> "Simétrica: $simetrica"
            else -> "Recta vacía"
        }
    }

    private fun formatParametric(parametrica: String): String {
        // Supongamos que la entrada paramétrica es algo como:
        // "x: 2 + 2t, y: 3 + 3t, z: 4 + 4t"
        val components = parametrica.split(",")
        val formattedComponents = components.joinToString(separator = "\n") {
            it.trim().removePrefix("x:").removePrefix("y:").removePrefix("z:").trim()
        }
        return "Paramétrica:\n$formattedComponents"
    }

}
