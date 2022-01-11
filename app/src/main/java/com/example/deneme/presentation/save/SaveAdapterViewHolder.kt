package com.example.deneme.presentation.save

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.databinding.*

import com.example.deneme.domain.entity.Receipe


sealed class SaveAdapterViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {

    class SaveCardViewHolder(
        val cardItem: SaveGridRecipeItemBinding) :
        SaveAdapterViewHolder(cardItem.root) {
        fun bind(receipe: Receipe) {
            cardItem.recipe = receipe
        }
    }

    class SaveDoubleRecipeViewHolder(
        val saveSingleRecipe: SaveSingleRecipeItemBinding) :
        SaveAdapterViewHolder(saveSingleRecipe.root) {
        fun bind(receipe: Receipe) {
            saveSingleRecipe.recipe = receipe
        }
    }

}