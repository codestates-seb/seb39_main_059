import ImageInput from '@Modules/ImageInput'

import * as S from './FeedForm.style'

const FeedForm = () => {
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) =>
    event.preventDefault()
  return (
    <S.FeedFormLayout>
      <S.ImageInputBox>
        <ImageInput inputName="image-input" />
      </S.ImageInputBox>
      <S.TitleInput inputName="title-input" placeholder="글제목" />
      <S.BodyInput
        inputName="body-input"
        placeholder="본문에 #을 이용해 태그를 입력해보세요!"
      />
      <S.SubmitButton
        backgroundColor="primary"
        color="white"
        fontSize="md"
        fontWeight="bold"
      >
        게시하기
      </S.SubmitButton>
    </S.FeedFormLayout>
  )
}
export default FeedForm
