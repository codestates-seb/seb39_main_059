import { FC, useEffect, useRef } from 'react'
import { useNavigate } from 'react-router-dom'
import { Back, Edit } from '@Assets/icons'
import { SubmitHandler, useForm } from 'react-hook-form'
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
  // console.log(auth)

  const dateRef = useRef<HTMLInputElement>(null)
  useEffect(() => {
    console.log(dateRef.current)
  }, [dateRef.current?.value])

  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm<FormValue>()

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
export default Cat
