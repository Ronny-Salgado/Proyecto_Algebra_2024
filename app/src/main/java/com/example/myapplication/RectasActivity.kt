package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RectasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rectas)

        val entradaRecta: EditText = findViewById(R.id.entradaRecta)
        val botonConvertir: Button = findViewById(R.id.botonConvertir)
        val resultado: TextView = findViewById(R.id.resultado)

        botonConvertir.setOnClickListener {
            val recta = entradaRecta.text.toString().trim()
            val formato = detectarFormato(recta)

            when (formato) {
                "vectorial" -> resultado.text = convertirDeVectorial(recta)
                "paramétrica" -> resultado.text = convertirDeParametrica(recta)
                "simétrica" -> resultado.text = convertirDeSimetrica(recta)
                else -> resultado.text = "Formato no reconocido. Por favor, introduce una recta válida."
            }
        }
    }

    private fun detectarFormato(recta: String): String {
        val vectorialRegex = """r:\s*\(([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+)\)\s*\+\s*t\(([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+)\)""".toRegex()
        val parametricaRegex = """x\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t,\s*y\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t,\s*z\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t""".toRegex()
        val simetricaRegex = """\(\s*x\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)\s*=\s*\(\s*y\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)\s*=\s*\(\s*z\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)""".toRegex()

        return when {
            recta.matches(vectorialRegex) -> "vectorial"
            recta.matches(parametricaRegex) -> "paramétrica"
            recta.matches(simetricaRegex) -> "simétrica"
            else -> "indefinido"
        }
    }

    private fun convertirDeVectorial(vectorial: String): String {
        val regex = """r:\s*\(([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+)\)\s*\+\s*t\(([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+),\s*([-+]?[0-9]*\.?[0-9]+)\)""".toRegex()
        val match = regex.find(vectorial)
        return if (match != null) {
            val operacion = 0
            val (x0, y0, z0, a, b, c) = match.destructured

            var paso = "Paso 1: gualar a , y , z cada posición del punto \n" +
                    "x = $x0 \ny = $y0 \nz = $z0 \n"

            paso += "Paso 2: multiplicar t por cada posición del vector \n" +
                    "<$a t, $b t, $c t> \n"

            paso +=  "\nPaso 3: conctruimos las parametricas\n" +
                    "x = $x0 + $a t \ny = $y0 + $b t \nz = $z0 + $c t \n"

            val termX = invertirSigno(x0, operacion) + ""
            val termY = invertirSigno(y0, operacion) + ""
            val termZ = invertirSigno(z0, operacion) + ""

            paso +=  "\nPaso 4: pasamos el numero al otro lado del = con su signo invertido\n" +
                    "x $termX = $a t \ny $termY = $b t \nz $termZ = $c t \n"

            paso +=  "\nPaso 5: pasamos los que multiplican a dividir al otro lado\n" +
                    "(x $termX) / $a = t \n(y $termY) / $b = t \n(z $termZ) / $c = t \n"

            paso +=  "\nPaso 6: construimos las simetricas\n" +
                    "(x $termX) / $a = (y $termY) / $b = (z $termZ) / $c "

            "$paso"
        } else {
            "Formato vectorial no válido."
        }
    }

    private fun convertirDeParametrica(parametrica: String): String {
        val regex = """x\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t,\s*y\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t,\s*z\s*=\s*([-+]?\d+)\s*([+-]?)\s*(\d+)t""".toRegex()
        val match = regex.find(parametrica)
        return if (match != null) {
            val operacion = 0
            val (x0, signoX, a, y0, signoY, b, z0, signoZ, c) = match.destructured

            // Aquí eliminamos el signo "+" en los coeficientes positivos y mantenemos el negativo
            val termX = if (signoX == "+") "$a" else "$signoX$a"
            val termY = if (signoY == "+") "$b" else "$signoY$b"
            val termZ = if (signoZ == "+") "$c" else "$signoZ$c"

            var paso = "Paso 1: construir la vectorial, los numero solos son el punto los que multiplican a t el vector: \n" +
                    "\nr: ($x0, $y0, $z0) + t<$termX, $termY, $termZ>\n"

            val termXp = invertirSigno(x0, operacion) + ""
            val termYp = invertirSigno(y0, operacion) + ""
            val termZp = invertirSigno(z0, operacion) + ""

            paso +=  "\nPaso 2: tomando las parametricas originales pasamos el numero al otro lado del = con su signo invertido\n" +
                    "x $termXp = $a t \ny $termYp = $b t \nz $termZp = $c t \n"

            paso +=  "\nPaso 3: pasamos los que multiplican a dividir al otro lado\n" +
                    "(x $termXp) / $a = t \n(y $termYp) / $b = t \n(z $termZp) / $c = t \n"

            paso +=  "\nPaso 4: construimos las simetricas\n" +
                    "(x $termXp) / $a = (y $termYp) / $b = (z $termZp) / $c "

            "$paso"
        } else {
            "Formato paramétrico no válido."
        }
    }

    private fun convertirDeSimetrica(simetrica: String): String {
        val regex = """\(\s*x\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)\s*=\s*\(\s*y\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)\s*=\s*\(\s*z\s*([+-]?\s*\d+)\)\s*/\s*([+-]?\s*\d+)""".toRegex()
        val match = regex.find(simetrica)

        return if (match != null) {
            val operacion = 1
            val (x0, a, y0, b, z0, c) = match.destructured

            var paso = "Paso 1: Igualamos a t:\n" +
                    " (x $x0) / $a = t\n (y $y0) / $b = t\n (z $z0) / $c = t\n"

            paso += "\nPaso 2: enviamos los divisores al otro lado del = a multiplicar t:\n" +
                    "  x $x0 = $a t\n  y $y0 = $b t\n  z $z0 = $c t\n"

            val termX = invertirSigno(x0, operacion) + " +  $a t"
            val termY = invertirSigno(y0, operacion) + " +  $b t"
            val termZ = invertirSigno(z0, operacion) + " +  $c t"
            paso += "\nPaso 3: Enviamos los numero al otro lado con su signo invertido:\n" +
                    "x = $termX\ny = $termY\nz = $termZ \n"

            val termXv = invertirSigno(x0, operacion) + ""
            val termYv = invertirSigno(y0, operacion) + ""
            val termZv = invertirSigno(z0, operacion) + ""
            paso += "\nPaso 3: Creamos la ecuación vectorial los nuemros solos seran el punto, los que multiplican a t seran el vector:\n" +
                    "r: ($termXv, $termYv, $termZv) + t<$a, $b, $c>"


            "$paso"
        } else {
            "Formato simétrico no válido."
        }
    }


    private fun invertirSigno(valor: String, operacion: Int): String {
        // Eliminamos cualquier signo (+ o -) al principio
        val valorSinSigno = valor.removePrefix("+").removePrefix("-")

        return if (operacion == 0) {
            // Si la operación es 0, invertimos el signo
            if (valor.startsWith("-")) {
                "+$valorSinSigno" // Elimina el signo negativo
            } else {
                "-$valorSinSigno" // Agrega el signo negativo
            }
        } else {
            return if (valor.startsWith("-")) {
                valorSinSigno // Elimina el signo negativo
            } else {
                "-$valorSinSigno" // Agrega el signo negativo
            }
        }
    }


}
