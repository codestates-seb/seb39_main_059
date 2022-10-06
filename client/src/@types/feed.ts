export interface Feeds {
  feed: Feed[]
  follow: Follow[]
  page: number
  pageSize: number
  totalElements: number
  totalPages: number
  hasMoreFeed?: boolean
  isLoading?: boolean
}

export interface Feed {
  feedId: number
  image: string
  like: boolean
}

export interface Follow {
  catId: number
  profileImage: string
}

export interface FeedDetailInitialState {
  feedDetail: FeedDetail
  isLoading: boolean
}

export interface FeedDetail {
  body: string
  catId: number
  comments: Comment[]
  feedId: number
  isLike: boolean
  likeCount: number
  name: string
  pictures: Picture[]
  profileImage: string
  tags: Tag[]
}

export interface Comment {
  body: string
  catId?: number
  feedCommentId: number
  likeCount: number
  name: string
  profileImage: string
  userId?: number
}

export interface Picture {
  picture: string
}

export interface Tag {
  tag: string
}
