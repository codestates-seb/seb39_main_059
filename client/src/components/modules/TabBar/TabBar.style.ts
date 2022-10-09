import styled, { css } from 'styled-components'

export const TabBarLayout = styled.nav(
  ({ theme }) => css`
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: 0px 25px;
    width: 300px;
    height: 50px;
    background-color: ${theme.color.white};
    box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.25);
    border-radius: 43px;
    position: sticky;
    left: calc(50% - 300px / 2);
    bottom: 30px; // Iphone11 에서는 30px
  `,
)

export const IconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
`
