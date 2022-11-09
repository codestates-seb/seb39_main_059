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

// -----------------------------------------
/* 요구
page:int
size:int
피드 조회 성공시
const res = {
  feed: [
    {
      feedId: 0,
      image: 'string',
      isLike: true,
    },
  ],
  follow: [
    {
      catId: 0,
      profileImage: 'string',
    },
  ],
  page: 0,
  pageSize: 0,
  totalElements: 0,
  totalPages: 0,
} */

// const initialState: Feeds = {
//   feed: [],
//   follow: [],
//   page: 1,
//   pageSize: 20,
//   totalElements: 0,
//   totalPages: 0,
//   hasMoreFeed: true,
//   isLoading: false,
// }
// -----------------------------------------

const boardSlice = createSlice({
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

// -----------------------------------------
// const userSlice = createSlice({
//   name: 'feeds',
//   initialState,
//   reducers: {},
//   extraReducers: builder => {
//     builder
//       // 전체 피드 불러오기
//       .addCase(getFeedsAsync.pending, state => {
//         state.isLoading = true
//       })
//       .addCase(getFeedsAsync.fulfilled, (state, { payload }) => {
//         const { feed, follow } = payload
//         state.isLoading = false
//         state.feed = feed
//         state.follow = follow
//         state.totalElements = payload.totalElements
//         state.totalPages = payload.totalPages
//       })
//       .addCase(getFeedsAsync.rejected, () => {
//         alert('피드를 불러오는 데 실패했습니다:(')
//       })
//   },
// })
// -----------------------------------------

export const boardsReducer: Reducer<typeof initialState> = boardSlice.reducer