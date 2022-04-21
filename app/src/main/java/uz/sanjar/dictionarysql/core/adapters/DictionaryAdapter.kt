package uz.sanjar.dictionarysql.core.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import uz.sanjar.dictionarysql.core.database.MemoryHelper
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ItemWordBinding

class DictionaryAdapter : RecyclerView.Adapter<DictionaryAdapter.ViewHolder>() {

    var itemClickListener: ((Words) -> Unit)? = null

    var data = ArrayList<Words>()
        set(value) {
            field.addAll(value)
            notifyItemRangeInserted(field.size - value.size, value.size)
        }

    fun clearData() {
        data.clear()
        // search qilib yozilgan vaqtda hamma narsa clear bolib ketgan edi
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(words: Words) {
            var context: Context
            val isFav: Boolean = MemoryHelper.getHelper().favState

            val language: Boolean = MemoryHelper.getHelper().language
            binding.addFavouriteItem.isChecked = words.fav == 1 && isFav == true
            if (words.isOpen) {
                binding.swipeLayout.open(false)
            } else {
                binding.swipeLayout.close(false)
            }

            var expandable: Boolean = words.isExpended

            // TODO: 3/25/2022 expandable ni ham yaxshilab korib chiqsh kerak

            if (words.isExpended) {
                binding.expandableLayout.expand(false)
            } else {
                binding.expandableLayout.collapse(false)
            }

            if (language == false) {
                binding.itemEng.text = " ${words.eng}"
                binding.itemUz.text = " ${words.uz}"
                binding.flagText.text = "\uD83C\uDDEC\uD83C\uDDE7 \uD83C\uDDFA\uD83C\uDDFF"
            } else if (language) {
                binding.itemEng.text = " ${words.uz}"
                binding.itemUz.text = " ${words.eng}"
                binding.flagText.text = "\uD83C\uDDFA\uD83C\uDDFF \uD83C\uDDEC\uD83C\uDDE7"
            }

            binding.descriptionItem.text = words.description

            binding.itemClick.setOnClickListener(View.OnClickListener {
                words.history = 1
                itemClickListener?.invoke(words)
                // har bitta bosilgan vaqtda chiqadi va keyin kirib ketadi
                binding.expandableLayout.toggle()

/*
                binding.itemEng.ellipsize=TextUtils.TruncateAt.MARQUEE
                binding.itemEng.isSelected=true

                binding.itemUz.ellipsize=TextUtils.TruncateAt.MARQUEE
                binding.itemUz.isSelected=true*/
            })
            // TODO: 3/14/2022 Delete uchun ohirida ishklatish kerak
            binding.addFavouriteItem.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    words.fav = 1
                    itemClickListener?.invoke(words)
                    MemoryHelper.getHelper().favState = b
                } else {
                    words.fav = 0
                    itemClickListener?.invoke(words)
                    MemoryHelper.getHelper().favState = b
                }
            }

            //Swipe surilgan vaqtda pasidagi recycle view dan yana biri surilib qolishining oldini oliosh maqsadida

            binding.swipeLayout.setSwipeListener(object : SwipeRevealLayout.SwipeListener {
                override fun onClosed(view: SwipeRevealLayout?) {
                    words.isOpen = false
                }

                override fun onOpened(view: SwipeRevealLayout?) {
                    words.isOpen = true
                }

                override fun onSlide(view: SwipeRevealLayout?, slideOffset: Float) {

                }

            })

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size
    fun sortByEnglish() {
        data.sortBy {
            it.eng
        }
        notifyDataSetChanged()
    }

    fun sortByUzbek() {
        data.sortBy {
            it.uz
        }
        notifyDataSetChanged()
    }

    fun searchEn(words: Words) {
        words.enuz = 1
        notifyDataSetChanged()
    }


}