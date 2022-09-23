import { ButtonHTMLAttributes, FC } from 'react'
import * as S from './Button.style'
import { FontSizeTypes, FontWeightTypes, ColorTypes } from '@/styles/theme'

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
