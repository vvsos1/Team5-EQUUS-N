import { useEffect, useState } from 'react';
import CustomInput from '../../components/CustomInput';
import NavBar from '../auth/components/NavBar';
import NavBar2 from '../../components/NavBar2';
import Icon from '../../components/Icon';
import LargeButton from '../../components/buttons/LargeButton';
import { useNavigate } from 'react-router-dom';
import { DropdownLarge } from '../../components/Dropdown';
import DatePicker from 'react-datepicker';
import CustomDatePicker from './components/CustomDatePicker';
import { showToast } from '../../utility/handleToast';
import {
  checkTeamSpaceMakingInfo,
  isValidTeamName,
} from '../../utility/inputChecker';

/**
 * @param {object} props
 * @param {boolean} props.isFirst
 * @returns
 */
export default function TeamSpaceMake({ isFirst = false }) {
  const [teamSpaceName, setTeamSpaceName] = useState('');
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [isAnonymous, setIsAnonymous] = useState(false);
  const navigate = useNavigate();

  const onClickNext = () => {
    if (!checkTeamSpaceMakingInfo(teamSpaceName, startDate, endDate)) {
      return;
    } else {
      //TODO: 팀 공간 만들기 요청
    }
    navigate(`/teamspace/make/success?teamName=${teamSpaceName}`, {
      state: isFirst ? { from: '/first' } : { from: '/' },
    });
  };

  const onClickPop = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (startDate > endDate) {
      setEndDate(startDate);
      showToast('종료일은 시작일보다 빠를 수 없습니다');
    }
  }, [startDate, endDate]);

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
      <div className='h-6' />
      <div className='flex flex-col gap-2'>
        {/* 제목 */}
        {<p className='subtitle-2 text-gray-0'>프로젝트 기간</p>}
        {/* 인풋 */}
        <div className='flex'>
          <CustomDatePicker date={startDate} setDate={setStartDate} />
          <div className='w-3 shrink-0' />
          <CustomDatePicker
            date={endDate}
            setDate={setEndDate}
            isStartTime={false}
          />
        </div>
      </div>
      <div className='h-6' />
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
