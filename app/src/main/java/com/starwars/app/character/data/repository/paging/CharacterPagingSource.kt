package com.starwars.app.character.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.model.maper.mapFromListModel
import com.starwars.app.character.domain.entity.Character
import retrofit2.HttpException
import java.io.IOException

class CharacterPagingSource (
    private val characterRemoteDataSource: CharacterRemoteDataSource
): PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return  state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return try {
            val currentPage = params.key ?: 1
            val characters = characterRemoteDataSource.getCharacter(page = currentPage)
            LoadResult.Page(
                data = characters.results.mapFromListModel(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (characters.results.isEmpty()) null else characters.count + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}