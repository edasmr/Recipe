package com.example.deneme.presentation.save

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.get
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.deneme.R
import com.example.deneme.databinding.FragmentSaveBinding
import com.example.deneme.domain.entity.HomeEntity
import com.example.deneme.domain.entity.Receipe
import com.example.deneme.presentation.home.RandomRecipeList

class SaveFragment : Fragment() {

    private val saveAdapter by lazy { SaveAdapter() }

    private val saveViewModel: SaveViewModel = get()
    private var list = mutableListOf<Receipe>()

    private var _binding: FragmentSaveBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        val root: View = binding.root

        saveAdapter.onReceipeClick = {
            val edcDesign = LayoutInflater.from(context)
                .inflate(R.layout.save_dialog, null, false)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setView(edcDesign)


            val name: TextView = edcDesign.findViewById(R.id.recipename)
            val summary: TextView = edcDesign.findViewById(R.id.summary)
            val spooncularScore: TextView = edcDesign.findViewById(R.id.spooncularScore)
            val pricePerServing: TextView = edcDesign.findViewById(R.id.pricePerServing)
            val recipeImage: ImageView = edcDesign.findViewById(R.id.recipeImage)
            val readyInMinutes:TextView = edcDesign.findViewById(R.id.readyInMinutes)




            name.text = it.title
            summary.text = Html.fromHtml(it.summary)
            spooncularScore.text = it.spoonacularScore.toString()
            pricePerServing.text = it.pricePerServing.toString()
            readyInMinutes.text=it.readyInMinutes.toString()




            Glide.with(requireContext()).load(it.image).into(recipeImage)

            val dialog = alertDialogBuilder.create()
            dialog.show()

        }

        _binding?.viewModel = saveViewModel

        binding.normalList.setOnClickListener {
            binding.recipeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            saveAdapter.updateListAndType(list, 0)
            binding.recipeList.adapter = saveAdapter
        }

        binding.collectionList.setOnClickListener {
            binding.recipeList.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            binding.recipeList.setHasFixedSize(true)
            saveAdapter.updateListAndType(list, 1)
            binding.recipeList.adapter = saveAdapter
        }

        lifecycleScope.launch {
            saveViewModel.saveState.collect {

                binding.progressBar.visibility = View.GONE

                when (it) {

                    is SaveUiState.Success -> {

                        if (it.saveEntity.randomRecipe.isNotEmpty()) {

                            var randomList = mutableListOf<RandomRecipeList>()
                            it.saveEntity.randomRecipe.forEachIndexed { index, recipe ->
                                SaveUiStateSuccess(index,recipe,randomList)

                            }

                            randomList.forEach { randomReceipeList ->
                                randomReceipeList.list?.forEach { receipe ->
                                    list.add(receipe)
                                }
                            }
                        }
                        binding.recipeList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                        saveAdapter.updateListAndType(list, 0)
                        binding.recipeList.adapter = saveAdapter
                    }


                    is SaveUiState.PageSuccess -> {

                        var beforeCount = list.size

                        var randomList = mutableListOf<RandomRecipeList>()
                        it.recipeList.forEachIndexed { index, recipe ->
                            SaveUiStateSuccess(index,recipe,randomList)
                        }

                        randomList.forEach { randomReceipeList ->
                            randomReceipeList.list?.forEach { receipe ->
                                list.add(receipe)
                            }
                        }

                        var endCount = list.size

                        binding.recipeList.adapter?.notifyItemChanged(beforeCount, endCount)
                    }

                    is SaveUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        saveViewModel.getLists()

        binding.recipeList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    saveViewModel.fetch()
                }
            }
        })

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun SaveUiStateSuccess(index:Int,recipe:Receipe,randomList:MutableList<RandomRecipeList> ){


                if (index % 2 == 0) {
                    randomList.add(RandomRecipeList(mutableListOf(recipe)))
                } else {
                    randomList[randomList.size - 1].list?.add(recipe)
                }
            }



}