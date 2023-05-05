package com.challenge.code_base_sdk.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.challenge.code_base_sdk.databinding.CharacterItemBinding
import com.challenge.code_base_sdk.model.domain.DomainCharacter


/**
 * The AppAdapter class is responsible for managing the list of characters in the app.
 * @property characters The list of DomainCharacter objects to display
 * @property itemClicked The click listener for the list items
 */
class AppAdapter(
    private val characters: MutableList<DomainCharacter> = mutableListOf(),
    // Higher-order function that takes a DomainCharacter object and returns Unit
    private val itemClicked: (DomainCharacter) -> Unit
) : RecyclerView.Adapter<ItemViewHolder>() {

    /**
     * Updates the list of characters with new items.
     * @param newItems The list of DomainCharacter objects to replace the current list
     */
    fun updateItems(newItems: List<DomainCharacter>) {
        // Only update the list if the new items are different from the current items
        if (characters != newItems) {
            characters.clear()
            characters.addAll(newItems)
            // Create a separate list to store the original items
            originalItems.clear()
            originalItems.addAll(newItems)
            // Notify the adapter that the data set has changed
            notifyDataSetChanged()
        }
    }

    // Create a separate list to store the original items
    private val originalItems: MutableList<DomainCharacter> = mutableListOf()

    /**
     * Filters the list of characters based on a query string.
     * @param query The query string to filter the list by
     */
    fun filter(query: String) {
        val filteredItems = if (query.isBlank()) {
            // If the query is blank, restore the original items
            originalItems.toMutableList()
        } else {
            // Otherwise, filter the items based on the query
            originalItems.filter {
                it.name.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        // Update your list with filtered items
        characters.clear()
        characters.addAll(filteredItems)
        // Notify the adapter that the data set has changed
        notifyDataSetChanged()
    }

    /**
     * Creates a new ItemViewHolder for a list item.
     * @param parent The parent ViewGroup
     * @param viewType The type of view to create
     * @return A new ItemViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    /**
     * Returns the number of items in the list.
     * @return The number of items in the list
     */
    override fun getItemCount(): Int = characters.size

    /**
     * Binds a DomainCharacter object to an ItemViewHolder.
     * @param holder The ItemViewHolder to bind to
     * @param position The position of the item in the list
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bindCharacter(characters[position], itemClicked)
}

/**
 * The ItemViewHolder class represents a single list item.
 * @property binding The CharacterItemBinding for the list item
 */
class ItemViewHolder(
    private val binding: CharacterItemBinding
): RecyclerView.ViewHolder(binding.root) {
    /**
    * Binds a DomainCharacter object to the list item.
    * @param item The DomainCharacter object to bind to
    * @param itemClicked The click listener for the list item
    */
    fun bindCharacter(item: DomainCharacter, itemClicked: (DomainCharacter) -> Unit){
        binding.charName.text = item.name

        itemView.setOnClickListener {
            itemClicked(item)
        }
    }
}