package com.example.deneme.domain.entity

class HomeEntity(
    val todaysRecipe: Receipe?,
    val randomRecipe: List<Receipe>,
) : Entity()
