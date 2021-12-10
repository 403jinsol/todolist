package kr.co.iotree.todolist.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.adapter.MainAdapter
import kr.co.iotree.todolist.database.TodoDatabase
import kr.co.iotree.todolist.database.TodoGroup
import kr.co.iotree.todolist.databinding.ActivityMainBinding
import kr.co.iotree.todolist.util.dpToPx
import kr.co.iotree.todolist.viewModel.CalendarViewModel

class MainActivity : AppCompatActivity() {
    val viewModel: CalendarViewModel by viewModels()
    var db: TodoDatabase? = null
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = TodoDatabase.getInstance(this)
        viewModel.groups.value = db!!.groupDao().getAllTodoGroup()

        //recyclerview setting
        adapter = MainAdapter(viewModel)
        binding.recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerview.itemAnimator = null //애니메이션 지우기
        binding.recyclerview.adapter = adapter
        //뷰모델 설정
        viewModel.date.observe(this) {
            (binding.recyclerview.adapter as MainAdapter).setDate(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!, viewModel.isMonth.value!!)
        }

        viewModel.isMonth.observe(this) {
            (binding.recyclerview.adapter as MainAdapter).setDate(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!, viewModel.isMonth.value!!)
        }

        setDrawerMenu(viewModel.groups.value)
    }

    override fun onResume() {
        super.onResume()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END))
            binding.drawerLayout.closeDrawer(GravityCompat.END)

        viewModel.groups.value = db!!.groupDao().getAllTodoGroup()
        viewModel.groups.observe(this) {
            adapter.notifyItemRangeChanged(0, it.size + 2)
        }
    }

    private fun setDrawerMenu(list: MutableList<TodoGroup>?) {
        val groupTitleViews = arrayListOf<TextView>()

        binding.menu.setOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END))
                binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.groupManage.setOnClickListener {
            val intent = Intent(this@MainActivity, GroupManageActivity::class.java)
            startActivity(intent)
        }

        //flexbox 그룹 추가
        for (group in list.orEmpty()) {
            val textView = TextView(this)
            textView.text = group.title
            textView.gravity = Gravity.CENTER
            textView.setTextColor(group.color)
            textView.setPadding(dpToPx(this, 10.0f).toInt(), 0, dpToPx(this, 10.0f).toInt(), dpToPx(this, 1.0f).toInt())
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.menu_group_font_size))
            textView.setTypeface(null, Typeface.BOLD)
            textView.setBackgroundResource(R.drawable.round)

            groupTitleViews.add(textView)
        }

        @Suppress("UNCHECKED_CAST")
        val temp = groupTitleViews.clone() as ArrayList<TextView> //wordViews 복사

        for (i in 0 until groupTitleViews.size) {
            val view = temp[i]
            binding.flexBox.addView(view) //textView 레이아웃에 추가

            val lp = view.layoutParams as FlexboxLayout.LayoutParams
            lp.height = dpToPx(this, 30.0f).toInt()
            lp.setMargins(
                0,
                resources.getDimension(R.dimen.menu_group_margin).toInt(),
                resources.getDimension(R.dimen.menu_group_margin).toInt(),
                resources.getDimension(R.dimen.menu_group_margin).toInt(),
            )
            view.layoutParams = lp
        }
    }
}