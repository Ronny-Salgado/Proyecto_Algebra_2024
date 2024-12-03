package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RectasActivity : AppCompatActivity() {

    private val rectas = mutableListOf<Recta>()
    private lateinit var extraFieldsContainer: LinearLayout
    private lateinit var textViewResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rectas)

        // Inicializar vistas
        extraFieldsContainer = findViewById(R.id.extraFieldsContainer)
        textViewResultado = findViewById(R.id.textViewResultado)

        val btnVectorial: Button = findViewById(R.id.btnVectorial)
        val btnParametrica: Button = findViewById(R.id.btnParametrica)
        val btnSimetrica: Button = findViewById(R.id.btnSimetrica)
        val btnAgregar: Button = findViewById(R.id.btnAgregar)
        val btnEliminar: Button = findViewById(R.id.btnEliminar)
        val btnConsultar: Button = findViewById(R.id.btnConsultar)
        val btnConvertir: Button = findViewById(R.id.btnConvertir)

        // Botón "Paramétrica" - Añade campos dinámicos
        btnParametrica.setOnClickListener {
            extraFieldsContainer.removeAllViews()
            val editTextX = createEditText("Ecuación para X (ej: x = 1 + 4t)")
            val editTextY = createEditText("Ecuación para Y (ej: y = 2 + 5t)")
            val editTextZ = createEditText("Ecuación para Z (ej: z = 3 + 6t)")
            extraFieldsContainer.addView(editTextX)
            extraFieldsContainer.addView(editTextY)
            extraFieldsContainer.addView(editTextZ)
        }

        // Botón "Vectorial"
        btnVectorial.setOnClickListener {
            extraFieldsContainer.removeAllViews()
            val editTextVectorial = createEditText("Introduce recta en formato vectorial (ej: r: (1,2,3) + t(4,5,6))")
            extraFieldsContainer.addView(editTextVectorial)
        }

        // Botón "Simétrica"
        btnSimetrica.setOnClickListener {
            extraFieldsContainer.removeAllViews()
            val editTextSimetrica = createEditText("Introduce recta en formato simétrico (ej: (x - 1)/4 = (y - 2)/5 = (z - 3)/6)")
            extraFieldsContainer.addView(editTextSimetrica)
        }

        // Botón "Agregar"
        btnAgregar.setOnClickListener {
            val childCount = extraFieldsContainer.childCount
            val inputs = mutableListOf<String>()

            for (i in 0 until childCount) {
                val editText = extraFieldsContainer.getChildAt(i) as? EditText
                val inputText = editText?.text.toString()
                if (inputText.isEmpty()) {
                    mostrarResultado("Por favor, llena todos los campos.")
                    return@setOnClickListener
                }
                inputs.add(inputText)
            }

            when (inputs.size) {
                1 -> {
                    val recta = Recta(vectorial = inputs[0], parametrica = null, simetrica = null)
                    agregarRecta(recta)
                }
                3 -> {
                    val parametrica = "x: ${inputs[0]}, y: ${inputs[1]}, z: ${inputs[2]}"
                    val recta = Recta(vectorial = null, parametrica = parametrica, simetrica = null)
                    agregarRecta(recta)
                }
                else -> mostrarResultado("Formato no válido.")
            }

            mostrarResultado("Recta agregada.")
            extraFieldsContainer.removeAllViews()
        }

        // Botón "Eliminar Recta"
        btnEliminar.setOnClickListener {
            if (rectas.isNotEmpty()) {
                // Mostrar la lista de rectas con índices
                val listaRectas = rectas.mapIndexed { index, recta -> "${index + 1}. ${recta}" }
                    .joinToString(separator = "\n\n")
                mostrarResultado("Rectas almacenadas:\n\n$listaRectas\n\nIntroduce el número de la recta que deseas eliminar:")

                // Crear un EditText para que el usuario ingrese el índice
                val inputField = createEditText("Número de recta")
                extraFieldsContainer.removeAllViews()
                extraFieldsContainer.addView(inputField)

                // Botón para confirmar la eliminación
                val btnConfirmar = Button(this).apply {
                    text = "Confirmar eliminación"
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                }

                extraFieldsContainer.addView(btnConfirmar)

                btnConfirmar.setOnClickListener {
                    val inputText = inputField.text.toString()
                    if (inputText.isNotEmpty()) {
                        val indice = inputText.toIntOrNull()
                        if (indice != null && indice in 1..rectas.size) {
                            rectas.removeAt(indice - 1)
                            mostrarResultado("Recta eliminada correctamente.\n\nRectas restantes:\n\n${rectas.joinToString("\n\n")}")
                            extraFieldsContainer.removeAllViews()
                        } else {
                            mostrarResultado("Número inválido. Intenta de nuevo.")
                        }
                    } else {
                        mostrarResultado("Por favor, introduce un número válido.")
                    }
                }
            } else {
                mostrarResultado("No hay rectas para eliminar.")
            }
        }


        // Botón "Consultar Recta"
        btnConsultar.setOnClickListener {
            if (rectas.isNotEmpty()) {
                val listaRectas = rectas.mapIndexed { index, recta -> "${index + 1}. ${recta}" }
                    .joinToString(separator = "\n\n")
                mostrarResultado("Rectas almacenadas:\n\n$listaRectas\n\n")
            } else {
                mostrarResultado("No hay rectas almacenadas.")
            }
        }


        // Botón "Convertir Recta"
        btnConvertir.setOnClickListener {
            if (rectas.isEmpty()) {
                mostrarResultado("No hay rectas para convertir.")
                return@setOnClickListener
            }

            // Mostrar la lista de rectas con índices
            val listaRectas = rectas.mapIndexed { index, recta -> "${index + 1}. ${recta}" }
                .joinToString(separator = "\n\n")
            mostrarResultado("Rectas almacenadas:\n\n$listaRectas\n\nIntroduce el número de la recta que deseas convertir:")

            // Crear un EditText para que el usuario ingrese el índice
            val inputField = createEditText("Número de recta")
            extraFieldsContainer.removeAllViews()
            extraFieldsContainer.addView(inputField)

            // Botón para confirmar selección de recta
            val btnSeleccionarRecta = Button(this).apply {
                text = "Seleccionar Recta"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

            extraFieldsContainer.addView(btnSeleccionarRecta)

            btnSeleccionarRecta.setOnClickListener {
                val inputText = inputField.text.toString()
                val indice = inputText.toIntOrNull()
                if (indice != null && indice in 1..rectas.size) {
                    val rectaSeleccionada = rectas[indice - 1]

                    mostrarResultado("Seleccionaste la recta:\n\n${rectaSeleccionada}\n\nSelecciona a qué notación deseas convertir:")
                    extraFieldsContainer.removeAllViews()

                    // Botones para seleccionar la notación a convertir
                    val btnVectorialConvert = Button(this).apply {
                        text = "Convertir a Vectorial"
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }

                    val btnParametricaConvert = Button(this).apply {
                        text = "Convertir a Paramétrica"
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }

                    val btnSimetricaConvert = Button(this).apply {
                        text = "Convertir a Simétrica"
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }

                    // Agregar botones al contenedor
                    extraFieldsContainer.addView(btnVectorialConvert)
                    extraFieldsContainer.addView(btnParametricaConvert)
                    extraFieldsContainer.addView(btnSimetricaConvert)

                    // Funcionalidad para cada conversión
                    btnVectorialConvert.setOnClickListener {
                        val resultado = convertirARecta("vectorial", rectaSeleccionada)
                        mostrarResultado("Resultado de la conversión:\n\n$resultado")
                        extraFieldsContainer.removeAllViews()
                    }

                    btnParametricaConvert.setOnClickListener {
                        val resultado = convertirARecta("paramétrica", rectaSeleccionada)
                        mostrarResultado("Resultado de la conversión:\n\n$resultado")
                        extraFieldsContainer.removeAllViews()
                    }

                    btnSimetricaConvert.setOnClickListener {
                        val resultado = convertirARecta("simétrica", rectaSeleccionada)
                        mostrarResultado("Resultado de la conversión:\n\n$resultado")
                        extraFieldsContainer.removeAllViews()
                    }
                } else {
                    mostrarResultado("Número inválido. Intenta de nuevo.")
                }
            }
        }

    }


    private fun convertirARecta(tipo: String, recta: Recta): String {
        return when (tipo) {
            "vectorial" -> {
                if (!recta.vectorial.isNullOrEmpty()) {
                    "Ya está en notación vectorial."
                } else if (!recta.parametrica.isNullOrEmpty()) {
                    parametricaAVectorialPasos(recta.parametrica)
                } else if (!recta.simetrica.isNullOrEmpty()) {
                    SimetricaAVectorialPasos(recta.simetrica)
                } else {
                    "No se puede convertir esta recta."
                }
            }

            "paramétrica" -> {
                if (!recta.parametrica.isNullOrEmpty()) {
                    "Ya está en notación paramétrica."
                } else if (!recta.vectorial.isNullOrEmpty()) {
                    vectorialAParametricaPasos(recta.vectorial)
                } else if (!recta.simetrica.isNullOrEmpty()) {
                    simetricaAParametricaPasos(recta.simetrica)
                } else {
                    "No se puede convertir esta recta."
                }
            }

            "simétrica" -> {
                if (!recta.simetrica.isNullOrEmpty()) {
                    "Ya está en notación simétrica."
                } else if (!recta.vectorial.isNullOrEmpty()) {
                    vectorialASimetricaPasos(recta.vectorial)
                } else if (!recta.parametrica.isNullOrEmpty()) {
                    parametricaASimetricaPasos(recta.parametrica)
                } else {
                    "No se puede convertir esta recta."
                }
            }

            else -> "Tipo de conversión no válido."
        }
    }


    private fun vectorialAParametricaPasos(vectorial: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Vectorial a Paramétrica ===\n")

            // Paso 1: Separar el punto inicial y el vector de dirección
            pasos.append("Paso 1: Separar el punto inicial y el vector de dirección.\n")
            val parts = vectorial.split("+ t(")
            val puntoInicial = parts[0].replace("r: ", "").replace("(", "").replace(")", "").trim()
            val direccion = parts[1].replace(")", "").trim()
            pasos.append("Punto inicial: $puntoInicial\n")
            pasos.append("Vector de dirección: $direccion\n")

            // Paso 2: Dividir los componentes del punto inicial y del vector de dirección
            pasos.append("Paso 2: Dividir los componentes del punto inicial y del vector de dirección.\n")
            val p = puntoInicial.split(",")
            val d = direccion.split(",")
            pasos.append("Componentes del punto inicial: x0=${p[0]}, y0=${p[1]}, z0=${p[2]}\n")
            pasos.append("Componentes del vector de dirección: a=${d[0]}, b=${d[1]}, c=${d[2]}\n")

            // Paso 3: Construir la ecuación paramétrica
            pasos.append("Paso 3: Construir la ecuación paramétrica.\n")
            pasos.append("Resultado:\n")
            pasos.append("x = ${p[0]} + ${d[0]}t\n")
            pasos.append("y = ${p[1]} + ${d[1]}t\n")
            pasos.append("z = ${p[2]} + ${d[2]}t\n")

            pasos.toString()
        } catch (e: Exception) {
            "Error en el formato de entrada para la notación vectorial."
        }
    }

    private fun vectorialASimetricaPasos(vectorial: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Vectorial a Simétrica ===\n")

            // Paso 1: Separar el punto inicial y el vector de dirección
            pasos.append("Paso 1: Separar el punto inicial y el vector de dirección.\n")
            val parts = vectorial.split("+ t(")
            val puntoInicial = parts[0].replace("r: ", "").replace("(", "").replace(")", "").trim()
            val direccion = parts[1].replace(")", "").trim()
            pasos.append("Punto inicial: $puntoInicial\n")
            pasos.append("Vector de dirección: $direccion\n")

            // Paso 2: Dividir los componentes del punto inicial y del vector de dirección
            pasos.append("Paso 2: Dividir los componentes del punto inicial y del vector de dirección.\n")
            val p = puntoInicial.split(",")
            val d = direccion.split(",")
            pasos.append("Componentes del punto inicial: x0=${p[0]}, y0=${p[1]}, z0=${p[2]}\n")
            pasos.append("Componentes del vector de dirección: a=${d[0]}, b=${d[1]}, c=${d[2]}\n")

            // Paso 3: Construir la ecuación simétrica
            pasos.append("Paso 3: Construir la ecuación simétrica.\n")
            pasos.append("Resultado:\n")
            pasos.append("(x - ${p[0]}) / ${d[0]} = ")
            pasos.append("(y - ${p[1]}) / ${d[1]} = ")
            pasos.append("(z - ${p[2]}) / ${d[2]}\n")

            pasos.toString()
        } catch (e: Exception) {
            "Error en el formato de entrada para la notación vectorial."
        }
    }

    private fun parametricaAVectorialPasos(parametrica: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Paramétrica a Vectorial ===\n")

            // Paso 1: Separar las ecuaciones de cada coordenada
            pasos.append("Paso 1: Separar las ecuaciones de cada coordenada.\n")
            val lines = parametrica.split("\n")
            val xLine = lines[0].replace("x = ", "").trim()
            val yLine = lines[1].replace("y = ", "").trim()
            val zLine = lines[2].replace("z = ", "").trim()
            pasos.append("Ecuación x: $xLine\n")
            pasos.append("Ecuación y: $yLine\n")
            pasos.append("Ecuación z: $zLine\n")

            // Paso 2: Extraer el punto inicial y el vector de dirección
            pasos.append("Paso 2: Extraer el punto inicial y el vector de dirección.\n")
            val xParts = xLine.split("+")
            val yParts = yLine.split("+")
            val zParts = zLine.split("+")

            val puntoInicial = "(${xParts[0].trim()}, ${yParts[0].trim()}, ${zParts[0].trim()})"
            val direccion = "(${xParts[1].replace("t", "").trim()}, ${yParts[1].replace("t", "").trim()}, ${zParts[1].replace("t", "").trim()})"
            pasos.append("Punto inicial: $puntoInicial\n")
            pasos.append("Vector de dirección: $direccion\n")

            // Paso 3: Construir la notación vectorial
            pasos.append("Paso 3: Construir la notación vectorial.\n")
            pasos.append("Resultado:\n")
            pasos.append("r: $puntoInicial + t$direccion\n")

            pasos.toString()
        } catch (e: Exception) {
            "Error en el formato de entrada para la notación paramétrica."
        }
    }

    private fun parametricaASimetricaPasos(parametrica: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Paramétrica a Simétrica ===\n")

            // Paso 1: Separar las ecuaciones de cada coordenada
            pasos.append("Paso 1: Separar las ecuaciones de cada coordenada.\n")
            val lines = parametrica.split("\n")
            val xLine = lines[0].replace("x = ", "").trim()
            val yLine = lines[1].replace("y = ", "").trim()
            val zLine = lines[2].replace("z = ", "").trim()
            pasos.append("Ecuación x: $xLine\n")
            pasos.append("Ecuación y: $yLine\n")
            pasos.append("Ecuación z: $zLine\n")

            // Paso 2: Extraer el punto inicial y el vector de dirección
            pasos.append("Paso 2: Extraer el punto inicial y el vector de dirección.\n")
            val xParts = xLine.split("+")
            val yParts = yLine.split("+")
            val zParts = zLine.split("+")

            val x0 = xParts[0].trim()
            val y0 = yParts[0].trim()
            val z0 = zParts[0].trim()
            val a = xParts[1].replace("t", "").trim()
            val b = yParts[1].replace("t", "").trim()
            val c = zParts[1].replace("t", "").trim()

            pasos.append("Punto inicial: x0=$x0, y0=$y0, z0=$z0\n")
            pasos.append("Vector de dirección: a=$a, b=$b, c=$c\n")

            // Paso 3: Construir la ecuación simétrica
            pasos.append("Paso 3: Construir la ecuación simétrica.\n")
            pasos.append("Resultado:\n")
            pasos.append("(x - $x0) / $a = ")
            pasos.append("(y - $y0) / $b = ")
            pasos.append("(z - $z0) / $c\n")

            pasos.toString()
        } catch (e: Exception) {
            "Error en el formato de entrada para la notación paramétrica."
        }
    }

    private fun simetricaAParametricaPasos(simetrica: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Simétrica a Paramétrica ===\n")

            // Paso 1: Convertir a notación vectorial
            pasos.append("Paso 1: Convertir a notación vectorial.\n")
            val vectorial = SimetricaAVectorialPasos(simetrica)
            pasos.append(vectorial).append("\n")

            // Paso 2: Convertir de vectorial a paramétrica
            pasos.append("Paso 2: Convertir de notación vectorial a paramétrica.\n")
            val parametrica = vectorialAParametricaPasos(vectorial)
            pasos.append(parametrica)

            pasos.toString()
        } catch (e: Exception) {
            "Error al convertir de simétrica a paramétrica."
        }
    }


    private fun SimetricaAVectorialPasos(simetrica: String): String {
        return try {
            val pasos = StringBuilder()
            pasos.append("=== Conversión de Notación Simétrica a Vectorial ===\n")

            // Paso 1: Separar las partes de la ecuación simétrica
            pasos.append("Paso 1: Separar las partes de la ecuación simétrica.\n")
            val parts = simetrica.split("=")
            val xPart = parts[0].replace("(x - ", "").replace(")", "").trim()
            val yPart = parts[1].replace("(y - ", "").replace(")", "").trim()
            val zPart = parts[2].replace("(z - ", "").replace(")", "").trim()
            pasos.append("Parte x: $xPart\n")
            pasos.append("Parte y: $yPart\n")
            pasos.append("Parte z: $zPart\n")

            // Paso 2: Extraer el punto inicial y el vector de dirección
            pasos.append("Paso 2: Extraer el punto inicial y el vector de dirección.\n")
            val xParts = xPart.split("/")
            val yParts = yPart.split("/")
            val zParts = zPart.split("/")

            val x0 = xParts[0].trim()
            val y0 = yParts[0].trim()
            val z0 = zParts[0].trim()
            val a = xParts[1].trim()
            val b = yParts[1].trim()
            val c = zParts[1].trim()

            pasos.append("Punto inicial: x0=$x0, y0=$y0, z0=$z0\n")
            pasos.append("Vector de dirección: a=$a, b=$b, c=$c\n")

            // Paso 3: Construir la notación vectorial
            pasos.append("Paso 3: Construir la notación vectorial.\n")
            val puntoInicial = "($x0, $y0, $z0)"
            val direccion = "($a, $b, $c)"
            pasos.append("Resultado:\n")
            pasos.append("r: $puntoInicial + t$direccion\n")

            pasos.toString()
        } catch (e: Exception) {
            "Error en el formato de entrada para la notación simétrica."
        }
    }



    private fun createEditText(hint: String): EditText {
        return EditText(this).apply {
            this.hint = hint
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun agregarRecta(recta: Recta) {
        rectas.add(recta)
    }

    private fun mostrarResultado(resultado: String) {
        textViewResultado.text = resultado
    }
}
