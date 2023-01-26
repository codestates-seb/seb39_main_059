import styled, { css } from 'styled-components'

export const FeedsLayout = styled.div`
  margin: 0px 100px;
  display: flex;
  flex-direction: column;
`

export const FollowCatBox = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
`

export const FeedBox = styled.ul`
  column-count: 3;

  @media screen and (max-width: 768px) {
    column-count: 2;
  }
`

export const FeedItem = styled.li`
  position: relative;
  margin-bottom: 20px;
  cursor: pointer;
  > * {
    border-radius: 10px;
  }
`
export const SvgButtonCssProp = css`
  position: absolute;
  right: 8px;
  top: 8px;
  > * {
    stroke-opacity: 0.3;
    :hover {
      stroke-width: 0;
      fill: red;
    }
  }
`

export const AvatarCssProp = css`
  cursor: pointer;
`
