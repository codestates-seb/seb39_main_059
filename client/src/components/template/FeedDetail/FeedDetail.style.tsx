import styled from 'styled-components'

export const FeedDetailLayout = styled.div`
  margin: 0px 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
`

export const UserInfoBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`

// TextArea로 수정
export const FeedBodyText = styled.p`
  //styleName: Text/Regular/15;
  font-size: 15px;
  font-weight: ${({ theme }) => theme.fontWeight.thin};
  line-height: 20px;
  letter-spacing: -0.40799999237060547px;
  text-align: left;
  padding: 10px 0;
`

export const TagBox = styled.div`
  margin-bottom: 20px;
  display: flex;
  justify-content: flex-start;
  gap: 10px;
  align-items: center;
`

export const Tag = styled.div`
  background-color: ${({ theme }) => theme.color.primary};
  color: ${({ theme }) => theme.color.white};
  font-size: ${({ theme }) => theme.fontSize.xs};
  width: content;
  padding: 5px 10px;
  border-radius: 30px;
`
