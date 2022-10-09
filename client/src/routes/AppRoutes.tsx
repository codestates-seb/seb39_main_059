import DashBoard from '@Layout/DashBoard'
import { Iphone11Pro } from '@Layout/PhoneLayout'
import PresentLayout from '@Layout/PresentLayout'
import Cat from '@pages/Cat'
import Login from '@pages/Login'
import Signup from '@pages/Signup'
import { RouteObject, useRoutes } from 'react-router-dom'
import { boardRoutes } from './board.routes'
import { errorRoutes } from './error.routes'
import { feedRoutes } from './feed.routes'
import { mainRoutes } from './main.routes'

const appRoutes: RouteObject = {
  path: '/',
  // element: <Iphone11Pro />,
  // element: <DashBoard />,
  element: <PresentLayout />,
  children: [
    { path: 'cat', element: <Cat /> },
    mainRoutes,
    { path: 'login', element: <Login /> },
    { path: 'signup', element: <Signup /> },
    feedRoutes,
    boardRoutes,
    errorRoutes,
  ],
}

const AppRoutes = () => {
  const element = useRoutes([appRoutes])
  return element
}

export default AppRoutes
