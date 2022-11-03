import styled, { css } from 'styled-components'

export const TabLayout = styled.div`
  display: flex;
  flex-direction: row;
  & > div:first-child {
    padding-left: 20px;
  }
`

interface TabMenu {
  selected: unknown
}
export const TabMenu = styled.div<TabMenu>(
  ({ theme, selected }) => css`
    padding: 13px 11px;
    border-bottom: 3px solid ${selected ? theme.color.primary : 'white'};
    font-size: ${theme.fontSize.lg};
    font-weight: ${selected ? theme.fontWeight.bold : theme.fontWeight.regular};
    cursor: pointer;
  `,
)
