import { hideModal } from '../../utility/handleModal';
import MediumButton from '../buttons/MediumButton';
import Icon from '../Icon';

export const coloredModalType = Object.freeze({
  PROFILE: 'PROFILE',
  DOUBLE: 'DOUBLE',
  SINGLE: 'SINGLE',
  SINGLE_DELETE: 'SINGLE_DELETE',
});

/**
 * 모달 컴포넌트
 * @param {object} props
 * @param {keyof coloredModalType} props.type - 모달 타입
 * @param {ReactElement} props.profileImage - 프사
 * @param {string} props.content - 모달 내용
 * @param {ReactElement} props.mainButton - 메인 버튼
 * @param {ReactElement} props.subButton - 서브 버튼
 * @returns
 */
export default function ColoredModal({
  type,
  profileImage,
  content,
  mainButton,
  subButton,
}) {
  return (
    <div className='rounded-400 text-gray-0 relative flex w-[353px] flex-col bg-gray-800 p-4'>
      {type === coloredModalType.PROFILE && (
        <div className='flex flex-col items-center justify-center gap-3 pt-10'>
          {profileImage}
          <h2 className='subtitle-2 mb-7'>{content}</h2>
          {subButton}
          {mainButton}
          <button
            className='absolute top-4 right-4'
            onClick={() => hideModal()}
          >
            <Icon name='delete' />
          </button>
        </div>
      )}
      {type === coloredModalType.DOUBLE && (
        <>
          <h2 className='subtitle-2 mx-auto my-12'>{content}</h2>
          <div className='flex gap-3'>
            {subButton}
            {mainButton}
          </div>
        </>
      )}
      {type === coloredModalType.SINGLE && (
        <>
          <h2 className='subtitle-2 mx-auto my-12'>{content}</h2>
          {mainButton}
        </>
      )}
      {type === coloredModalType.SINGLE_DELETE && (
        <>
          <h2 className='subtitle-2 mx-auto my-12'>{content}</h2>
          {mainButton}
          <button
            className='absolute top-4 right-4'
            onClick={() => hideModal()}
          >
            <Icon name='delete' />
          </button>
        </>
      )}
    </div>
  );
}
