import Avatar from '@Atoms/Avatar'
import Button from '@Atoms/Button'
import Image from '@Atoms/Image'
import { FeedDetail } from '@Types/feed'
import { FC } from 'react'
import * as S from './FeedDetail.style'

const FeedDetail: FC<FeedDetail> = ({ body, name, profileImage, pictures }) => {
  return (
    <S.FeedDetailLayout>
      <S.UserInfoBox>
        <Avatar
          diameter="30px"
          fontSize="15px"
          name={name}
          imageUrl={profileImage}
        />
        {/* 팔로우 button폰트 size가 12px로 theme에 정의되어있지 않습니다. */}
        <Button
          backgroundColor="primary"
          color="white"
          fontSize="sm"
          fontWeight="bold"
        >
          팔로잉
        </Button>
      </S.UserInfoBox>
      {pictures && pictures.map(item => <Image src={item.picture} />)}
      <S.FeedBodyText>{body}</S.FeedBodyText>
      {/* 더미데이터 */}
      <S.TagBox>
        <S.Tag>#봉투홀릭</S.Tag>
        <S.Tag>#2시간째</S.Tag>
        <S.Tag>#개냥이</S.Tag>
      </S.TagBox>
    </S.FeedDetailLayout>
  )
}
export default FeedDetail
