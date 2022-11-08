import { createSlice, Reducer, PayloadAction } from '@reduxjs/toolkit'
import { Feed, Feeds } from '@Types/feed'
import {
  getFeedsAsync,
  addLikeAsync,
  cancelLikeAsync,
} from '../actions/FeedsAction'

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

const feedsSlice = createSlice({
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
      // 좋아요 추가하기
      .addCase(addLikeAsync.pending, state => {
        state.isLoading = true
      })
      .addCase(addLikeAsync.fulfilled, (state, { payload }) => {
        const data = state.feed?.find(item => item.feedId === payload) as Feed
        data.isLike = !data.isLike
      })
      .addCase(addLikeAsync.rejected, () => {
        console.error()
      })
      // 좋아요 취소하기
      .addCase(cancelLikeAsync.pending, state => {
        state.isLoading = true
      })
      .addCase(cancelLikeAsync.fulfilled, (state, { payload }) => {
        const data = state.feed?.find(item => item.feedId === payload) as Feed
        data.isLike = !data.isLike
      })
      .addCase(cancelLikeAsync.rejected, () => {
        console.error()
      })
  },
})

export const feedsReducer: Reducer<typeof initialState> = feedsSlice.reducer
