import styled from 'styled-components'
import FeedDetailView from '@Template/FeedDetail'
import CommentsView from '@Template/Comments'
import { FC, useEffect } from 'react'
import { useParams } from 'react-router-dom'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import { getFeedDetailAsync } from '@/redux/actions/FeedDetailAction'

const FeedDetailLayout = styled.div``

const FeedDetail: FC = () => {
  const { id } = useParams()
  const dispatch = useAppDispatch()
  const data = useAppSelector(state => state.feedDetail.feedDetail)

  useEffect(() => {
    dispatch(getFeedDetailAsync(id as string))
  }, [dispatch])

  return (
    <FeedDetailLayout>
      <FeedDetailView {...data} />
      <CommentsView {...data} />
    </FeedDetailLayout>
  )
}
export default FeedDetail
