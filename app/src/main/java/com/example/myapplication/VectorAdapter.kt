package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class VectorAdapter(
    private val vectors: ArrayList<Vector>,
    private val onCheckBoxChanged: (List<Int>) -> Unit,
    private val onDeleteClicked: (Int) -> Unit
) : RecyclerView.Adapter<VectorAdapter.VectorViewHolder>() {

    private val selectedIndices = mutableSetOf<Int>() // Almacena índices seleccionados

    class VectorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vectorTextView: TextView = itemView.findViewById(R.id.tvVector)
        val checkBox: CheckBox = itemView.findViewById(R.id.cbSelectVector)
        val deleteButton: Button = itemView.findViewById(R.id.btnDeleteVector)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VectorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vector, parent, false)
        return VectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: VectorViewHolder, position: Int) {
        val vector = vectors[position]
        holder.vectorTextView.text = "Vector ${position + 1}: (${vector.a}, ${vector.b}, ${vector.c})"


        // Configura el CheckBox
        holder.checkBox.isChecked = selectedIndices.contains(position)
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedIndices.add(position)
            } else {
                selectedIndices.remove(position)
            }
            onCheckBoxChanged(selectedIndices.toList())
        }

        // Configura el botón de eliminar
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return vectors.size
    }
}
