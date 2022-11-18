import styled, { css } from 'styled-components'

export const TextBox = styled.div`
  display: inline-block;
  width: 100%;
  text-align: center;
  padding: 0px 50px;
`

export const HeaderLayout = styled.div<React.CSSProperties>(
  ({ theme }) => css`
    background-color: ${theme.color.primary};
    height: 52px;
  `,
)
export const HeaderBox = styled.div<React.CSSProperties>(
  ({ theme }) => css`
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    background-color: ${theme.color.white};
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 20px 12px 20px;
    z-index: 100;
  `,
)
export const ButtonBox = styled.div`
  width: 24px;
`

export const MarginBox = styled.div`
  height: 52px;
`
