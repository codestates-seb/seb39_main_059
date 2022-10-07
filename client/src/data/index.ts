import { Boards } from '@Types/board'

const tmplate = {
  boardId: 0,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '닉네임',
  picture:
    'https://images.unsplash.com/photo-1664574653790-cee0e10a4242?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '제목입니다',
  viewCount: 4,
}

const info1 = {
  boardId: 1,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://image-notepet.akamaized.net/resize/620x-/seimage/20170330%2F0a7ef234210cad51fb6ae873bbc0dff4.JPG',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '동물병원 추천해주세요',
  viewCount: 4,
}

const info2 = {
  boardId: 2,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '고양이조아',
  picture:
    'https://images.unsplash.com/photo-1610973053414-abc5309f0a8c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '고양이 장난감 추천',
  viewCount: 4,
}

const info3 = {
  boardId: 3,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://images.mypetlife.co.kr/content/uploads/2020/08/09150318/%EC%A0%9C%EB%AA%A9-%EC%97%86%EC%9D%8C-3-4.png',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '🏥 동물 병원 인기 순위',
  viewCount: 4,
}

const info4 = {
  boardId: 4,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://images.mypetlife.co.kr/content/uploads/2019/11/09152422/%EB%A9%94%EB%A1%B1%ED%95%98%EB%8A%94-%EA%B3%A0%EC%96%91%EC%9D%B4-%ED%98%80-%EB%82%B4%EB%B0%80%EA%B3%A0-%EC%9E%88%EB%8A%94-%EC%9D%B4%EC%9C%A0%EB%8A%94_-0-17-screenshot-768x432.png',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '고양이, 혼자 집에 있을 때 무얼하고 있을까?',
  viewCount: 4,
}

const info5 = {
  boardId: 5,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://i1.daumcdn.net/thumb/C230x300/?fname=https://blog.kakaocdn.net/dn/bD8ax7/btqPEMGjsUB/ulebNthv9TF9FhWRp2eQyK/img.jpg',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '고양이 예방접종 시기는?',
  viewCount: 4,
}

const suggestion1 = {
  boardId: 6,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://dnvefa72aowie.cloudfront.net/origin/article/202201/9EB6417E084964C46BDC7A2FC607977F3D6A3A815BADC8949AAC59C060A57E3C.jpg?q=95&s=1440x1440&t=inside',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '여러분 건대에 고양이 까페 생긴거 알고 계시나요?',
  viewCount: 4,
}

const report1 = {
  boardId: 7,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '주한',
  picture:
    'https://biz.chosun.com/resizer/MZexRK4PoiYrJS9KDtblP-A-4jY=/2560x0/smart/cloudfront-ap-northeast-1.images.arcpublishing.com/chosunbiz/WWCGXNYTRFFWTO3NT5DQTGD3EA.jpg',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '🚨 만두를 찾습니다!',
  viewCount: 4,
}

const report2 = {
  boardId: 8,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '냥냥펀치',
  picture:
    'https://images.unsplash.com/photo-1640246773181-dc85ccc478c9?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTE3fHxwZXQlMjBmb29kfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '🚨 강남역에서 고양이 주인 찾아요',
  viewCount: 4,
}

const etc1 = {
  boardId: 9,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '냥이 집사',
  picture:
    'https://images.unsplash.com/photo-1543852786-1cf6624b9987?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80',
  tags: [
    {
      tag: 'string',
    },
  ],
  title: '고양이가 자꾸 할퀴어요ㅠㅠ',
  viewCount: 4,
}

const etc2 = {
  boardId: 10,
  commentCount: 3,
  createdDate: '2022-10-06T13:47:00.444Z',
  likeCount: 150,
  name: '츄르',
  picture:
    'https://images.unsplash.com/photo-1622564871517-58e120ec6732?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1771&q=80',

  tags: [
    {
      tag: 'string',
    },
  ],
  title: '저희 집 막내를 소개합니다',
  viewCount: 4,
}

export const dummy: Boards = {
  items: [
    info4,
    info5,
    info3,
    info2,
    report1,
    suggestion1,
    info1,
    etc2,
    report2,
    etc1,
    /** ------------------------------------------------------------------------------------- */
  ],
  page: 0,
  pageSize: 0,
  totalElements: 0,
  totalPages: 0,
}
