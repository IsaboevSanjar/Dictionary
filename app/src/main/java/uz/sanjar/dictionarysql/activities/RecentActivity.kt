package uz.sanjar.dictionarysql.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.sanjar.dictionarysql.core.adapters.RecentAdapter
import uz.sanjar.dictionarysql.core.database.Database
import uz.sanjar.dictionarysql.core.model.Words
import java.util.*


class RecentActivity : AppCompatActivity() {
    private var backButton: ImageButton? = null
    private var sortButton: ImageButton? = null
    private var recyclerViewFavourites: RecyclerView? = null
    private var data = ArrayList<Words>()
    private val adapter = RecentAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(uz.sanjar.dictionarysql.R.layout.activity_recent)
        loadViews()
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerViewFavourites?.addItemDecoration(divider)

        adapter.itemClickListenerR = {
            var n = Database.getDatabase().updateWords(it)
        }
        loadAllData()
        windowBack()
        loadActions()
    }

    private fun loadAllData() {
        adapter.clearRecentData()
        // TODO: 3/13/2022 ClearButton ishlamayaptimi?
        data.addAll(Database.getDatabase().history)
        adapter.data = data
    }

    private fun loadActions() {
        backButton!!.setOnClickListener { onBackPressed() }
        sortButton!!.setOnClickListener(View.OnClickListener {
            val popupMenu = PopupMenu(this, sortButton)
            popupMenu.menuInflater.inflate(
                uz.sanjar.dictionarysql.R.menu.filter_recent,
                popupMenu.menu
            )

            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId === uz.sanjar.dictionarysql.R.id.en_filter) {
                    adapter.sortByEnglish()
                } else if (menuItem.itemId === uz.sanjar.dictionarysql.R.id.uz_filter) {
                    adapter.sortByUzbek()
                }
                false
            }

            popupMenu.show()


        })
    }

    private fun loadViews() {
        backButton = findViewById(uz.sanjar.dictionarysql.R.id.back_btn_recent)
        sortButton = findViewById(uz.sanjar.dictionarysql.R.id.sort_btn_favourites)
        recyclerViewFavourites = findViewById(uz.sanjar.dictionarysql.R.id.word_list_recent)

        recyclerViewFavourites?.adapter = adapter
        recyclerViewFavourites?.layoutManager = LinearLayoutManager(this)
    }

    private fun windowBack() {
        val window = this.window
        window.statusBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
        window.navigationBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
    }
}