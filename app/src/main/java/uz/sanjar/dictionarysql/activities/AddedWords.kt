package uz.sanjar.dictionarysql.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.sanjar.dictionarysql.R
import uz.sanjar.dictionarysql.core.adapters.AddedAdapter
import uz.sanjar.dictionarysql.core.database.Database
import uz.sanjar.dictionarysql.core.dialogs.InsertDialog
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ActivityAddedWordsBinding

class AddedWords : AppCompatActivity() {

    private val data = ArrayList<Words>()
    private val adapter = AddedAdapter()

    private var _binding: ActivityAddedWordsBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddedWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.wordListAdded.addItemDecoration(divider)
        binding.backBtnAdd.setOnClickListener(View.OnClickListener { onBackPressed() })
        binding.wordListAdded.adapter = adapter
        binding.wordListAdded.layoutManager = LinearLayoutManager(this)
        adapter.itemClickListenerR = {
            val n = Database.getDatabase().updateWords(it)
        }
        windowBack()
        loadAllData()
    }

    private fun loadAllData() {
        adapter.clearAddedData()
        // TODO: 3/13/2022 ClearButton ishlamayaptimi?
        data.addAll(Database.getDatabase().added)
        adapter.data = data
    }

    fun itemAdd(view: View?) {
        val dialog = InsertDialog(this)
        dialog.show()
    }

    private fun windowBack() {
        val window = this.window
        window.navigationBarColor = ContextCompat.getColor(this, R.color.dictionary_back_blue)
        window.statusBarColor = ContextCompat.getColor(this, R.color.dictionary_back_blue)
    }
}