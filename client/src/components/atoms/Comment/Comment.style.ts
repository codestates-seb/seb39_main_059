import styled, { css } from 'styled-components'

export const Commentlayout = styled.li`
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  vertical-align: bottom;
  gap: 7px;
`

export const AvatarBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
`

export const ContentBox = styled.div`
  padding-top: 5px;
  font-size: ${({ theme }) => theme.fontSize.sm};
  letter-spacing: -0.40799999237060547px;
  line-height: 1.2;
  flex: 1;
`

export const AvatarCssProp = css`
  width: content;
  justify-content: center;
`

export const SvgButtonCssProp = css`
  padding-top: 5px;
  width: content;
  justify-content: center;
  align-items: center;
  > * {
    stroke-opacity: 0.3;
    :hover {
      stroke-width: 0;
      fill: red;
      cursor: pointer;
    }
  }
`
