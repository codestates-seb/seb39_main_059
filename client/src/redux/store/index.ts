import {
  configureStore,
  combineReducers,
  PreloadedState,
} from '@reduxjs/toolkit'
import { useDispatch, useSelector, TypedUseSelectorHook } from 'react-redux'

import { userReducer } from '../reducers/userSlice'

const rootReducer = combineReducers({
  /* 슬라이스로 생성한 리듀서는 여기에 추가하면 됩니다. */
  user: userReducer,
})

export const setupStore = (preloadedState?: PreloadedState<RootState>) => {
  return configureStore({
    reducer: rootReducer,
    preloadedState,
  })
}

export const useAppDispatch: () => AppDispatch = useDispatch
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector

export type RootState = ReturnType<typeof rootReducer>
export type AppStore = ReturnType<typeof setupStore>
export type AppDispatch = AppStore['dispatch']

export type CreateAsyncThunkTypes = {
  dispatch: AppDispatch
  state: RootState
  rejectValue: string
}
