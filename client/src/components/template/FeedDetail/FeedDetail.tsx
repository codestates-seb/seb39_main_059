import Avatar from '@Atoms/Avatar'
import Button from '@Atoms/Button'
import Image from '@Atoms/Image'
import SvgButton from '@Atoms/SvgButton'
import { FeedDetail } from '@Types/feed'
import { FC, useState } from 'react'
import { axiosInstance } from '@Utils/instance'
import { FEED_PATH } from '@Routes/feed.routes'
import { useNavigate } from 'react-router-dom'
import Dropdown from '@Modules/Dropdown'
import * as S from './FeedDetail.style'

const FeedDetail: FC<FeedDetail> = ({
  body,
  name,
  profileImage,
  pictures,
  isMyFeed,
  feedId,
}) => {
  const [isDropdownView, setIsDropdownView] = useState<boolean | undefined>(
    false,
  )
  const navigate = useNavigate()

  const handleDeleteFeed = async () => {
    try {
      if (window.confirm('삭제하시겠습니까?')) {
        const axiosResponse = axiosInstance.delete(`${FEED_PATH}/${feedId}`, {
          headers: {
            contentType: 'application/json',
            tokenNeeded: true,
          },
        })
      }
      alert('삭제되었습니다.')
      navigate(-1)
    } catch (error: any) {
      alert('에러가 발생했습니다 :(')
    }
  }
  return (
    <S.FeedDetailLayout>
      <S.InfoBox>
        <S.UserInfoBox>
          <Avatar
            diameter="30px"
            fontSize="15px"
            name={name}
            imageUrl={profileImage}
          />
          <Button
            backgroundColor="primary"
            color="white"
            fontSize="sm"
            fontWeight="bold"
          >
            팔로잉
          </Button>
        </S.UserInfoBox>
        {isMyFeed && (
          <S.DropdownBox>
            <SvgButton
              icon="DropdownMenuIcon"
              onClick={() => setIsDropdownView(!isDropdownView)}
            />
            {isDropdownView && <Dropdown handleDeleteFeed={handleDeleteFeed} />}
          </S.DropdownBox>
        )}
      </S.InfoBox>
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
