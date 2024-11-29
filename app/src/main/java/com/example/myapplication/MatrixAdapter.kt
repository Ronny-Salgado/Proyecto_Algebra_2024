package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MatrixAdapter(private val matrices: MutableList<Array<Array<Int>>>) : RecyclerView.Adapter<MatrixAdapter.MatrixViewHolder>() {

    private val selectedMatrices = mutableListOf<Array<Array<Int>>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatrixViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_matrix, parent, false)
        return MatrixViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MatrixViewHolder, position: Int) {
        val matrix = matrices[position]
        holder.tvMatrix.text = formatMatrix(matrix)
        holder.btnDeleteMatrix.setOnClickListener {
            matrices.removeAt(position)
            notifyDataSetChanged()
        }
        holder.cbSelectMatrix.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                selectedMatrices.add(matrix)
            } else {
                selectedMatrices.remove(matrix)
            }
        }
    }

    override fun getItemCount() = matrices.size

    fun getSelectedMatrices() = selectedMatrices

    private fun formatMatrix(matrix: Array<Array<Int>>): String {
        return matrix.joinToString("\n") { row -> row.joinToString(", ") }
    }

    class MatrixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMatrix: TextView = itemView.findViewById(R.id.tvMatrix)
        val btnDeleteMatrix: Button = itemView.findViewById(R.id.btnDeleteMatrix)
        val cbSelectMatrix: CheckBox = itemView.findViewById(R.id.cbSelectMatrix)
    }
}
