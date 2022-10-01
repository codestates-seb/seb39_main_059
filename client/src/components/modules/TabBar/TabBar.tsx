import {
  NewIcon,
  HomeIcon,
  MyProfileIcon,
  SearchIcon,
  TabBarHeartIcon,
} from '@Assets/icons'
import * as S from './TabBar.style'

const TabBar = () => {
  return (
    <S.TabBarLayout>
      <S.IconBox>
        <HomeIcon />
      </S.IconBox>
      <S.IconBox>
        <SearchIcon />
      </S.IconBox>
      <S.IconBox>
        <NewIcon />
      </S.IconBox>
      <S.IconBox>
        <TabBarHeartIcon />
      </S.IconBox>
      <S.IconBox>
        <MyProfileIcon />
      </S.IconBox>
    </S.TabBarLayout>
  )
}

export default TabBar
