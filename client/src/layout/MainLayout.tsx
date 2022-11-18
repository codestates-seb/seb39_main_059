import Header from '@Modules/Header'
import TabBar from '@Modules/TabBar'
import { BOARD_PATH } from '@Routes/board.routes'
import { FEED_PATH } from '@Routes/feed.routes'
import { NavLink, Outlet } from 'react-router-dom'
import styled from 'styled-components'

const SNavLayout = styled.div`
  position: relative;
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 50%;
  max-width: 400px;
  min-width: 173px;
  height: 34px;
  margin: 0 auto;

  background: rgba(198, 198, 198, 0.2);
  border-radius: 36px;

  .feeds,
  .boards {
    color: white;
    transition: 1s ease-in-out;
  }

  .feeds ~ .decoreation {
    left: 0%;
  }

  .boards ~ .decoreation {
    left: 50%;
  }
`

const SDecoreation = styled.div`
  position: absolute;
  z-index: -1;
  height: 100%;
  width: 50%;
  text-align: center;
  background: #f8adb2;
  border-radius: 28px;
  left: 50%;

  transition: left 1s;
`

const SMainLayout = styled.div`
  position: relative;
`

const Nav = () => {
  return (
    <SNavLayout>
      <NavLink
        className={({ isActive }) => (isActive ? 'feeds' : '')}
        to={FEED_PATH}
      >
        냥이생활
      </NavLink>
      <NavLink
        className={({ isActive }) => (isActive ? 'boards' : '')}
        to={BOARD_PATH}
      >
        집사생활
      </NavLink>
      <SDecoreation className="decoreation" />
    </SNavLayout>
  )
}

export const MainLayout = () => {
  return (
    <SMainLayout>
      <Header backButtonDisabled>
        <Nav />
      </Header>
      <Outlet />
      <TabBar />
    </SMainLayout>
  )
}
