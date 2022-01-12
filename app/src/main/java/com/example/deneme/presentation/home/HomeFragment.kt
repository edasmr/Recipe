package com.example.deneme.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.deneme.R
import com.example.deneme.databinding.FragmentHomeBinding
import com.example.deneme.domain.entity.Receipe
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.get


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel = get()
    private var list = mutableListOf<Any>()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        _binding?.viewModel = homeViewModel

        lifecycleScope.launch {
            homeViewModel.homeState.collect{

                binding.progressBar.visibility = View.GONE

                when(it){
                    is HomeUiState.Success -> {
                        it.homeEntity.todaysRecipe?.let { recipe ->
                            list.add(getString(R.string.today_recipe))
                            list.add(recipe)
                        }

                        if(it.homeEntity.randomRecipe.isNotEmpty()){
                            list.add(getString(R.string.top_recipe))

                            val randomList = mutableListOf<RandomRecipeList>()
                            it.homeEntity.randomRecipe.forEachIndexed { index, recipe ->
                                SaveUiStateSuccess(index,recipe,randomList)
                            }

                            list.addAll(randomList)
                        }

                        binding.recipeList.adapter = HomeAdapter(list)
                    }
                    is HomeUiState.PageSuccess -> {
                        val beforeCount = list.size

                        val randomList = mutableListOf<RandomRecipeList>()
                        it.recipeList.forEachIndexed { index, recipe ->
                            SaveUiStateSuccess(index,recipe,randomList)
                        }

                        list.addAll(randomList)

                        val endCount = list.size

                        binding.recipeList.adapter?.notifyItemChanged(beforeCount, endCount)
                    }
                    is HomeUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        homeViewModel.getLists()

        binding.recipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    homeViewModel.fetch()
                }
            }
        })

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun SaveUiStateSuccess(index:Int, recipe: Receipe, randomList:MutableList<RandomRecipeList> ){


        if (index % 2 == 0) {
            randomList.add(RandomRecipeList(mutableListOf(recipe)))
        } else {
            randomList[randomList.size - 1].list?.add(recipe)
        }
    }



}