package uz.sanjar.dictionarysql.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.sanjar.dictionarysql.R
import uz.sanjar.dictionarysql.core.adapters.DictionaryAdapter
import uz.sanjar.dictionarysql.core.database.Database
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ActivityFavouritesBinding
import java.util.*

class Favourites : AppCompatActivity() {
    private val data = ArrayList<Words>()
    private val adapter = DictionaryAdapter()

    private var _binding: ActivityFavouritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.wordListFav.addItemDecoration(divider)
        binding.wordListFav.adapter = adapter
        binding.wordListFav.layoutManager = LinearLayoutManager(this)
        adapter.itemClickListener = {
            val n = Database.getDatabase().updateWords(it)
        }
        binding.backBtnFav.setOnClickListener(View.OnClickListener { onBackPressed() })
        loadAllData()
        windowBack()
    }

    private fun loadAllData() {
        adapter.clearData()
        // TODO: 3/13/2022 ClearButton ishlamayaptimi?
        data.addAll(Database.getDatabase().fav)
        adapter.data = data
    }

    private fun windowBack() {
        val window = this.window
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dictionary_back_blue)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dictionary_back_blue)
    }
}