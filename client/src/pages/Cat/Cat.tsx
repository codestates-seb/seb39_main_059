import { FC } from 'react'
import { useNavigate } from 'react-router-dom'
import { Back, Edit } from '@Assets/icons'
import { SubmitHandler, useForm } from 'react-hook-form'
import { axiosInstance } from '@Utils/instance'
import { CatspiceList as CatBreedList, CatWeightList } from '@/constant'
import * as S from './Cat.style'
import { useAppSelector } from '@/redux/store'

interface FormValue {
  image: string
  name: string
  weight: string
  gender: string
  birth: string
  breed: string
  description: string
}

const Cat: FC = () => {
  const navigate = useNavigate()
  const auth = useAppSelector(state => state.user)

  const { register, handleSubmit } = useForm<FormValue>()

  const onSubmitHandler: SubmitHandler<FormValue> = data => {
    console.log(data)
  }

  return (
    <S.CatLayout>
      <S.Header className="header">
        <button type="button" onClick={() => navigate(-1)}>
          <Back />
        </button>
        <span>캣 프로필</span>
        <button type="button">
          <Edit />
        </button>
      </S.Header>
      <S.CatForm className="new-cat" onSubmit={handleSubmit(onSubmitHandler)}>
        <S.CatImageInput
          inputName="image-input"
          desc="사진 교체하기"
          onPost={getImgUrl}
          required
          {...register('image', { required: true })}
        />
        <S.CatNameInput
          type="text"
          inputName="name"
          placeholder="이름"
          required
          {...register('name', { required: true })}
        />
        <S.CatBirthInput
          type="date"
          inputName="birth"
          data-placeholder="생일"
          required
          {...register('birth', { required: true })}
        />
        <S.SelectsBox>
          <S.Select
            defaultValue=""
            required
            {...register('weight', { required: true })}
          >
            <option key="placeholder" value="" disabled>
              몸무게
            </option>
            {CatWeightList.map(weight => (
              <option
                key={`${weight}`}
                value={`${weight * 1000}`}
              >{`${weight} kg`}</option>
            ))}
          </S.Select>
          <S.Select
            defaultValue=""
            required
            {...register('gender', { required: true })}
          >
            <option key="placeholder" value="" disabled>
              성별
            </option>
            <option key="male" value="m">
              ♂
            </option>
            <option key="female" value="f">
              ♀︎
            </option>
          </S.Select>
        </S.SelectsBox>
        <S.Select
          defaultValue=""
          required
          {...register('breed', { required: true })}
        >
          <option key="placeholder" value="" disabled>
            폼종
          </option>
          {CatBreedList.map(breed => (
            <option key={breed} value={breed}>
              {breed}
            </option>
          ))}
        </S.Select>
        <S.DescArea
          inputName="description"
          placeholder="고양이를 자랑해 보세요!"
          required
          {...register('description', { required: true })}
        />
        <S.SubmitButton>등록하기</S.SubmitButton>
      </S.CatForm>
    </S.CatLayout>
  )
}

const getImgUrl = async (formData: FormData): Promise<string> => {
  const axiosResponse = await axiosInstance.post('/upload', formData, {
    headers: {
      'content-type': 'multipart/form-data',
      tokenNeeded: true
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

export default Cat
