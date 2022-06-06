package com.dantropov.medtest.di

import android.content.Context
import com.dantropov.medtest.database.MedQuizDB
import com.dantropov.medtest.database.dao.MedQuizDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MedQuizDB {
        return MedQuizDB.getInstance(context)
    }

    @Provides
    fun provideMedicineDao(medicineDb: MedQuizDB): MedQuizDao {
        return medicineDb.medQuizDao()
    }
}