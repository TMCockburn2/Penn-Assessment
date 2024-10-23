package com.example.pennassessment.DI

import android.app.Application
import android.content.Context
import com.example.pennassessment.API.AirQualityStationAPI
import com.example.pennassessment.BaseApplication
import com.example.pennassessment.Data.Location.LocationInfo
import com.example.pennassessment.Data.Location.LocationInfoImpl
import com.example.pennassessment.Data.Repo.StationRepository
import com.example.pennassessment.Data.Repo.StationRepositoryImpl
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication

    }


    /**
     * The following functions are singleton instances that can be injected through the project, as well as other providing functions. For the API, I left the url string
     * as it was the only place it was needed, rather than moving it to a separate variable
     */
    @Provides
    @Singleton
    fun provideAPI(): AirQualityStationAPI {
        return Retrofit.Builder()
            .baseUrl("https://api.waqi.info")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AirQualityStationAPI::class.java)
    }

    /**
     * the api input parameter is taken from provideAPI()
     */
    @Provides
    @Singleton
    fun provideRepository(api:AirQualityStationAPI): StationRepository {
        return StationRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    /**
     * fused location and application are taken from the provides calls
     */
    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application
    ): LocationInfo = LocationInfoImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )

}