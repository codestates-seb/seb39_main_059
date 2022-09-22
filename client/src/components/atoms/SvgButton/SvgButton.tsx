import { CSSProp } from 'styled-components'
import * as icons from '@Assets/icons'
import * as S from './SvgButton.style'

export type IconType = keyof typeof icons

export const iconTypes = Object.keys(icons) as IconType[]

export type Props = {
  /** 사용 할 아이콘 타입 */
  icon: IconType
  cssProp?: CSSProp
  /** 클릭 이벤트 핸들러 */
  onClick?: () => void
}

const SvgIcon = ({ icon, cssProp, onClick }: Props) => {
  const SVGIcon = icons[icon]

  return (
    <S.SvgIconBox isClickable={!!onClick} onClick={onClick} cssProp={cssProp}>
      <SVGIcon style={{ margin: '0px' }} />
    </S.SvgIconBox>
  )
}

export default SvgIcon
