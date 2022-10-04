import { Feeds } from '@Types/feed'
import Image from '@Atoms/Image'
import SvgButton from '@Atoms/SvgButton'
import Avatar from '@Atoms/Avatar'
import * as S from './Feeds.style'
import { feedsDummyData } from './data'
import { SvgButtonCssProp, AvatarCssProp } from './Feeds.style'

const Feeds = () => {
  const handleFeedClick = () => {
    /* 특정 피드를 클릭했을 때 해당 피드 페이지로 이동하는 함수 */
  }

  const handleLikeClick = () => {
    /* 좋아요 버튼을 클릭했을 때 실행되는 함수 */
  }

  const handleFollowClick = () => {
    /* 팔로우한 고양이 프로필을 클릭했을 때 실행되는 함수 */
  }

  return (
    <S.FeedsLayout>
      <S.FollowCatBox>
        {feedsDummyData.follow.map(item => (
          <Avatar
            key={item.catId}
            diameter="70px"
            imageUrl={item.profileImage}
            cssProp={AvatarCssProp}
          />
        ))}
      </S.FollowCatBox>
      <S.FeedBox>
        {feedsDummyData.feed.map(item => {
          return (
            <S.FeedItem key={item.feedId}>
              <Image src={item.image} />
              <SvgButton icon="HeartIcon" cssProp={SvgButtonCssProp} />
            </S.FeedItem>
          )
        })}
      </S.FeedBox>
    </S.FeedsLayout>
  )
}
export default Feeds
