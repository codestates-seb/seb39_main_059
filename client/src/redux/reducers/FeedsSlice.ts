import { createSlice, Reducer } from '@reduxjs/toolkit'
import { Feeds } from '@Types/feed'
import { getFeedsAsync } from '../actions/FeedsAction'

const initialState: Feeds = {
  feed: [],
  follow: [],
  page: 1,
  pageSize: 20,
  totalElements: 0,
  totalPages: 0,
  hasMoreFeed: true,
  isLoading: false,
}

const userSlice = createSlice({
  name: 'feeds',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      // 전체 피드 불러오기
      .addCase(getFeedsAsync.pending, state => {
        state.isLoading = true
      })
      .addCase(getFeedsAsync.fulfilled, (state, { payload }) => {
        const { feed, follow } = payload
        state.isLoading = false
        state.feed = feed
        state.follow = follow
        state.totalElements = payload.totalElements
        state.totalPages = payload.totalPages
      })
      .addCase(getFeedsAsync.rejected, () => {
        alert('피드를 불러오는 데 실패했습니다:(')
      })
  },
})

export const feedsReducer: Reducer<typeof initialState> = userSlice.reducer
