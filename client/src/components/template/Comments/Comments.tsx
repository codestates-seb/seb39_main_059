import Comment from '@Atoms/Comment'
import { FeedDetail } from '@Types/feed'
import { FC } from 'react'
import * as S from './Comments.style'

const Comments: FC<FeedDetail> = ({ comments }) => {
  return (
    <S.CommentsLayout>
      <S.CommentTitleBox>
        <S.CommentTitle>댓글</S.CommentTitle>
        <S.CommentCount>{comments.length}개</S.CommentCount>
      </S.CommentTitleBox>
      <S.CommentBox>
        {comments.map(item => (
          <Comment
            imageUrl={item.profileImage}
            userName={item.name}
            key={item.feedCommentId}
          >
            {item.body}
          </Comment>
        ))}
      </S.CommentBox>
    </S.CommentsLayout>
  )
}

export default Comments
