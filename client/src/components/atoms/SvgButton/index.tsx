import styled, { css, CSSProp } from 'styled-components'
import * as icons from '@Assets/icons'

export type IconType = keyof typeof icons

export const iconTypes = Object.keys(icons) as IconType[]

export type Props = {
  /** 사용 할 아이콘 타입 */
  icon: IconType
  cssProp?: CSSProp
  /** 클릭 이벤트 핸들러 */
  onClick?: () => void
}

const SvgIconBox = styled.div<{ isClickable: boolean; cssProp?: CSSProp }>(
  ({ cssProp, isClickable }) => css`
    display: inline-block;
    transition: opacity 0.5s;
    ${isClickable &&
    css`
      cursor: pointer;
      :hover {
        opacity: 0.5;
      }
    `}
    ${cssProp}
  `,
)

const SvgIcon = ({ icon, cssProp, onClick }: Props) => {
  const SVGIcon = icons[icon]

  return (
    <SvgIconBox isClickable={!!onClick} onClick={onClick} cssProp={cssProp}>
      <SVGIcon style={{ margin: '0px' }} />
    </SvgIconBox>
  )
}

export default SvgIcon
