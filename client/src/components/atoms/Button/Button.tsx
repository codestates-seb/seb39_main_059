import { ButtonHTMLAttributes, PropsWithChildren } from 'react'
import * as S from './Button.style'

export interface Props extends ButtonHTMLAttributes<HTMLButtonElement> {
  fontSize: 'xs' | 'sm' | 'md' | 'lg' | 'xl'
  fontWeight: 'regular' | 'bold'
  color: 'primary' | 'black' | 'deepGray' | 'softGray' | 'white' | 'red' | 'yellow'
  backgroundColor: 'primary' | 'black' | 'deepGray' | 'softGray' | 'white' | 'red' | 'yellow'
  children: React.ReactNode
}

const Button = ({ children, ...props }: PropsWithChildren<Props>): JSX.Element => (
  <S.Button {...props}>{children}</S.Button>
)

export default Button
