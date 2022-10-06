import styled from 'styled-components'

export const CommentsLayout = styled.div`
  margin: 0 20px;
`

export const CommentBox = styled.div`
  height: 120px;
  overflow: scroll;
  -ms-overflow-style: none;
  ::-webkit-scrollbar {
    display: none;
  }
`

export const CommentTitleBox = styled.div`
  margin-bottom: 20px;
  display: flex;
  gap: 5px;
`

export const CommentTitle = styled.div`
  display: flex;
  align-items: center;
  font-size: ${({ theme }) => theme.fontSize.xl};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`

export const CommentCount = styled.div`
  display: flex;
  align-items: center;
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.color.deepGray};
`
