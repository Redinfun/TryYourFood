package br.com.tryyourfood.data.repository

import br.com.tryyourfood.data.LocalDataSource
import br.com.tryyourfood.data.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}