package kr.co.iotree.todolist.activity

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.MainAdapter
import kr.co.iotree.todolist.adapter.TodoTestAdapter
import kr.co.iotree.todolist.database.Todo
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.databinding.ActivityMainBinding
import kr.co.iotree.todolist.util.dpToPx
import kr.co.iotree.todolist.vo.TodoGroupVo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var groupList: List<TodoGroupVo>
    private val groupTitleViews = arrayListOf<TextView>() // 정답 순서대로 TextView가 들어있다.

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyyMMdd")
        val currentDate = sdf.format (date)

        val todoGroup1 = TodoGroupVo("일반", "#ff0000ff")
        val todoGroup2 = TodoGroupVo("일반2", "#ffff0000")

        groupList = mutableListOf(todoGroup1, todoGroup2)

        binding.recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerview.adapter = MainAdapter(groupList as MutableList<TodoGroupVo>)
        (binding.recyclerview.adapter as MainAdapter).notifyItemInserted(groupList.size)

        setDrawerMenu()
    }

    private fun setDrawerMenu() {
        binding.menu.setOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        for (group in groupList) {
            val textView = TextView(this)
            textView.text = group.title
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.parseColor(group.color))
            textView.setPadding(dpToPx(this, 15.0f).toInt(), 0, dpToPx(this, 15.0f).toInt(), dpToPx(this, 1.0f).toInt())
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.todo_item_font_size))
            textView.setBackgroundResource(R.color.todo_group_background)

            groupTitleViews.add(textView)
        }

        @Suppress("UNCHECKED_CAST")
        val temp = groupTitleViews.clone() as ArrayList<TextView> //wordViews 복사

        for (i in 0 until groupTitleViews.size) {
            val view = temp[i]
            binding.flexBox.addView(view) //textView 레이아웃에 추가

            val lp = view.layoutParams as FlexboxLayout.LayoutParams
            lp.height = dpToPx(this, 32.5f).toInt()
            lp.setMargins(
                resources.getDimension(R.dimen.todo_small_margin).toInt(),
                resources.getDimension(R.dimen.todo_small_margin).toInt(),
                resources.getDimension(R.dimen.todo_small_margin).toInt(),
                resources.getDimension(R.dimen.todo_small_margin).toInt(),
            )
            view.layoutParams = lp
        }
    }
}