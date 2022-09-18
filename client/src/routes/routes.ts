import React from 'react'

const Home = React.lazy(() => import('../pages/Home'))
const Page404 = React.lazy(() => import('../pages/Page404'))
const Page500 = React.lazy(() => import('../pages/Page500'))

const routes = [,
    {path: '/', name: 'Home', element: Home},
    {path: '/404', name: '404', element: Page404},
    {path: '/500', name: '500', element: Page500},
]

export default routes