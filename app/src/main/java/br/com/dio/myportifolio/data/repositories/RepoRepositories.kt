package br.com.dio.myportifolio.data.repositories

import br.com.dio.myportifolio.data.model.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun listRepositories(user: String): Flow<List<Repo>>
}