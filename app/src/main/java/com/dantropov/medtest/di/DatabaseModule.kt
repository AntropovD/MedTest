package com.dantropov.medtest.di

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.dantropov.medtest.database.MedQuizDB
import com.dantropov.medtest.database.dao.MedQuizDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context, callback: MedQuizDB.Callback): MedQuizDB {
        return Room.databaseBuilder(context, MedQuizDB::class.java, "medicine.db")
            .addCallback(callback)
            .build()
    }

    @Provides
    fun provideMedicineDao(medicineDb: MedQuizDB): MedQuizDao {
        return medicineDb.medQuizDao()
    }

    @Provides
    @Singleton
    fun resourcesProvider(@ApplicationContext context: Context): Resources = context.resources

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope