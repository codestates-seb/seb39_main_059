import Avatar from '@Atoms/Avatar'
import Button from '@Atoms/Button'
import ContentInput from '@Modules/FormTextInput/FormTextInput'
import Image from '@Atoms/Image'
import * as S from './FeedDetail.style'

const FeedDetail = () => {
  return (
    <S.FeedDetailLayout>
      <S.UserInfoBox>
        <Avatar
          diameter="30px"
          fontSize="15px"
          name="닉네임"
          imageUrl="https://images.unsplash.com/photo-1664136262345-daa7e71f0c0b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2787&q=80"
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
      <Image src="https://images.unsplash.com/photo-1514888286974-6c03e2ca1dba?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1686&q=80" />
      <S.FeedBodyText>
        어릴 때, 그렇게 좋아하던 봉투였는데....
        <br />
        여전히 좋아하네요...
      </S.FeedBodyText>
      <ContentInput
        inputName="feed-body"
        placeholder="본문에 #을 이용해 태그를 입력해보세요"
      />
    </S.FeedDetailLayout>
  )
}
export default FeedDetail
