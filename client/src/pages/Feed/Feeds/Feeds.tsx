import { Feeds } from '@Types/feed'
import Image from '@Atoms/Image'
import SvgButton from '@Atoms/SvgButton'
import Avatar from '@Atoms/Avatar'
import { useEffect } from 'react'
import { Link } from 'react-router-dom'
import { FEED_PATH } from '@Routes/feed.routes'
import * as S from './Feeds.style'
import { SvgButtonCssProp, AvatarCssProp } from './Feeds.style'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import { getFeedsAsync } from '@/redux/actions/FeedsAction'

const Feeds = () => {
  const dispatch = useAppDispatch()
  const { feed, follow } = useAppSelector(state => state.feeds)

  useEffect(() => {
    dispatch(getFeedsAsync())
  }, [dispatch])

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
              <Link to={`/${FEED_PATH}/${item.feedId}`} key={item.feedId}>
                <S.FeedItem>
                  <Image src={item.image} />
                  {item.isLike ? (
                    <SvgButton
                      icon="EmptyHeartIcon"
                      cssProp={SvgButtonCssProp}
                    />
                  ) : (
                    <SvgButton icon="HeartIcon" cssProp={SvgButtonCssProp} />
                  )}
                </S.FeedItem>
              </Link>
            )
          })}
      </S.FeedBox>
    </S.FeedsLayout>
  )
}
export default Feeds
