import { FC } from 'react'
import { Outlet } from 'react-router-dom'
import styled, { css, CSSProp } from 'styled-components'

const phoneSize = {
  Iphone11Pro: {
    width: '375px',
    height: '812px',
  },
  IphoneSE: {
    width: '320px',
    height: '568px',
  },
  Iphone11ProMax: {
    width: '414px',
    height: '896px',
  },
}

const PhoneBox = styled.div<{ cssProp?: CSSProp }>(
  ({ cssProp }) => css`
    position: relative;
    border: 3px solid black;
    width: 100%;
    height: 100%;
    overflow: scroll;
    -ms-overflow-style: none;
    &::-webkit-scrollbar {
      display: none;
    }
    ${cssProp}
  `,
)

const phoneLayout = (cssProp: CSSProp) =>
  function PhoneLayout() {
    return (
      <PhoneBox cssProp={cssProp}>
        <Outlet />
      </PhoneBox>
    )
  }

export const Iphone11Pro: FC<{ children: JSX.Element }> = phoneLayout(
  css(phoneSize.Iphone11Pro),
)

export const Iphone11Max: FC<{ children: JSX.Element }> = phoneLayout(
  css(phoneSize.Iphone11ProMax),
)

export const IphoneSE: FC<{ children: JSX.Element }> = phoneLayout(
  css(phoneSize.IphoneSE),
)

const CustomPhoneLayout = styled.div`
  /* default: Iphone11Pro */
  width: 375px;
  height: 812px;
  resize: both;
  overflow: auto;
`

export const CustomPhone: FC<{ children: JSX.Element }> = () => {
  return (
    <CustomPhoneLayout>
      <PhoneBox>
        <Outlet />
      </PhoneBox>
    </CustomPhoneLayout>
  )
}
