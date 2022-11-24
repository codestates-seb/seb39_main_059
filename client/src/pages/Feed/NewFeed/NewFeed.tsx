import { FC, useEffect, useState } from 'react'
import PostForm from '@Template/PostForm'
import { useNavigate } from 'react-router-dom'
import { SubmitHandler } from 'react-hook-form'
import { FormValue } from '@Template/PostForm/PostForm'
import Header from '@Modules/Header'
import { axiosInstance } from '@Utils/instance'
import { FEED_PATH } from '@Routes/feed.routes'
import { useAppSelector } from '@/redux/store'
import * as S from './NewFeed.style'

interface Props {
  catId: number
  name: string
  profileImage: string
}

const NewFeed: FC = () => {
  const navigate = useNavigate()
  const { isLogin } = useAppSelector(state => state.user)
  const [catInfos, setCatInfos] = useState<Props[]>([])
  const [catId, setCatId] = useState<number>(0)

  const hasCatInfos = <Props,>(data: Props[]) => {
    if (!data.length) {
      if (
        window.confirm(
          '고양이를 등록하면 피드를 작성할 수 있습니다.\n등록 페이지로 이동하시겠습니까?',
        )
      ) {
        navigate('/cat')
      } else {
        navigate('/')
      }
    }
  }

  const getCatInfos = async (callback: (params: Props[]) => void) => {
    const { data } = await axiosInstance.get('/users/cats', {
      headers: {
        contentType: 'application/json',
        Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    })
    setCatInfos(data)
    callback(data)
  }

  useEffect(() => {
    if (!isLogin) {
      alert('로그인이 필요한 서비스입니다🐱')
      navigate('/login')
      return
    }
    getCatInfos(hasCatInfos)
  }, [])

  const onSubmitHandler: SubmitHandler<FormValue> = async data => {
    const { picture, body } = data
    const formData = {
      body,
      catId,
      pictures: [
        {
          picture,
        },
      ],
      tags: null,
    }
    const axiosResponse = await axiosInstance.post(FEED_PATH, formData, {
      headers: {
        contentType: 'application/json',
        Authorization: `Bearer ${localStorage.getItem('ACCESS_TOKEN')}`,
      },
    })
    navigate(-1)
    return axiosResponse
  }

  const catList: JSX.Element[] = catInfos.map(catInfo => (
    <option key={catInfo.catId} value={catInfo.catId}>
      {catInfo.name}
    </option>
  ))

  return (
    <S.NewFeedLayout>
      <Header>냥이생활 글쓰기</Header>
      <div>
        <select
          onChange={e => {
            setCatId(Number(e.target.value))
          }}
        >
          <option value={0}>--고양이 선택--</option>
          {catList && catList}
        </select>
      </div>
      <PostForm onSubmitHandler={onSubmitHandler} />
    </S.NewFeedLayout>
  )
}

export default NewFeed
