import { useState } from 'react';
import CustomInput from '../../components/CustomInput';
import NavBar from '../auth/components/NavBar';
import NavBar2 from '../../components/NavBar2';
import Icon from '../../components/Icon';
import LargeButton from '../../components/buttons/LargeButton';
import { useNavigate } from 'react-router-dom';

/**
 * @param {object} props
 * @param {boolean} props.isFirst
 * @returns
 */
export default function TeamSpaceMake({ isFirst = false }) {
  const [teamSpaceName, setTeamSpaceName] = useState('');
  const [isAnonymous, setIsAnonymous] = useState(false);
  const navigate = useNavigate();

  const onClickNext = () => {
    navigate(`/teamspace/make/success?teamName=${teamSpaceName}`, {
      state: isFirst ? { from: '/first' } : { from: '/' },
    });
  };

  const onClickPop = () => {
    navigate(-1);
  };

  return (
    <div className='relative flex h-dvh w-full flex-col justify-start'>
      {isFirst ?
        <NavBar title='새로운 팀 스페이스 만들기' />
      : <NavBar2
          canPop={true}
          title='팀 스페이스 생성하기'
          onClickPop={onClickPop}
        />
      }
      <div className={`h-${isFirst ? '2' : '4'}`} />
      <CustomInput
        label='팀 이름'
        content={teamSpaceName}
        setContent={setTeamSpaceName}
      />
      <div className='flex h-[85px]'></div>
      <CustomInput
        label='피드백 방식'
        isOutlined={false}
        content='익명을 기본으로 설정할게요'
        addOn={
          <button
            className='flex h-full w-full items-center justify-center'
            onClick={() => setIsAnonymous(!isAnonymous)}
          >
            <Icon name={isAnonymous ? 'checkBoxClick' : 'checkBoxNone'} />
          </button>
        }
      />
      <p className='caption-1 mt-3 text-gray-300'>
        피드백 작성 시 피드백 방식을 변경할 수 있어요!
      </p>

      {/* 다음 버튼 */}
      <div className='absolute right-0 bottom-[34px] left-0 flex flex-col bg-gray-900'>
        <LargeButton
          text='다음'
          isOutlined={false}
          onClick={() => {
            onClickNext();
          }}
        />
        {isFirst && <div className='h-3' />}
        {isFirst && (
          <div
            className={
              'rounded-300 flex h-[56px] w-full items-center justify-center px-4 py-2 text-gray-300'
            }
          >
            <a>건너뛰기</a>
          </div>
        )}
      </div>
    </div>
  );
}
