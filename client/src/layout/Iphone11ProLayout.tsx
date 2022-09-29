import { Outlet } from 'react-router-dom'
import styled from 'styled-components'

export const Iphone11ProBox = styled.div`
  border: 3px solid black;
  width: 375px;
  height: 812px;
  overflow: scroll;
  -ms-overflow-style: none;
  &::-webkit-scrollbar {
    display: none;
  }
`

export const Iphone11ProLayout = () => (
  <Iphone11ProBox>
    <Outlet />
  </Iphone11ProBox>
)
