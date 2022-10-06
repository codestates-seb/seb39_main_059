import { Outlet } from 'react-router-dom'
import styled from 'styled-components'
import * as phoneLayouts from './PhoneLayout'

const BorderBox = styled.div`
  border-radius: 10px;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 1px 2px, rgba(0, 0, 0, 0.07) 0px 2px 4px,
    rgba(0, 0, 0, 0.07) 0px 4px 8px, rgba(0, 0, 0, 0.07) 0px 8px 16px,
    rgba(0, 0, 0, 0.07) 0px 16px 32px, rgba(0, 0, 0, 0.07) 0px 32px 64px;
`

const DashBoardLayout = styled.div`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const PresentLayout = () => {
  const PhoneLayout = phoneLayouts.Iphone11Pro
  return (
    <DashBoardLayout>
      <BorderBox>
        <PhoneLayout>
          <Outlet />
        </PhoneLayout>
      </BorderBox>
    </DashBoardLayout>
  )
}
export default PresentLayout
