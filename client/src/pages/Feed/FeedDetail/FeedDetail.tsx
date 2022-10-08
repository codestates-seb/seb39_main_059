import styled from 'styled-components'
import FeedDetailView from '@Template/FeedDetail'
import CommentsView from '@Template/Comments'
import { FC, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { Back } from '@Assets/icons'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import { getFeedDetailAsync } from '@/redux/actions/FeedDetailAction'

export const Header = styled.div`
  display: grid;
  grid-template-columns: 24px auto 24px;
  margin: 15px 20px 12px 20px;
  span {
    margin: auto;
  }
`

const FeedDetailLayout = styled.div``

const FeedDetail: FC = () => {
  const navigate = useNavigate()
  const { id } = useParams()
  const dispatch = useAppDispatch()
  const data = useAppSelector(state => state.feedDetail.feedDetail)

  useEffect(() => {
    dispatch(getFeedDetailAsync(id as string))
  }, [dispatch])

  return (
    <FeedDetailLayout>
      <Header className="header">
        <button type="button" onClick={() => navigate(-1)}>
          <Back />
        </button>
        <span>냥이생활</span>
      </Header>
      <FeedDetailView {...data} />
      <CommentsView {...data} />
    </FeedDetailLayout>
  )
}
export default FeedDetail
