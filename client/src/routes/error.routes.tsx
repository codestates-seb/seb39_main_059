import { lazy } from 'react'
import { RouteObject } from 'react-router-dom'

const Page404 = lazy(() => import('@pages/Page404'))
const Page500 = lazy(() => import('@pages/Page500'))


export const errorRoutes: RouteObject = {
  children: [
    { path: '/500', element: <Page500 /> },
    { path: '/*', element: <Page404 /> },
  ],
}
