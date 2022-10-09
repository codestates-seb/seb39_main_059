import { ButtonHTMLAttributes, FC } from 'react'
import { FontSizeTypes, FontWeightTypes, ColorTypes } from '@Styles/theme'
import * as S from './Button.style'

export interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  fontSize: keyof FontSizeTypes
  fontWeight: keyof FontWeightTypes
  color: keyof ColorTypes
  backgroundColor: keyof ColorTypes
  children: React.ReactNode
}

const Button: FC<Props> = ({ children, ...props }) => (
  <S.Button {...props}>{children}</S.Button>
)

export default Button
