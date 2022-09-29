import { BOARD_PATH } from '@Routes/board.routes'
import { FEED_PATH } from '@Routes/feed.routes'
import { Link, Outlet } from 'react-router-dom'
import styled from 'styled-components'
import { Iphone11ProBox } from './Iphone11ProLayout'



const NavBar = styled.nav`
  background-color: beige;
  padding: 10px 10px;
  display: flex;
  flex-direction: column;
  gap: 5px;
`

const DashBoardBox = styled.div`
  display: flex;
`

const DashBoard = () => {
  return (
    <DashBoardBox>
      <NavBar>
        <Link to="login">login</Link>
        <Link to="signup">signup</Link>
        <Link to={FEED_PATH}>feed</Link>
        <Link to={BOARD_PATH}>Board</Link>
      </NavBar>
      <Iphone11ProBox>
        <Outlet />
      </Iphone11ProBox>
    </DashBoardBox>
  )
}
export default DashBoard
