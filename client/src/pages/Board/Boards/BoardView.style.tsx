import Text from '@Atoms/Text'
import styled, { css } from 'styled-components'

export const BoardViewLayout = styled.div`
  height: 97px;
  padding: 20px;
  display: flex;
  gap: 30px;
  border-bottom: 1px solid #838383;
`

export const TitleText = styled.div(
  ({ theme }) => css`
    font-size: ${theme.fontSize.lg};
    font-weight: ${theme.fontWeight.bold};
  `,
)

export const InfoText = styled.span<{ bold?: unknown }>(
  ({ theme, bold }) => css`
    font-size: ${bold ? theme.fontSize.xs : theme.fontSize.sm};
    font-weight: ${bold ? theme.fontWeight.bold : theme.fontWeight.regular};
    color: ${bold ? theme.color.black : theme.color.deepGray};
  `,
)

export const InfoBox = styled.div`
  display: flex;
  gap: 9px;
`
export const TextBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 8px;
  flex-grow: 1;
`

export const ImageView = styled.img`
  width: 57px;
  height: 57px;
  object-fit: cover;
  border-radius: 10px;
`
