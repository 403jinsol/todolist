package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderColorBinding
import kr.co.iotree.todolist.util.GroupColor
import kr.co.iotree.todolist.viewModel.GroupInfoViewModel
import kr.co.iotree.todolist.viewholder.ColorViewHolder

class ColorAdapter(private val viewModel: GroupInfoViewModel) : RecyclerView.Adapter<ColorViewHolder>() {
    private val list = GroupColor.values()
    private var color = viewModel.color.value

    fun setColor(color: Int) {
        this.color = color
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(ViewholderColorBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bindData(viewModel, list[position])
    }

    override fun getItemCount(): Int = list.size
}