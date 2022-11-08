import { CameraIcon } from '@Assets/icons'
import Image from '@Atoms/Image'
import Text from '@Atoms/Text'
import { fileExtensionValid, isEmpty } from '@Utils/utility'
import React, {
  FC,
  forwardRef,
  useImperativeHandle,
  useRef,
  useState,
} from 'react'
import { CSSProp } from 'styled-components'
import * as S from './ImageInput.style'

// 허용가능한 확장자 목록!
const ALLOW_FILE_EXTENSION = 'jpg,jpeg,png'
const FILE_SIZE_MAX_LIMIT = 4 * 1024 * 1024 // 4MB

const fileUploadValidHandler = (file: File): boolean => {
  // 파일 확장자 체크
  if (!fileExtensionValid(file.name, ALLOW_FILE_EXTENSION)) {
    alert(
      `업로드 가능한 확장자가 아닙니다. [가능한 확장자 : ${ALLOW_FILE_EXTENSION}]`,
    )
    return false
  }

  // 파일 용량 체크
  if (file.size > FILE_SIZE_MAX_LIMIT) {
    alert('업로드 가능한 최대 용량은 5MB입니다. ')
    return false
  }

  return true
}

export interface Props {
  /** name of input */
  inputName: string
  name: string
  /** onChange handler(setState) */
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void
  /** onFocusOut handler */
  // onFocusOut?: (e: React.FocusEvent<HTMLInputElement>) => void
  onBlur: any
  /** uploadHandler */
  onPost: (FormData: FormData) => Promise<string>
  // onUploadImage?:
  desc?: string
  cssProp?: CSSProp
  imgCssProp?: CSSProp
}

const ImageInput = forwardRef<HTMLInputElement, Props>(
  ({ inputName, onPost, onChange, onBlur, name, ...props }, ref) => {
    const [imgUrl, setImgUrl] = useState('')

    const urlRef = useRef<HTMLInputElement>(null)
    useImperativeHandle(ref, () => urlRef.current as HTMLInputElement, [urlRef])

    const fileUploadHandler = async (
      event: React.ChangeEvent<HTMLInputElement>,
    ) => {
      const target = event.currentTarget
      const file = (target.files as FileList)[0]

      if (!fileUploadValidHandler(file)) {
        target.value = ''
        return
      }

      // !!중요1. formData활용!!
      const formData = new FormData()
      formData.append('images', file)
      if (urlRef.current) {
        urlRef.current.focus()
      }
      const imgUrl = await onPost(formData)
      // 파일 업로드 성공!
      if (isEmpty(imgUrl)) alert('파일 업로드 실패')

      setImgUrl(imgUrl)
      if (urlRef.current) {
        urlRef.current.value = imgUrl
        urlRef.current.dispatchEvent(new Event('change', { bubbles: true }))
        urlRef.current.blur()
      }
    }

    return (
      <S.ImageInputlayout cssProp={props.cssProp}>
        <S.ImgLabel imgCssProp={props.imgCssProp} htmlFor="image-input">
          {isEmpty(imgUrl) ? <CameraIcon /> : <Image src={imgUrl} />}
        </S.ImgLabel>
        <S.HiddenInput
          type="file"
          id="image-input"
          name={inputName}
          onChange={fileUploadHandler}
        />
        <S.HiddenInput
          type="text"
          name={name}
          ref={urlRef}
          onChange={onChange}
          onBlur={onBlur}
        />
        {props.desc && (
          <Text fontSize="sm" color="softGray" fontWeight="regular">
            {props.desc}
          </Text>
        )}
      </S.ImageInputlayout>
    )
  },
)

export default ImageInput
