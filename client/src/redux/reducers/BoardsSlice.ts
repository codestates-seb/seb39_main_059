import { createSlice, Reducer } from '@reduxjs/toolkit'
import { Boards } from '@Types/board'
import { getBoardsAsync } from '../actions/boardsAction'
import { getFeedsAsync } from '../actions/FeedsAction'

const initialState: Boards = {
  items: [],
  page: 1,
  pageSize: 20,
  totalElements: 0,
  totalPages: 0,
  hasMoreFeed: true,
  isLoading: false,
}

const boardsSlice = createSlice({
  name: 'boards',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      // 전체 게시글 불러오기
      .addCase(getBoardsAsync.pending, state => {
        state.isLoading = true
      })
      .addCase(getBoardsAsync.fulfilled, (state, { payload }) => {
        const { items } = payload
        state.isLoading = false
        state.items = items
        state.totalElements = payload.totalElements
        state.totalPages = payload.totalPages
      })
      .addCase(getFeedsAsync.rejected, () => {
        alert('피드를 불러오는 데 실패했습니다:(')
      })
  },
})

export const boardsReducer: Reducer<typeof initialState> = boardsSlice.reducer