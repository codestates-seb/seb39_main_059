import { createSlice, Reducer } from '@reduxjs/toolkit'
import { FeedDetailInitialState } from '@Types/feed'
import { getFeedDetailAsync } from '../actions/FeedDetailAction'

const initialState: FeedDetailInitialState = {
  feedDetail: {
    body: '',
    catId: 0,
    comments: [],
    feedId: 0,
    isLike: false,
    likeCount: 0,
    name: '',
    pictures: [],
    profileImage: '',
    tags: [],
  },
  isLoading: false,
}

const feedDetailSlice = createSlice({
  name: 'feedDetail',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      // 특정 피드 불러오기
      .addCase(getFeedDetailAsync.pending, state => {
        state.isLoading = true
      })
      .addCase(getFeedDetailAsync.fulfilled, (state, { payload }) => {
        state.isLoading = false
        state.feedDetail = payload
      })
      .addCase(getFeedDetailAsync.rejected, () => {
        alert('피드를 불러오는 데 실패했습니다:(')
      })
  },
})

export const feedDetailReducer: Reducer<typeof initialState> =
  feedDetailSlice.reducer
