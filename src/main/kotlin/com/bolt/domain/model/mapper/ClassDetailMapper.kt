package com.bolt.domain.model.mapper

import com.bolt.model.Ability
import com.bolt.model.ApiReference
import com.bolt.model.ClassDetail
import com.bolt.model.ProficiencyChoice
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

object ClassDetailMapper {

    fun mapToClassDetail(json: JsonObject): ClassDetail {
        return ClassDetail(
            name = json["name"]?.jsonPrimitive?.content ?: "Unknown",
            hitDie = json["hit_die"]?.jsonPrimitive?.content?.toInt() ?: 0,
            proficiencyChoices = json["proficiency_choices"]?.jsonArray?.map { proficiencyChoice ->
                mapToProficiencyChoice(proficiencyChoice.jsonObject)
            } ?: emptyList(),
            savingThrows = json["saving_throws"]?.jsonArray?.map { savingThrow ->
                mapToAbility(savingThrow.jsonObject)
            } ?: emptyList())
    }

    private fun mapToProficiencyChoice(json: JsonObject): ProficiencyChoice {
        return ProficiencyChoice(
            desc = json["desc"]?.jsonPrimitive?.content ?: "Unknown",
            choose = json["choose"]?.jsonPrimitive?.int,
            type = json["type"]?.jsonPrimitive?.content,
            from = json["from"]?.jsonObject?.get("options")?.jsonArray?.mapNotNull { option ->
                option.jsonObject["item"]?.jsonObject?.let { mapToApiReference(it) }
            } ?: emptyList())
    }

    private fun mapToAbility(json: JsonObject): Ability {
        return Ability(
            name = json["name"]?.jsonPrimitive?.content,
            index = json["index"]?.jsonPrimitive?.content,
            url = json["url"]?.jsonPrimitive?.content
        )
    }

    private fun mapToApiReference(json: JsonObject): ApiReference {
        return ApiReference(
            name = json["name"]?.jsonPrimitive?.content,
            index = json["index"]?.jsonPrimitive?.content,
            url = json["url"]?.jsonPrimitive?.content
        )
    }
}