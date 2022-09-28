import styled, { css, CSSProp } from 'styled-components'

export const TextArea = styled.textarea<{ cssProp?: CSSProp }>(
  ({ cssProp, theme }) => css`
    background-color: transparent;
    border: none;
    resize: none;
    &:focus-visible {
      outline: none;
    }
    &::placeholder {
      font-size: ${theme.fontSize.sm};
      font-weight: ${theme.fontWeight.regular};
      line-height: 15px;
      text-align: left;

      color: ${theme.color.deepGray};
    }
    ${cssProp}
  `,
)
