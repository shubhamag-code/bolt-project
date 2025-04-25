package com.bolt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClassDetail(
    val name: String = "Unknown",
    @SerialName("hit_die") val hitDie: Int = 0,
    @SerialName("proficiency_choices") val proficiencyChoices: List<ProficiencyChoice> = emptyList(),
    val savingThrows: List<Ability> = emptyList()
)

@Serializable
data class ProficiencyChoice(
    val desc: String?, val choose: Int? = null, val type: String? = null, val from: List<ApiReference>? = null
)

@Serializable
data class ApiReference(
    val name: String? = null, val index: String? = null, val url: String? = null
)

@Serializable
data class Ability(
    val name: String? = null, val index: String? = null, val url: String? = null
)
