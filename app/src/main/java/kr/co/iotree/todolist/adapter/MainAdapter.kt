package kr.co.iotree.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.iotree.todolist.databinding.ViewholderCalendarBinding
import kr.co.iotree.todolist.databinding.ViewholderTodoGroupBinding
import kr.co.iotree.todolist.databinding.ViewholderUserNameBinding
import kr.co.iotree.todolist.viewModel.CalendarViewModel
import kr.co.iotree.todolist.adapter.viewholder.CalendarViewHolder
import kr.co.iotree.todolist.adapter.viewholder.CalendarGroupViewHolder
import kr.co.iotree.todolist.adapter.viewholder.UserNameViewHolder

class MainAdapter(private val viewModel: CalendarViewModel, private var supportFragmentManager: FragmentManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var year = viewModel.year.value
    var month = viewModel.month.value
    var date = viewModel.date.value
    private var isMonth = true

    fun setDate(year: Int, month: Int, date: Int, isMonth: Boolean) {
        this.year = year
        this.month = month
        this.date = date
        this.isMonth = isMonth
        notifyItemRangeChanged(1, itemCount)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> USER_NAME
            1 -> CALENDAR
            else -> TODO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            USER_NAME -> return UserNameViewHolder(ViewholderUserNameBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CALENDAR -> return CalendarViewHolder(ViewholderCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
            TODO -> return CalendarGroupViewHolder(ViewholderTodoGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel, supportFragmentManager)
        }
        throw RuntimeException("Invalid ViewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            USER_NAME -> (holder as UserNameViewHolder).bindData()
            CALENDAR -> (holder as CalendarViewHolder).bindData(viewModel, isMonth)
            TODO -> (holder as CalendarGroupViewHolder).bindData(viewModel.allCalendarGroup.value!![position - 2], year!!, month!!, date!!)
        }
    }

    override fun getItemCount(): Int = (viewModel.allCalendarGroup.value?.size ?: 0) + 2

    companion object {
        const val USER_NAME = 1
        const val CALENDAR = 2
        const val TODO = 3
    }
}