import { lazy } from 'react'
import { RouteObject } from 'react-router-dom'

// const Boards = lazy(() => import('@pages/Board/Boards'))
const BoardDetail = lazy(() => import('@pages/Board/BoardDetail'))
const NewBoard = lazy(() => import('@pages/Board/NewBoard'))
const EditBoard = lazy(() => import('@pages/Board/EditBoard'))

export const BOARD_PATH = '%EC%A7%91%EC%82%AC%EC%83%9D%ED%99%9C'

export const boardRoutes: RouteObject = {
  path: BOARD_PATH,
  children: [
    // { index: true, element: <Boards /> },
    { path: ':id', element: <BoardDetail /> },
    { path: 'new', element: <NewBoard /> },
    {
      path: 'edit',
      children: [{ path: ':id', element: <EditBoard /> }],
    },
  ],
}
