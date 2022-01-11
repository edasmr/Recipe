package com.example.deneme.presentation.save

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.R
import com.example.deneme.databinding.SaveGridRecipeItemBinding
import com.example.deneme.databinding.SaveSingleRecipeItemBinding
import com.example.deneme.domain.entity.Receipe

class SaveAdapter : RecyclerView.Adapter<SaveAdapterViewHolder>() {

    var mList: List<Receipe> = listOf()
    var listType: Int = 0
    var onReceipeClick: (Receipe) -> Unit = {}

    override fun onBindViewHolder(holder: SaveAdapterViewHolder, position: Int) {

        when (holder) {
            is SaveAdapterViewHolder.SaveCardViewHolder -> {
                holder.bind(mList[position])
                holder.cardItem.recipeGridItemCard.setOnClickListener {
                    onReceipeClick(mList[position])
                }
            }
            is SaveAdapterViewHolder.SaveDoubleRecipeViewHolder -> {
                holder.bind(mList[position])
                holder.saveSingleRecipe.recipeSingleItemCard.setOnClickListener {
                    onReceipeClick(mList[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveAdapterViewHolder {

        return if (viewType == R.layout.save_grid_recipe_item) {
            val binding = SaveGridRecipeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            SaveAdapterViewHolder.SaveCardViewHolder(binding)
        } else {
            val binding =
                SaveSingleRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SaveAdapterViewHolder.SaveDoubleRecipeViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (listType == 0) {
            R.layout.save_single_recipe_item
        }   else {
            R.layout.save_grid_recipe_item
        }
    }

    fun updateListAndType(mlist: List<Receipe>, listType: Int) {
        this.mList = mlist
        this.listType = listType
    }

}