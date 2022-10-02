import { MainLayout } from '@Layout/MainLayout'
import Boards from '@pages/Board/Boards'
import Feeds from '@pages/Feed/Feeds'
import { Navigate, RouteObject } from 'react-router-dom'
import { BOARD_PATH } from './board.routes'
import { FEED_PATH } from './feed.routes'

export const mainRoutes: RouteObject = {
  path: '',
  element: <MainLayout />,
  children: [
    { index: true, element: <Navigate to={FEED_PATH} /> },
    { path: FEED_PATH, element: <Feeds /> },
    { path: BOARD_PATH, element: <Boards /> },
  ],
}
