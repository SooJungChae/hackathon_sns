package com.planethackathon.worklifebalancelife.common

/**
 * Created by minseok on 2018. 9. 2..
 * hackathon_sns.
 */
class GlobalUtils {

    companion object {
        fun millisToString(milliseconds: Long): String {
            val seconds = (milliseconds / 1000) % 60
            val minutes = (milliseconds / (1000 * 60) % 60)
            val hours = (milliseconds / (1000 * 60 * 60) % 24)

            return "$hours” $minutes’ $seconds."
        }
    }
}