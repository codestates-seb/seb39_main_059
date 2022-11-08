import { axiosInstance } from '@Utils/instance'
import { Back } from '@Assets/icons'
import { useNavigate } from 'react-router-dom'
import { FC } from 'react'
import { FieldErrors, SubmitHandler, useForm } from 'react-hook-form'
import * as S from './PostForm.style'

interface Props {
  hasTitle?: boolean
  imgRequired?: boolean
  onSubmitHandler: SubmitHandler<FormValue>
}

export interface FormValue {
  body: string
  picture: string
  title: string
}

const PostForm: FC<Props> = ({
  onSubmitHandler,
  hasTitle = false,
  imgRequired,
}) => {
  const navigate = useNavigate()
  const getImgUrl = async (formData: FormData): Promise<string> => {
    const axiosResponse = await axiosInstance.post('/upload', formData, {
      headers: {
        'content-type': 'multipart/form-data',
        Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    })
    /*          // HttpStatus가 200번호 구역이 아니거나
      // 서버에서 응답 코드로 0(성공)을 주지 않았을 경우
      if (
        axiosResponse.status < 200 ||
        axiosResponse.status >= 300 ||
        axiosResponse.data.resultCode !== 0
      ) {
        // Error를 발생시켜 Catch문을 타게 만들어주는데, 서버에 응답받은 메시지를 넣어준다!
        // 서버에서 응답 메시지를 받지 못했을경우 기본 메시지 설정또한 함께 해준다
        throw Error(axiosResponse.data.message || '문제가 발생했어요!')
      } */
    return axiosResponse.data
  }

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValue>()

  const onInvalid = (errors: FieldErrors) => {
    alert('submit fail')
  }

  return (
    <S.PostFormLayout onSubmit={handleSubmit(onSubmitHandler, onInvalid)}>
      <S.Header className="header">
        <button type="button" onClick={() => navigate(-1)}>
          <Back />
        </button>
        <span>글쓰기</span>
      </S.Header>
      <S.ImageInputBox>
        <S.PostImageInput
          inputName="image-input"
          onPost={getImgUrl}
          {...register('picture', { required: '사진은 필수 입력입니다' })}
        />
      </S.ImageInputBox>
      {hasTitle && (
        <S.TitleInput
          inputName="title-input"
          placeholder="글제목"
          {...register('title', { required: '제목은 필수 입력입니다' })}
        />
      )}
      <S.BodyInput
        inputName="body-input"
        placeholder="본문에 #을 이용해 태그를 입력해보세요!"
        {...register('body', { required: '본문은 필수 입력입니다' })}
      />
      <S.SubmitButton
        backgroundColor="primary"
        color="white"
        fontSize="md"
        fontWeight="bold"
        type="submit"
      >
        게시하기
      </S.SubmitButton>
    </S.PostFormLayout>
  )
}
export default PostForm
