import styled from 'styled-components'
import { FEED_PATH } from '@Routes/feed.routes'
import { Link } from 'react-router-dom'

const NotFoundPageLayout = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 20px;
  background-color: ${({ theme }) => theme.color.white};
  color: ${({ theme }) => theme.color.primary};
`

const NotFoundContent = styled.div`
  font-size: ${({ theme }) => theme.fontSize.xl};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
`

const Page404 = () => {
  return (
    <NotFoundPageLayout>
      <NotFoundContent>서비스 준비중입니다!🐱</NotFoundContent>
      <Link to={`/${FEED_PATH}`}>
        <div>👉 뒤로 가기</div>
      </Link>
    </NotFoundPageLayout>
  )
}

export default Page404
