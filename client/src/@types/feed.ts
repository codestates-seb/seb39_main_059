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