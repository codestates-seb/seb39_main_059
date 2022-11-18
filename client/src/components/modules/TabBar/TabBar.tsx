import {
  NewIcon,
  HomeIcon,
  MyProfileIcon,
  SearchIcon,
  TabBarHeartIcon,
} from '@Assets/icons'
import { Link, useLocation } from 'react-router-dom'
import { useAppSelector } from '@/redux/store'
import * as S from './TabBar.style'

const TabBar = () => {
  const tmpLocation = useLocation()
  const { isLogin } = useAppSelector(state => state.user)

  return (
    <S.TabBarLayout>
      <S.IconBox>
        <Link to="/">
          <HomeIcon />
        </Link>
      </S.IconBox>
      <S.IconBox>
        <Link to="search">
          <SearchIcon />
        </Link>
      </S.IconBox>
      <S.IconBox>
        <Link to={`${tmpLocation.pathname}/new`}>
          <NewIcon />
        </Link>
      </S.IconBox>
      <S.IconBox>
        <Link to={`${tmpLocation.pathname}/likes`}>
          <TabBarHeartIcon />
        </Link>
      </S.IconBox>
      <S.IconBox>
        <Link to={isLogin?"/profile":"/login"}>
          <MyProfileIcon />
        </Link>
      </S.IconBox>
    </S.TabBarLayout>
  )
}

export default TabBar
