import styled from 'styled-components'

export const TabBarLayout = styled.nav`
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding: 0px 25px;
  width: 300px;
  height: 50px;
  box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.25);
  border-radius: 43px;
  position: absolute;
  left: calc(50% - 300px / 2);
  bottom: 3.5%; // Iphone11 에서는 30px
`

export const IconBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: 50px;
`
