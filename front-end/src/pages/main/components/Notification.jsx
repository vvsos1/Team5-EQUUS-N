import classNames from 'classnames';
import Lottie from 'lottie-react';
import boxLottie from '../../../assets/lotties/box.json';
import fileLottie from '../../../assets/lotties/file.json';
import letterLottie from '../../../assets/lotties/letter.json';
import Icon from '../../../components/Icon';

export const notiType = Object.freeze({
  REQUEST: 'REQUEST',
  REPORT: 'REPORT',
  UNCONFIRM: 'UNCONFIRM',
  NEW: 'NEW',
});

/**
 * 노티파이 컴포넌트
 * @param {Object} props
 * @param {keyof notiType} props.type
 * @param {Function} props.onClick
 * @param {Function} props.onClose
 * @param {string} props.name
 * @param {number} props.count
 */
export default function Notification({ type, onClick, onClose, name, count }) {
  // TODO: 파동 애니메이션 추가
  // TODO: delete 아이콘, 화살표 아이콘 수정
  const getContent = () => {
    switch (type) {
      case notiType.UNCONFIRM:
        return {
          animationData: letterLottie,
          message: '확인하지 않은\n피드백이 있어요!',
          buttonText: '피드백 확인하기',
        };
      case notiType.NEW:
        return {
          animationData: letterLottie,
          message: '새로운 피드백이\n도착했어요!',
          buttonText: '피드백 확인하기',
        };
      case notiType.REPORT:
        return {
          animationData: fileLottie,
          message: `${name}님의 피드백을\n정리했어요!`,
          buttonText: '피드백 리포트 확인하기',
        };
      case notiType.REQUEST:
        return {
          animationData: boxLottie,
          message: `${name}님 외 ${count}명이\n피드백을 요청했어요!`,
          buttonText: '피드백 보내기',
        };
      default:
        throw new Error('Invalid notiType');
    }
  };

  const { animationData, message, buttonText } = getContent();

  return (
    <div
      className={classNames(
        'rounded-400 relative w-full',
        type === notiType.REQUEST || type === notiType.UNCONFIRM ?
          'bg-gray-100'
        : 'bg-lime-500',
      )}
    >
      <Lottie animationData={animationData} />

      <p className='header-4 absolute top-5 left-6 whitespace-pre-line'>
        {message}
      </p>

      <button
        className='absolute bottom-4 left-6 flex items-center gap-0.5'
        onClick={onClick}
      >
        <p className='caption-2 text-gray-600'>{buttonText}</p>
        <Icon name='chevronDown' className='-rotate-90' />
      </button>

      <button className='absolute top-4 right-4' onClick={onClose}>
        <Icon name='delete' color='var(--color-gray-500)' />
      </button>
      <div className='absolute right-[50px] bottom-2 h-[30px] w-[88px] rounded-tl-[7px] rounded-tr-[7px] bg-gradient-to-b from-[#2a2a2a] from-60% to-transparent'>
        <p className='mt-1 text-center text-[10px] font-thin text-gray-100'>
          NEW
        </p>
      </div>
    </div>
  );
}
