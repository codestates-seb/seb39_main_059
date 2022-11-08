import { Suspense } from 'react'
import { BrowserRouter } from 'react-router-dom'
import { ThemeProvider } from 'styled-components'
import { GlobalStyle } from '@Styles/GlobalStyle'
import theme from '@Styles/theme'
import AppRoutes from './routes'

const loading = <div>화면을 불러오는 중 입니다.</div>

const App = () => {
  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <GlobalStyle />
        <Suspense fallback={loading}>
          <AppRoutes />
        </Suspense>
      </ThemeProvider>
    </BrowserRouter>
  )
}

export default App
