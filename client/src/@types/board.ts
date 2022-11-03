export interface Boards {
  items: Board[]
  page: number
  pageSize: number
  totalElements: number
  totalPages: number
  hasMoreFeed?: boolean
  isLoading?: boolean
}

export interface Board {
  boardId: number
  commentCount: number
  createdDate: string
  likeCount: number
  name: string
  picture: string
  tags: Tag[]
  title: string
  viewCount: number
}

export interface Tag {
  tag: string
}
