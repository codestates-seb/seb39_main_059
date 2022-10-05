import Avatar from '@Atoms/Avatar'
import Button from '@Atoms/Button'
import ContentInput from '@Modules/FormTextInput/FormTextInput'
import Image from '@Atoms/Image'
import { useEffect } from 'react'
import { useParams } from 'react-router-dom'
import * as S from './FeedDetail.style'
import { useAppDispatch, useAppSelector } from '@/redux/store'
import { getFeedDetailAsync } from '@/redux/actions/FeedDetailAction'

const FeedDetail = () => {
  const { id } = useParams()
  const dispatch = useAppDispatch()
  const { body, name, profileImage, pictures } = useAppSelector(
    state => state.feedDetail.feedDetail,
  )

  useEffect(() => {
    dispatch(getFeedDetailAsync(id as string))
  }, [dispatch])

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
      <ContentInput
        inputName="feed-body"
        placeholder="본문에 #을 이용해 태그를 입력해보세요"
      />
    </S.FeedDetailLayout>
  )
}
export default FeedDetail
