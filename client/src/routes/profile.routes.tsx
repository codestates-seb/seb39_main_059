import { lazy } from 'react'
import { RouteObject } from 'react-router-dom'

const Profile = lazy(() => import('@pages/Profile'))

export const profileRoutes: RouteObject = {
  path: 'profile',
  children: [
    { index: true, element: <Profile /> },
    // { path: 'my', element: <BoardDetail /> },
  ],
}
