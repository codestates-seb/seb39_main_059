import { createAsyncThunk } from '@reduxjs/toolkit'
import { axiosInstance } from '@Utils/instance'
import { FEED_PATH } from '@Routes/feed.routes'
import { Feeds } from '@Types/feed'
import { CreateAsyncThunkTypes } from '../store'

export const getFeedsAsync = createAsyncThunk<
  Feeds,
  undefined,
  CreateAsyncThunkTypes
>('feeds/getFeedsAsync', async (_, thunkAPI) => {
  try {
    // thunkAPI.getState()로 현재 state값을 조회할 수 있습니다.
    const { page, pageSize } = thunkAPI.getState().feeds
    const { data } = await axiosInstance.get(
      `/${FEED_PATH}?page=${page}&size=${pageSize}`,
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
        },
      },
    )
    return data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
