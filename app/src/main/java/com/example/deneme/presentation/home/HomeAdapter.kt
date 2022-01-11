package com.example.deneme.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.R
import com.example.deneme.databinding.DoubleRecipeItemBinding
import com.example.deneme.databinding.HomeTitleItemBinding
import com.example.deneme.databinding.TodayReceipeCardItemBinding
import com.example.deneme.domain.entity.Receipe

class HomeAdapter(private var mList: List<Any>) : RecyclerView.Adapter<HomeAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapterViewHolder {

        when(viewType){
            R.layout.today_receipe_card_item -> {
                val binding = TodayReceipeCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return HomeAdapterViewHolder.HomeCardViewHolder(binding)
            }
            R.layout.home_title_item -> {
                val binding = HomeTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeAdapterViewHolder.HomeTitleViewHolder(binding)
            }
           // R.layout.item_collection -> {
           //     val binding = ItemCollectionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            //    return HomeAdapterViewHolder.HomeCollectionViewHolder(binding)
          //  }
            R.layout.double_recipe_item -> {
                val binding = DoubleRecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HomeAdapterViewHolder.HomeDoubleRecipeViewHolder(binding)
            }
        }

        val binding = HomeTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeAdapterViewHolder.HomeTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAdapterViewHolder, position: Int) {

        when(holder){
            is HomeAdapterViewHolder.HomeCardViewHolder ->{
                holder.cardItem.recipe = mList[position] as? Receipe
            }
            is HomeAdapterViewHolder.HomeTitleViewHolder ->{
                holder.titleItem.title.text = mList[position] as? String

                if(position == 0){
                    holder.titleItem.button.visibility = View.GONE
                }else{
                    holder.titleItem.button.visibility = View.VISIBLE
                    holder.titleItem.button.setOnClickListener{

                    }
                }
            }

            is HomeAdapterViewHolder.HomeDoubleRecipeViewHolder ->{
                val list = (mList[position] as RandomRecipeList).list

                holder.doubleRecipe.recipe1 = if(list?.size ?: 0 > 0) list?.get(0) else null
                holder.doubleRecipe.recipe2 = if(list?.size ?: 0 > 1) list?.get(1) else null
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemViewType(position: Int): Int {

        when(mList[position]){
            is String -> return R.layout.home_title_item
            is Receipe -> return R.layout.today_receipe_card_item
            is RandomRecipeList -> return R.layout.double_recipe_item
        }

        return R.layout.today_receipe_card_item
    }
}