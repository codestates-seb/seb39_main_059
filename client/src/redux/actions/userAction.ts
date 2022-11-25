import { renewAccessToken } from '@Api/auth'
import { createAsyncThunk } from '@reduxjs/toolkit'
import { UserInfo, LoginPayload } from '@Types/user'
import { axiosInstance } from '@Utils/instance'
import { isTokenExpired, retrieveUserToken } from '@Utils/tokenControl'
import { CreateAsyncThunkTypes } from '../store'

export const signupAsync = createAsyncThunk<
  undefined,
  UserInfo,
  CreateAsyncThunkTypes
>('user/signupAsync', async (payload, thunkAPI) => {
  try {
    const { data } = await axiosInstance.post('/signup', payload)
    return data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})

export const loginAsync = createAsyncThunk<
  UserInfo,
  LoginPayload,
  CreateAsyncThunkTypes
>('user/loginAsync', async (payload, thunkAPI) => {
  try {
    const { data } = await axiosInstance.post('/login', payload)

    if (data) {
      localStorage.setItem('ACCESS_TOKEN', data.accessToken)
      localStorage.setItem('REFRESH_TOKEN', data.refreshToken)
    }

    const userInfo = await axiosInstance.get('/users/my-info', {
      headers: {
        tokenNeeded: true,
      },
    })

    return {
      ...userInfo.data,
    }
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})

export const logoutAsync = createAsyncThunk<
  undefined,
  undefined,
  CreateAsyncThunkTypes
>('user/logoutAsync', async (_, thunkAPI) => {
  try {
    let payload = {
      accessToken: retrieveUserToken('ACCESS_TOKEN'),
      refreshToken: retrieveUserToken('REFRESH_TOKEN'),
    }

    if (isTokenExpired(payload.accessToken!)) {
      const {
        renewedAccessToken: accessToken,
        renewedRefreshToken: refreshToken,
      } = await renewAccessToken(payload.refreshToken!)

      payload = { accessToken, refreshToken }
    }

    const { data } = await axiosInstance.post('/logout', payload, {
      headers: {
        tokenNeeded: true,
      },
    })
    
    localStorage.clear()

    return data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
