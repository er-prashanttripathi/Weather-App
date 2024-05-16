package com.app.ecom.weatherApp.adapters

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.ecom.weatherApp.databinding.ItemsBoardsBinding
import com.app.ecom.weatherApp.utils.createGradientDrawableTwo
import com.app.ecom.weatherApp.utils.generateRandomColor
import com.sports.battle.utils.onDebouncedListener


class VideoListExamAdapter(
    var context: Context,
    private val list: List<ContactsContract.Contacts.Data>,
    internal var listener: OnItemClickListener
) : RecyclerView.Adapter<VideoListExamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemsBoardsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    interface OnItemClickListener {
        fun onCatItemClick(response: ContactsContract.Contacts.Data)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class ViewHolder(private val binding: ItemsBoardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(response: ContactsContract.Contacts.Data, position: Int) {
            /**================== Generate & set Gradient bg color programatically without xml=============== */


            val gradientDrawable = context?.let {
                createGradientDrawableTwo(
                    context = it,
                    startColor = generateRandomColor(),
                    endColor = generateRandomColor()
                )
            }

// Now you can set this gradient drawable as the background of any view
            binding.llParent.background = gradientDrawable
            /**================== Generate & set Gradient bg color programatically without xml=============== */
            binding.apply {
//                tvCatName.text = "" + list[position].video.title
                root.onDebouncedListener {
                    listener.onCatItemClick(response)
                }

            }
        }
    }
}