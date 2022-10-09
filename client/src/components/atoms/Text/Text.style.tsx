import styled, { css } from 'styled-components'
import { Props } from './Text'

export const Text = styled.button<Props>(
  ({ color, theme, fontSize, fontWeight }) => css`
    color: ${theme.color[color]};
    font-size: ${theme.fontSize[fontSize]};
    font-weight: ${theme.fontWeight[fontWeight]};
  `,
)
