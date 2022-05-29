package com.ishzk.android.memo.di

import com.ishzk.android.memo.Repository.FireStoreMemoRepository
import com.ishzk.android.memo.Repository.MemoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier

@Qualifier
annotation class FireStoreMemo

@Module
@InstallIn(ActivityComponent::class)
class RepositoryModule {
    @FireStoreMemo
    @Provides
    fun provideRepository(): MemoRepository{
        return FireStoreMemoRepository()
    }
}