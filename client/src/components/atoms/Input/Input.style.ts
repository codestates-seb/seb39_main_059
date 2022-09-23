import styled, { css, CSSProp } from 'styled-components'

export const Input = styled.input<{ cssProp?: CSSProp }>(
  ({ cssProp, theme }) => css`
    border: none;
    height: 2rem;
    border-radius: 0.625rem;
    padding: 7px 10px 7px 15px;
    background: ${theme.color.softGray};

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
