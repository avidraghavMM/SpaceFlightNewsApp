package com.raghav.spacedawn.di

import android.content.Context
import androidx.room.Room
import com.raghav.spacedawn.db.ReminderDao
import com.raghav.spacedawn.db.ReminderDatabase
import com.raghav.spacedawn.network.LaunchLibrary
import com.raghav.spacedawn.network.SpaceFlightAPI
import com.raghav.spacedawn.repository.AppRepository
import com.raghav.spacedawn.repository.IAppRepository
import com.raghav.spacedawn.utils.AppApplication
import com.raghav.spacedawn.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        ReminderDatabase::class.java,
        "reminder_db.db"
    ).build()

    @Singleton
    @Provides
    fun provideReminderDao(database: ReminderDatabase) = database.getRemindersDao()

    @Singleton
    @Provides
    fun provideRepository(
        dao: ReminderDao,
        spaceFlightAPI: SpaceFlightAPI,
        launchLibrary: LaunchLibrary
    ): IAppRepository = AppRepository(
        dao = dao,
        spaceFlightApi = spaceFlightAPI,
        launchLibraryApi = launchLibrary
    )

    @Provides
    @Singleton
    fun provideAppApplication(@ApplicationContext app: Context): AppApplication {
        return app as AppApplication
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val level = logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(level).build()
    }

    @Singleton
    @Provides
    @Named(Constants.RETROFIT_CLIENT_FOR_SPACE_FLIGHT_API)
    fun provideRetrofitForSpaceFlightApi(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL_SPACEFLIGHT)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

    @Singleton
    @Provides
    fun provideSpaceFlightApi(
        @Named(Constants.RETROFIT_CLIENT_FOR_SPACE_FLIGHT_API) retrofit: Retrofit
    ): SpaceFlightAPI {
        return retrofit.create(SpaceFlightAPI::class.java)
    }

    @Singleton
    @Provides
    @Named(Constants.RETROFIT_CLIENT_FOR_LAUNCH_LIBRARY_API)
    fun provideRetrofitForLaunchApi(client: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(Constants.BASE_URL_LAUNCHLIBRARY)
            .addConverterFactory(GsonConverterFactory.create()).client(client).build()

    @Singleton
    @Provides
    fun provideLaunchApi(
        @Named(Constants.RETROFIT_CLIENT_FOR_LAUNCH_LIBRARY_API) retrofit: Retrofit
    ): LaunchLibrary {
        return retrofit.create(LaunchLibrary::class.java)
    }
}
