import { createAsyncThunk } from '@reduxjs/toolkit'
import { UserInfo } from '@Types/user'
import { axiosInstance } from '@Utils/instance'
import { CreateAsyncThunkTypes } from '../store'

export const signupAsync = createAsyncThunk<
  undefined,
  UserInfo,
  CreateAsyncThunkTypes
>('user/signup', async (payload, thunkAPI) => {
  try {
    const res = await axiosInstance.post('/signup', payload)
    return res.data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
