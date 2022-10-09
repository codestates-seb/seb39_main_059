import { createAsyncThunk } from '@reduxjs/toolkit'
import { UserInfo, LoginPayload } from '@Types/user'
import { axiosInstance } from '@Utils/instance'
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
        Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    })

    return {
      ...userInfo.data,
    }
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
