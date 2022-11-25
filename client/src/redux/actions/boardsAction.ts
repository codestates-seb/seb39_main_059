import { createAsyncThunk } from '@reduxjs/toolkit'
import { axiosInstance } from '@Utils/instance'
import { BOARD_PATH } from '@Routes/board.routes'
import { Boards } from '@Types/board'
import { CreateAsyncThunkTypes } from '../store'

export const getBoardsAsync = createAsyncThunk<
  Boards,
  undefined,
  CreateAsyncThunkTypes
>('boards/getBoardsAsync', async (_, thunkAPI) => {
  try {
    const { page, pageSize } = thunkAPI.getState().boards
    // --------------------------------------
    const { data } = await axiosInstance.get(
      `/${BOARD_PATH}?page=${page}&size=${pageSize}`,
      {
        headers: {
          tokenNeeded: true
        },
      },
    )
    return data
  } catch (error: any) {
    return thunkAPI.rejectWithValue(error.message)
  }
})
