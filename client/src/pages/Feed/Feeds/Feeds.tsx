import { Feeds } from '@Types/feed'
import Image from '@Atoms/Image'
import SvgButton from '@Atoms/SvgButton'
import Avatar from '@Atoms/Avatar'
import { useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { FEED_PATH } from '@Routes/feed.routes'
import * as S from './Feeds.style'
import { SvgButtonCssProp, AvatarCssProp } from './Feeds.style'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import {
  getFeedsAsync,
  addLikeAsync,
  cancelLikeAsync,
} from '@/redux/actions/FeedsAction'

const Feeds = () => {
  const navigate = useNavigate()
  const dispatch = useAppDispatch()
  const { feed, follow } = useAppSelector(state => state.feeds)
  const { isLogin } = useAppSelector(state => state.user)

  useEffect(() => {
    dispatch(getFeedsAsync())
  }, [])

  const handleLikeClick = (feedId: number, isLike: boolean) => {
    if (isLogin) {
      if (isLike) {
        dispatch(cancelLikeAsync(feedId))
      } else {
        dispatch(addLikeAsync(feedId))
      }
    } else {
      alert('로그인이 필요한 서비스입니다🐱')
      navigate('/login')
    }
  }

  return (
    <S.FeedsLayout>
      <S.FollowCatBox>
        {follow &&
          follow.map(item => (
            <Avatar
              key={item.catId}
              diameter="70px"
              imageUrl={item.profileImage}
              cssProp={AvatarCssProp}
            />
          ))}
      </S.FollowCatBox>
      <S.FeedBox>
        {feed &&
          feed.map(item => {
            return (
              <S.FeedItem key={item.feedId}>
                <Link to={`/${FEED_PATH}/${item.feedId}`}>
                  <Image src={item.image} />
                </Link>
                <SvgButton
                  icon={item.isLike ? 'EmptyHeartIcon' : 'HeartIcon'}
                  cssProp={SvgButtonCssProp}
                  onClick={() => handleLikeClick(item.feedId, item.isLike)}
                />
              </S.FeedItem>
            )
          })}
      </S.FeedBox>
    </S.FeedsLayout>
  )
}
export default Feeds
