import { CameraIcon, HeartIcon } from '@Assets/icons'
import { BOARD_PATH } from '@Routes/board.routes'
import { FEED_PATH } from '@Routes/feed.routes'
import { NavLink, Outlet } from 'react-router-dom'
import styled from 'styled-components'

const SNavLayout = styled.div`
  position: relative;
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 173px;
  height: 34px;
  margin: 15px auto 12px auto;

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

const STapBarLayout = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  padding: 0px 25px;

  position: absolute;
  width: 300px;
  height: 50px;
  left: calc(50% - 300px / 2 - 0.5px);
  bottom: 41px;

  background: #ffffff;
  box-shadow: 0px 1px 4px rgba(0, 0, 0, 0.25);
  border-radius: 43px;
`

const TapBar = () => {
  return (
    <STapBarLayout>
      <NavLink to={FEED_PATH}>
        <CameraIcon />
      </NavLink>
      <NavLink to="login">
        <HeartIcon />
      </NavLink>
    </STapBarLayout>
  )
}

export const MainLayout = () => {
  return (
    <>
      <Nav />
      <Outlet />
      <TapBar />
    </>
  )
}
