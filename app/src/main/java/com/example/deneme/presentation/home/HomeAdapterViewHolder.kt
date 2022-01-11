package com.example.deneme.presentation.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.databinding.DoubleRecipeItemBinding
import com.example.deneme.databinding.HomeTitleItemBinding
import com.example.deneme.databinding.TodayReceipeCardItemBinding

sealed class HomeAdapterViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {

    class HomeCardViewHolder(val cardItem: TodayReceipeCardItemBinding) : HomeAdapterViewHolder(cardItem.root)
    class HomeTitleViewHolder(val titleItem: HomeTitleItemBinding) : HomeAdapterViewHolder(titleItem.root)
    class HomeDoubleRecipeViewHolder(val doubleRecipe: DoubleRecipeItemBinding) : HomeAdapterViewHolder(doubleRecipe.root)


}