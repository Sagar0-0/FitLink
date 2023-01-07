package com.example.fitlink.model

data class ExerciseRoutine(
    var monday: String? = null,
    var tuesday: String? = null,
    var wednesday: String? = null,
    var thursday: String? = null,
    var friday: String? = null,
    var saturday: String? = null,
    var sunday: String? = null,
){

    companion object{
        const val FIELD_MONDAY = "monday"
        const val FIELD_TUESDAY = "tuesday"
        const val FIELD_WEDNESDAY= "wednesday"
        const val FIELD_THURSDAY = "thursday"
        const val FIELD_FRIDAY = "friday"
        const val FIELD_SATURDAY = "saturday"
        const val FIELD_SUNDAY = "sunday"

    }
}
