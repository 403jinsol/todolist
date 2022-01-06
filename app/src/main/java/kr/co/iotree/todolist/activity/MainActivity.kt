package kr.co.iotree.todolist.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amitshekhar.DebugDB
import com.google.android.flexbox.FlexboxLayout
import kr.co.iotree.todolist.R
import kr.co.iotree.todolist.activity.BaseApplication.Companion.pref
import kr.co.iotree.todolist.adapter.MainAdapter
import kr.co.iotree.todolist.databinding.ActivityMainBinding
import kr.co.iotree.todolist.util.LocaleUtil
import kr.co.iotree.todolist.util.PrefUtil
import kr.co.iotree.todolist.util.PrefUtil.Companion.START_SUNDAY
import kr.co.iotree.todolist.util.dpToPx
import kr.co.iotree.todolist.viewModel.CalendarViewModel


class MainActivity : BaseActivity() {
    val viewModel: CalendarViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: MainAdapter
    private var startSunday = pref.getPrefBool(START_SUNDAY, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //recyclerview setting
        adapter = MainAdapter(viewModel, supportFragmentManager)
        binding.recyclerview.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.recyclerview.itemAnimator = null //애니메이션 지우기
        binding.recyclerview.adapter = adapter

        binding.storage.setOnClickListener {
            startActivity(Intent(this, StorageActivity::class.java))
        }

        DebugDB.getAddressLog()
        setViewModel()
        setDrawerMenu()
    }

    override fun onResume() {
        super.onResume()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.END))
            binding.drawerLayout.closeDrawer(GravityCompat.END)

        if (startSunday != pref.getPrefBool(START_SUNDAY, false)) {
            startSunday = pref.getPrefBool(START_SUNDAY, false)
            adapter.notifyItemChanged(1)
        }

        viewModel.allTodo.observe(this) {
            adapter.notifyItemRangeChanged(2, viewModel.allCalendarGroup.value?.size ?: 0)
        }

        viewModel.allCalendarGroup.observe(this) {
            adapter.notifyItemRangeChanged(2, viewModel.allCalendarGroup.value?.size ?: 0)
        }
    }

    private fun setViewModel() {
        viewModel.date.observe(this) {
            viewModel.changeCompleteCount(viewModel.year.value!!, viewModel.month.value!!)
        }

        viewModel.isMonth.observe(this) {
            adapter.setDate(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!, viewModel.isMonth.value!!)
        }

        viewModel.completeCount.observe(this) {
            adapter.setDate(viewModel.year.value!!, viewModel.month.value!!, viewModel.date.value!!, viewModel.isMonth.value!!)
            adapter.notifyItemChanged(1)
        }

        viewModel.allTodo.observe(this) {
            adapter.notifyItemRangeChanged(1, viewModel.allCalendarGroup.value?.size ?: 0)
        }

        viewModel.allCalendarGroup.observe(this) {
            adapter.notifyItemRangeChanged(2, viewModel.allCalendarGroup.value?.size ?: 0)
            setGroupFlexbox()
        }

        viewModel.allTime.observe(this) {
            setTimeFlexbox()
        }
    }

    private fun setDrawerMenu() {
        binding.menu.setOnClickListener {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.END))
                binding.drawerLayout.openDrawer(GravityCompat.END)
        }

        binding.groupManage.setOnClickListener { startActivity(Intent(this, GroupManageActivity::class.java)) }

        binding.inquiry.setOnClickListener { startActivity(Intent(this, InquiryActivity::class.java)) }

        binding.timeManage.setOnClickListener { startActivity(Intent(this, TimeManageActivity::class.java)) }

        binding.setting.setOnClickListener { startActivity(Intent(this, SettingActivity::class.java)) }

        setFlexbox()
    }

    private fun setFlexbox() {
        setGroupFlexbox()
        setTimeFlexbox()
    }

    private fun setGroupFlexbox() {
        val groupList = viewModel.allCalendarGroup.value
        val groupTitleViews = arrayListOf<TextView>()

        binding.groupFlexBox.removeAllViews()

        //flexbox 그룹 추가
        for (group in groupList.orEmpty()) {
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

        addTextView(groupTitleViews, binding.groupFlexBox)
    }

    private fun setTimeFlexbox() {
        val timeList = viewModel.allTime.value
        val timeViews = arrayListOf<TextView>()

        binding.timeFlexBox.removeAllViews()

        //flexbox 시간 추가
        for (time in timeList.orEmpty()) {
            val timeText = if (pref.getPrefString(PrefUtil.LOCALE_CODE, LocaleUtil.OPTION_PHONE_LANGUAGE) == "en") String.format(
                "%d:%02d %s",
                if (time.hour < 12) time.hour else time.hour - 12, time.minute,
                if (time.hour < 12) resources.getString(R.string.am) else resources.getString(R.string.pm)
            )
            else
                String.format(
                    "%s %d:%02d",
                    if (time.hour < 12) resources.getString(R.string.am) else resources.getString(R.string.pm),
                    if (time.hour < 12) time.hour else time.hour - 12, time.minute
                )

            val textView = TextView(this)
            textView.text = timeText
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.BLACK)
            textView.setPadding(dpToPx(this, 10.0f).toInt(), 0, dpToPx(this, 10.0f).toInt(), dpToPx(this, 1.0f).toInt())
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.menu_group_font_size))
            textView.setTypeface(null, Typeface.BOLD)
            textView.setBackgroundResource(R.drawable.round)

            timeViews.add(textView)
        }

        addTextView(timeViews, binding.timeFlexBox)
    }

    private fun addTextView(arrayList: ArrayList<TextView>, flexbox: FlexboxLayout) {
        @Suppress("UNCHECKED_CAST")
        val temp = arrayList.clone() as ArrayList<TextView> //wordViews 복사

        for (i in 0 until arrayList.size) {
            val view = temp[i]
            flexbox.addView(view) //textView 레이아웃에 추가

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