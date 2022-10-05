import { BOARD_PATH } from '@Routes/board.routes'
import { FEED_PATH } from '@Routes/feed.routes'
import { ChangeEvent, useState } from 'react'
import { Link, Outlet } from 'react-router-dom'
import styled from 'styled-components'
import * as phoneLayouts from './PhoneLayout'

const NavBar = styled.nav`
  background-color: beige;
  padding: 10px 10px;
  display: flex;
  flex-direction: column;
  gap: 5px;
`

const DashBoardLayout = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

type PhoneLayouts = {
  [Property in keyof typeof phoneLayouts as string]: typeof phoneLayouts[Property]
}

const DashBoard = () => {
  const layoutList = Object.keys(phoneLayouts)
  const [Selected, setSelected] = useState(layoutList[0])

  const onChangeHandler = (event: ChangeEvent<HTMLSelectElement>) => {
    setSelected(event.target.value)
  }

  const PhoneLayout = (phoneLayouts as PhoneLayouts)[Selected]
  return (
    <DashBoardLayout>
      <NavBar>
        <select onChange={onChangeHandler} value={Selected}>
          {layoutList.map(layoutName => (
            <option key={layoutName}>{layoutName}</option>
          ))}
        </select>
        <Link to="cat">cat</Link>
        <Link to="login">login</Link>
        <Link to="signup">signup</Link>
        <Link to={FEED_PATH}>feed</Link>
        <Link to={BOARD_PATH}>Board</Link>
      </NavBar>
      <PhoneLayout>
        <Outlet />
      </PhoneLayout>
    </DashBoardLayout>
  )
}
export default DashBoard
