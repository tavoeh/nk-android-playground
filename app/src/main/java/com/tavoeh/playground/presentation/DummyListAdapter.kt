package com.tavoeh.playground.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/** Usage
val adapter = DummyListAdapter {
Toast.makeText(context, "Item Clicked $it", Toast.LENGTH_SHORT).show()
}
adapter.submitList(sampleItemList)
 */

class DummyListAdapter(
    private val onItemClick: (Item) -> Unit
) : ListAdapter<Item, ItemViewHolder>(ItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemBinding = parent.inflater.inflate(android.R.layout.simple_list_item_1, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = currentList[position]
        with(holder.view) {
            findViewById<TextView>(android.R.id.text1).text = item.text
            setOnClickListener { onItemClick(item) }
        }
    }
}

class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

object ItemDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
}

data class Item(val id: String, val text: String)

val sampleItemList = listOf(
    Item("001", "Text 1"),
    Item("002", "Text 2"),
    Item("003", "Text 3")
)

val View.inflater: LayoutInflater
    get() = LayoutInflater.from(this.context)
