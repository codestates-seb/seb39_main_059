import { Iphone11ProLayout } from '@Layout/Iphone11ProLayout'
import Login from '@pages/Login'
import Signup from '@pages/Signup'
import { RouteObject, useRoutes } from 'react-router-dom'
import { BoardRoutes } from './board.routes'
import { errorRoutes } from './error.routes'
import { feedRoutes } from './feed.routes'

const appRoutes: RouteObject = {
  path: '/',
  element: <Iphone11ProLayout />,
  children: [
    {path: 'login', element:<Login/>},
    {path: 'signup', element:<Signup/>},
    feedRoutes, 
    BoardRoutes, 
    errorRoutes
  ],
}

const AppRoutes = () => {
  const element = useRoutes([appRoutes])
  return element
}

export default AppRoutes
