import styled from 'styled-components'
import { Props } from './Button'

export const Button = styled.button<Props>`
  padding: 0.25rem 0.5rem;
  border-radius: 0.875rem;
  color: ${({ color, theme }) => theme.color[color]};
  background-color: ${({ backgroundColor, theme }) => theme.color[backgroundColor]};
  font-size: ${({ fontSize, theme }) => theme.fontSize[fontSize]};
  font-weight: ${({ fontWeight, theme }) => theme.fontWeight[fontWeight]};
  opacity: 0.5;
  &:hover {
    opacity: 1;
  }
`
