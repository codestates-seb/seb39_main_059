import { ThemeProvider } from 'styled-components'
import { GlobalStyle } from './styles/GlobalStyle'
import theme from './styles/theme'
import { Iphone11ProContainer } from './styles/Iphone11ProContainer'

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Iphone11ProContainer>{/* 하위 컴포넌트 */}</Iphone11ProContainer>
    </ThemeProvider>
  )
}

export default App
