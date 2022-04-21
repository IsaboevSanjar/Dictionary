package uz.sanjar.dictionarysql.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import uz.sanjar.dictionarysql.core.database.MemoryHelper
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ItemRecentBinding

class RecentAdapter : RecyclerView.Adapter<RecentAdapter.ViewHolder>() {

    var itemClickListenerR: ((Words) -> Unit)? = null

    var data = ArrayList<Words>()
        set(value) {
            field.addAll(value)
            notifyItemRangeInserted(field.size - value.size, value.size)
        }

    fun clearRecentData() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemRecentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(words: Words) {
            val isFav: Boolean = MemoryHelper.getHelper().favState
            binding.addFavItem.isChecked = words.fav == 1 && isFav == true

            if (words.isOpen) {
                binding.swipeLayoutRecent.open(false)
            } else {
                binding.swipeLayoutRecent.close(false)
            }
            if (words.isExpended) {
                binding.expandableLayout.expand(false)
            } else {
                binding.expandableLayout.collapse(false)
            }/*
            binding.itemEng.ellipsize= TextUtils.TruncateAt.MARQUEE
            binding.itemUz.ellipsize= TextUtils.TruncateAt.MARQUEE
            binding.itemUz.isSelected=true
*/
            binding.itemEng.text = " ${words.eng}"
            binding.itemUz.text = " ${words.uz}"

            // TODO: 3/14/2022 Delete uchun ohirida ishklatish kerak
            binding.addFavItem.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    words.fav = 1
                    itemClickListenerR?.invoke(words)
                    MemoryHelper.getHelper().favState = b
                } else {
                    words.fav = 0
                    itemClickListenerR?.invoke(words)
                    MemoryHelper.getHelper().favState = b
                }
            }

            binding.descriptionItemRecent.text = words.description
            binding.itemClickRecent.setOnClickListener(View.OnClickListener {
                itemClickListenerR?.invoke(words)
                binding.expandableLayout.toggle()
            })

            binding.deleteRecent.setOnClickListener(View.OnClickListener {
                words.history = 0
                itemClickListenerR?.invoke(words)
                // har bitta bosilgan vaqtda chiqadi va keyin kirib ketadi
            })


            binding.swipeLayoutRecent.setSwipeListener(object : SwipeRevealLayout.SwipeListener {
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
        ViewHolder(ItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false))

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
}