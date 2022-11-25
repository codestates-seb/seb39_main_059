import { createAsyncThunk } from '@reduxjs/toolkit'
import { axiosInstance } from '@Utils/instance'
import { FEED_PATH } from '@Routes/feed.routes'
import { FeedDetail } from '@Types/feed'
import { CreateAsyncThunkTypes } from '../store'

export const getFeedDetailAsync = createAsyncThunk<
  FeedDetail,
  string,
  CreateAsyncThunkTypes
>('feedDetail/getFeedDetailAsync', async (payload, thunkAPI) => {
  try {
    const { data } = await axiosInstance.get(`/${FEED_PATH}/${payload}`, {
      headers: {
        tokenNeeded: true
      },
    })
    return data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
