package com.example.notes.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.notes.model.Note

typealias NotesLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Note>

class NotesPagingSource(
    private val loader: NotesLoader,
    private val pageSize: Int
) : PagingSource<Int, Note>() {

    override fun getRefreshKey(state: PagingState<Int, Note>): Int? {
        val position = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(position) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Note> {
        val pageIndex = params.key ?: 0

        return try {
            val notes = loader.invoke(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = notes,
                prevKey = if(pageIndex == 0) null else pageIndex - 1,
                nextKey = if(notes.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )

        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}