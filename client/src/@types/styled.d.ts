import 'styled-components'
import { ColorTypes, FontSizeTypes, FontWeightTypes } from '../styles/theme'

declare module 'styled-components' {
  export interface DefaultTheme {
    fontSize: FontSizeTypes
    fontWeight: FontWeightTypes
    color: ColorTypes
  }
}
