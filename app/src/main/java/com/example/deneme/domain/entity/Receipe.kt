package com.example.deneme.domain.entity

data class Receipe(
    var id: Int? = null,
    var title: String? = null,
    var image: String? = null,
    var summary: String? = null,
    var spoonacularScore: Double? = null,
    var readyInMinutes: Int? = null,
    var pricePerServing: Double? = null,
   // var saved: Boolean? = null
) : Entity()
