import { ThemeProvider } from 'styled-components'
import { GlobalStyle } from '@Styles/GlobalStyle'
import theme from '@Styles/theme'
import { Iphone11ProContainer } from '@Styles/Iphone11ProContainer'

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <Iphone11ProContainer>{/* 하위 컴포넌트 */}</Iphone11ProContainer>
    </ThemeProvider>
  )
}

export default App
