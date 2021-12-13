package kr.co.iotree.todolist.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderColorBinding
import kr.co.iotree.todolist.util.GroupColor
import kr.co.iotree.todolist.viewModel.GroupInfoViewModel

class ColorViewHolder(private val binding: ViewholderColorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bindData(viewModel: GroupInfoViewModel, color: GroupColor) {
        itemView.setOnClickListener {
            viewModel.color.value = color.color
        }

        binding.colorIcon.setColorFilter(color.color)
        binding.colorIcon.tag = color.color

        binding.iconLine.visibility = View.INVISIBLE

        if (viewModel.color.value == binding.colorIcon.tag) {
            binding.iconLine.visibility = View.VISIBLE
        }
    }
}