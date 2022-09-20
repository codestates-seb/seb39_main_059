import 'styled-components'
import { ColorsTypes, FontSizeTypes, fontWeightTypes } from '../styles/theme'

declare module 'styled-components' {
  export interface DefaultTheme {
    fontSize: FontSizeTypes
    fontWeight: fontWeightTypes
    color: ColorsTypes
  }
}
