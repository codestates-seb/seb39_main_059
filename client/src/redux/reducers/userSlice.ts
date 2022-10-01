import { createSlice, Reducer } from '@reduxjs/toolkit'
import { User } from '@Types/user'
import { signupAsync } from '../actions/userAction'

const initialState: User = {
  isLoading: true,
  isSignup: false,
  userInfo: null,
}

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {},
  extraReducers: builder => {
    builder
      // 회원가입
      .addCase(signupAsync.pending, state => {
        state.isSignup = false
      })
      .addCase(signupAsync.fulfilled, state => {
        state.isLoading = false
        state.isSignup = true
        alert('회원가입에 성공했습니다.')
      })
      .addCase(signupAsync.rejected, state => {
        state.isLoading = false
        alert('회원가입에 실패했습니다.')
      })
  },
})
export const userReducer: Reducer<typeof initialState> = userSlice.reducer
