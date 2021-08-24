package br.com.dio.myportifolio.domain

import br.com.dio.myportifolio.core.UseCase
import br.com.dio.myportifolio.data.model.Repo
import br.com.dio.myportifolio.data.repositories.RepoRepository
import kotlinx.coroutines.flow.Flow


class ListUserRepositoriesUseCase(
    private val repository: RepoRepository
) : UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String): Flow<List<Repo>> {
        return repository.listRepositories(param)
    }
}