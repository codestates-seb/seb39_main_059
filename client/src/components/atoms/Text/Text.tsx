import { ButtonHTMLAttributes, FC } from 'react'
import { FontSizeTypes, FontWeightTypes, ColorTypes } from '@Styles/theme'
import { CSSProp } from 'styled-components'
import * as S from './Text.style'

export interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  fontSize: keyof FontSizeTypes
  fontWeight: keyof FontWeightTypes
  color: keyof ColorTypes
  cssProp?: CSSProp
  children: React.ReactNode
}

const Text: FC<Props> = ({ children, ...props }) => (
  <S.Text {...props}>{children}</S.Text>
)

export default Text
