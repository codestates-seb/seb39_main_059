import { createSlice, Reducer } from '@reduxjs/toolkit'
import { UserInitialState } from '@Types/user'
import { signupAsync, loginAsync, logoutAsync } from '../actions/userAction'

const initialState: UserInitialState = {
  isLoading: true,
  isSignup: false,
  isLogin: false,
  userInfo: {
    email: '',
    password: '',
    location: null,
    name: '',
    profileImage: null,
    userId: '',
  },
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
      // 로그인
      .addCase(loginAsync.pending, state => {
        state.isSignup = false
      })
      .addCase(loginAsync.fulfilled, (state, { payload }) => {
        state.isLoading = false
        state.isSignup = true
        state.isLogin = true
        state.userInfo = payload
        alert('로그인 성공!')
      })
      .addCase(loginAsync.rejected, state => {
        state.isLoading = false
        alert('로그인 실패')
      })
      // 로그아웃
      .addCase(logoutAsync.pending, state => {
        state.isLogin = true
      })
      .addCase(logoutAsync.fulfilled, state => {
        state.isLoading= true
        state.isSignup= false
        state.isLogin= false
        state.userInfo= {
          email: '',
          password: '',
          location: null,
          name: '',
          profileImage: null,
          userId: '',
        }
        alert('로그아웃 성공!')
      })
      .addCase(logoutAsync.rejected, state => {
        state.isLogin = true
        alert('로그아웃 실패')
      })
  },
})
export const userReducer: Reducer<typeof initialState> = userSlice.reducer
