import styled, { css, CSSProp } from 'styled-components'

export const SvgButtonBox = styled.div<{
  isClickable: boolean
  cssProp?: CSSProp
}>(
  ({ cssProp, isClickable }) => css`
    display: inline-block;
    transition: opacity 0.5s;
    ${isClickable &&
    css`
      cursor: pointer;
      :hover {
        opacity: 0.5;
      }
    `}
    ${cssProp}
  `,
)
