import { lazy } from 'react'
import { RouteObject } from 'react-router-dom'

const Feed = lazy(() => import('@pages/Feed/Feeds'))
const FeedDetail = lazy(() => import('@pages/Feed/FeedDetail'))
const NewFeed = lazy(() => import('@pages/Feed/NewFeed'))
const EditFeed = lazy(() => import('@pages/Feed/EditFeed'))

export const FEED_PATH = '%EB%83%A5%EC%9D%B4%EC%83%9D%ED%99%9C'

export const feedRoutes: RouteObject = {
  path: FEED_PATH,
  children: [
    { index: true, element: <Feed /> },
    { path: ':id', element: <FeedDetail /> },
    { path: 'new', element: <NewFeed /> },
    {
      path: 'edit',
      children: [{ path: ':id', element: <EditFeed /> }],
    },
  ],
}