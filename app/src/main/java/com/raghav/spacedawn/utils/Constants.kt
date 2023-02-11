package com.raghav.spacedawn.utils

class Constants {
    companion object {
        const val BASE_URL_SPACEFLIGHT = "https://api.spaceflightnewsapi.net/v3/"
        const val ARTICLE_DATE_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val LAUNCH_DATE_INPUT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val DATE_OUTPUT_FORMAT = "MMMM dd,yyyy HH:mm z"
        const val CHANNEL_ID = "id"
        const val CHANNEL_NAME = "channel"
        const val NOTIFICATION_ID: Int = 0
        const val MinutestoMiliseconds: Long = 900000 // 15 minutes
        const val STATUS_SET = "Reminder Set"
        const val SPACE_FLIGHT_API = "space_flight_api"
        const val LAUNCH_LIBRARY_API = "retrofit_client_for_launch_library_api"
        const val DATABASE_NAME = "reminder_db.db"
        const val LAUNCHES_INCREMENT = 10
        const val SKIP_ARTICLES_COUNT = 10
        const val SEARCH_DELAY_TIME = 3000L
        const val REMINDER = "Reminder!"
        const val REMINDER_SET = "Reminder to see the Rocket Launch set via Space Dawn"
    }
}
