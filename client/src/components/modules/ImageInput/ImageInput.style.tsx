import styled from 'styled-components'

export const ImgLabel = styled.label`
  display: flex;
  width: 86px;
  height: 85px;
  justify-content: center;
  align-items: center;

  background: #ffffff;
  box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
  border-radius: 10px;
  cursor: pointer;
`

export const HiddenInput = styled.input`
  display: none;
`

// export const Input = styled.input<{ cssProp?: CSSProp }>(
//   ({ cssProp, theme }) => css`
//     border: none;
//     height: 2rem;
//     border-radius: 0.625rem;
//     padding: 7px 10px 7px 15px;
//     background: ${theme.color.softGray};

//     &::placeholder {
//       font-size: ${theme.fontSize.sm};
//       font-weight: ${theme.fontWeight.regular};
//       line-height: 15px;
//       text-align: left;

//       color: ${theme.color.deepGray};
//     }
//     ${cssProp}
//   `,
// )
