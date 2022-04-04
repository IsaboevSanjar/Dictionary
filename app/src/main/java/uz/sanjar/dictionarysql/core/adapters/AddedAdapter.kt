package uz.sanjar.dictionarysql.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import uz.sanjar.dictionarysql.core.database.Database
import uz.sanjar.dictionarysql.core.model.Words
import uz.sanjar.dictionarysql.databinding.ItemAddedBinding

class AddedAdapter : RecyclerView.Adapter<AddedAdapter.ViewHolder>() {

    var itemClickListenerR: ((Words) -> Unit)? = null

    var data = ArrayList<Words>()
        set(value) {
            field.addAll(value)
            notifyItemRangeInserted(field.size - value.size, value.size)
        }

    fun clearAddedData() {
        data.clear()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemAddedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(words: Words) {

            if (words.isOpen) {
                binding.swipeLayoutAdded.open(false)
            } else {
                binding.swipeLayoutAdded.close(false)
            }
            if (words.isExpended) {
                binding.expandableLayout.expand(false)
            } else {
                binding.expandableLayout.collapse(false)
            }
            binding.itemEng.text = " ${words.eng}"
            binding.itemUz.text = " ${words.uz}"

            // TODO: 3/14/2022 Delete uchun ohirida ishklatish kerak
            binding.addFevItem.setOnCheckedChangeListener { checkBox, isChecked ->
                if (isChecked) {
                    words.fav = 1
                    itemClickListenerR?.invoke(words)
                } else {
                    words.fav = 0
                    itemClickListenerR?.invoke(words)
                }
            }
            binding.descriptionItemRecent.text = words.description
            binding.itemClickRecent.setOnClickListener(View.OnClickListener {
                words.history = 1
                itemClickListenerR?.invoke(words)
                binding.expandableLayout.toggle()
            })
            binding.deleteAdded.setOnClickListener(View.OnClickListener {
                val int: Int = words.id
                Database.getDatabase().deleteWords(words)
                notifyItemChanged(int)
                itemClickListenerR?.invoke(words)
                // har bitta bosilgan vaqtda chiqadi va keyin kirib ketadi
            })
            binding.swipeLayoutAdded.setSwipeListener(object : SwipeRevealLayout.SwipeListener {
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
        ViewHolder(ItemAddedBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindData(data[position])

    override fun getItemCount(): Int = data.size
}