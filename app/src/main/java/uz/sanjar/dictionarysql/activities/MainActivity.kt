package uz.sanjar.dictionarysql.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.sanjar.dictionarysql.BuildConfig
import uz.sanjar.dictionarysql.R
import uz.sanjar.dictionarysql.core.adapters.DictionaryAdapter
import uz.sanjar.dictionarysql.core.database.Database
import uz.sanjar.dictionarysql.core.database.MemoryHelper
import uz.sanjar.dictionarysql.core.dialogs.InsertDialog
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val data = ArrayList<Words>()
    private val context = this
    private val adapter = DictionaryAdapter()
    private var _words: Words? = null
    private val __words get() = _words!!

    private var words: Words = Words(0, "", "", "", 0, 0, 0, 0, false, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val divider: RecyclerView.ItemDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.wordList.addItemDecoration(divider)

        binding.wordList.adapter = adapter
        binding.wordList.layoutManager = LinearLayoutManager(this)

        adapter.itemClickListener = {
            val n = Database.getDatabase().updateWords(it)
        }

        MemoryHelper.getHelper().language

        loadAllData()
        windowBack()
        menuClicked()
        favouriteMenuClicked()
        recentMenuClicked()
        aboutUsCliched()
        settingMenuClicked()
        addedMenuClicked()
        sortClicked()
        //bindData(words)

        searchClickedEn()
        //IDK what is changingConfigurations but it's working [position]
        //bindData(words)
        // TODO: 3/24/2022  Shu yerda owrdga null Pointer bervotti nimadur qilish kerak 

    }

    private fun bindData(words: Words) {
        Toast.makeText(this, "bindData", Toast.LENGTH_SHORT).show()
        binding.sortMain.setOnClickListener(View.OnClickListener {
            val popupMenu = android.widget.PopupMenu(this, binding.sortMain)
            popupMenu.menuInflater.inflate(R.menu.filter_main, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId === R.id.en_filter) {
                    adapter.sortByEnglish()
                } else if (menuItem.itemId === R.id.uz_filter) {
                    adapter.sortByUzbek()
                } else if (menuItem.itemId === R.id.en_search) {
                    adapter.searchEn(words)
                } else if (menuItem.itemId === R.id.uz_search) {
                    words.enuz = 0
                } else if (menuItem.itemId == R.id.shuffle_filter) {
                    loadAllData()

                }
                false
            }

            popupMenu.show()
        })


        if (words.enuz == 1) {
            searchClickedEn()
        } else if (words.enuz == 0) {
            searchClickedUz()
        }

    }

    private fun aboutUsCliched() {
        binding.infoMenu.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, AboutUs::class.java)
            startActivity(intent)
        })
    }

    private fun sortClicked() {
        binding.sortMain.setOnClickListener(View.OnClickListener {
            val popupMenu = android.widget.PopupMenu(this, binding.sortMain)
            popupMenu.menuInflater.inflate(R.menu.filter_main, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId === R.id.en_filter) {
                    adapter.sortByEnglish()
                } else if (menuItem.itemId === R.id.uz_filter) {
                    adapter.sortByUzbek()
                } else if (menuItem.itemId === R.id.en_search) {
                    MemoryHelper.getHelper().language = false
                    searchClickedEn()
                    loadAllData()
                } else if (menuItem.itemId === R.id.uz_search) {
                    MemoryHelper.getHelper().language = true
                    searchClickedUz()
                    loadAllData()
                } else if (menuItem.itemId == R.id.shuffle_filter) {
                    loadAllData()
                }
                false
            }

            popupMenu.show()
        })
    }

    private fun menuClicked() {
        binding.menuButton.setOnClickListener(View.OnClickListener {
            binding.frameMenu.visibility = View.VISIBLE
            binding.mainLayout.isEnabled = false
            binding.floatActionBtn.isEnabled = false
            binding.wordList.isEnabled = false
            binding.menuBackToMain.visibility = View.VISIBLE
            binding.searchView.isEnabled = false
            binding.wordList.isClickable = false
        })
        binding.menuBackToMain.setOnClickListener(View.OnClickListener {
            mainActivityOn()
        })
    }

    private fun recentMenuClicked() {
        binding.favouritesMenu.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, RecentActivity::class.java)
            startActivity(intent)
        })
    }

    private fun settingMenuClicked() {
        binding.settingsMenu.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        })
    }

    private fun favouriteMenuClicked() {
        binding.recentMenu.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, Favourites::class.java)
            startActivity(intent)
        })
    }

    private fun addedMenuClicked() {
        binding.addedMenu.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, AddedWords::class.java)
            startActivity(intent)
        })
    }

    private fun searchClickedEn() {
        with(binding) {
            searchView.addTextChangedListener {
                it?.let {
                    if (it.toString().trim().isNotEmpty()) {
                        adapter.clearData()
                        val data = Database.getDatabase().searchEnglishWords(it.toString().trim())
                        adapter.data = data
                    } else {
                        adapter.clearData()
                        loadAllData()


                    }
                }
            }
        }
    }

    private fun searchClickedUz() {
        with(binding) {
            searchView.addTextChangedListener {
                it?.let {
                    if (it.toString().trim().isNotEmpty()) {
                        adapter.clearData()
                        val data = Database.getDatabase().searchUzWords(it.toString().trim())
                        adapter.data = data
                    } else {
                        adapter.clearData()
                        loadAllData()
                    }
                }
            }
        }
    }

    private fun mainActivityOn() {
        binding.frameMenu.visibility = View.GONE
        binding.mainLayout.isEnabled = true
        binding.floatActionBtn.isEnabled = true
        binding.wordList.isEnabled = true
        binding.searchView.isEnabled = true
        binding.menuBackToMain.visibility = View.GONE
    }

    private fun loadAllData() {
        adapter.clearData()
        // TODO: 3/13/2022 ClearButton ishlamayaptimi?

        data.clear()
        data.addAll(Database.getDatabase().dictionary)

        adapter.data = data
        mainActivityOn()
    }

    fun itemAdd(view: View) {
        val dialog = InsertDialog(this)
        dialog.show()
    }

// TODO: 3/17/2022 Bular umumiyntarzda Play AMrketga Qoygandan Keyin Share qilish va Rate qilish uchundir

    private fun setListeners() {
        val appPackageName =
            BuildConfig.APPLICATION_ID
        binding.shareMenu.setOnClickListener {
            shareLink("\n Let me recommend this application to you\n https://play.google.com/store/apps/details?id=$appPackageName\n\n")
        }

        binding.rateMenu.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        }

    }

    fun Context.shareLink(link: String) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, link)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    fun windowBack() {
        val window: Window = this.window
        // Kotlindagi depressed bolgani uchun shu method ishlatiladi ekan
        window.statusBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
        window.navigationBarColor =
            ContextCompat.getColor(this, uz.sanjar.dictionarysql.R.color.dictionary_back_blue)
    }

    override fun onResume() {
        super.onResume()
        MemoryHelper.getHelper().favState
    }

    override fun onStop() {
        super.onStop()
        MemoryHelper.getHelper().favState
    }
}