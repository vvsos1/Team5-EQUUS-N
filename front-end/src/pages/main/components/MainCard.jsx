import Tag from '../../../components/Tag';
import { TagType } from '../../../components/Tag';
import Icon from '../../../components/Icon';
import MediumButton from '../../../components/buttons/MediumButton';
import { useEffect, useRef, useState } from 'react';
import classNames from 'classnames';
import { hideModal, showModal } from '../../../utility/handleModal';
import Modal, { ModalType } from '../../../components/modals/Modal';
import ProfileImage from '../../../components/ProfileImage';

/**
 * 메인 카드 컴포넌트
 * @param {object} props
 * @param {boolean} props.isInTeam - 팀에 속해있는지 여부
 * @param {object} props.recentSchedule - 최신 스케줄
 * @param {function} props.onClickMainButton - 메인 버튼 클릭 이벤트
 * @param {function} props.onClickSubButton - 서브 버튼 클릭 이벤트
 * @param {function} props.onClickChevronButton - 체크론 버튼 클릭 이벤트
 * @returns {ReactElement}
 */
export default function MainCard({
  isInTeam = true,
  recentSchedule,
  onClickMainButton,
  onClickSubButton,
  onClickChevronButton,
}) {
  if (!isInTeam) {
    return (
      <MainCardFrame>
        <>
          <p className='body-1 mt-11 mb-10 text-center text-gray-300'>
            팀 스페이스가 비어있어요
          </p>
          <MediumButton
            text={'팀 스페이스 생성하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          />
        </>
      </MainCardFrame>
    );
  }

  // 2022-12-12T17:00:00 와 같은 형식으로 비교
  const scheduleDifferece =
    new Date(recentSchedule.at(-1).end) - new Date(new Date().toISOString());
  console.log(scheduleDifferece);

  // recentschedule의 종료시각이 현시각으로부터 24시간 이상 차이날경우: 로직필요
  if (recentSchedule.length === 0 || scheduleDifferece < -86400000) {
    return (
      <MainCardFrame>
        <>
          <p className='body-1 mt-11 mb-10 text-center text-gray-300'>
            다음 일정이 비어있어요
          </p>
          <MediumButton
            text={'일정 추가하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          />
          <button onClick={onClickChevronButton}>
            <Icon name='chevronRight' className='absolute top-4 right-4' />
          </button>
        </>
      </MainCardFrame>
    );
  }

  const contentRef = useRef(null);
  const [isOpen, setIsOpen] = useState(false);
  const [height, setHeight] = useState(0);

  // 스케줄 컨텐츠 높이 계산
  useEffect(() => {
    if (contentRef.current) {
      setHeight(isOpen ? contentRef.current.scrollHeight : 0);
    }
  }, [isOpen]);

  if (0 < scheduleDifferece && scheduleDifferece < 86400000) {
    return (
      <MainCardFrame>
        <div className='rounded-400 flex h-fit w-full flex-col gap-6 overflow-hidden bg-gray-800 transition-all duration-300'>
          <p className='body-1 text-center text-gray-400'>
            {recentSchedule.name}
          </p>
          <MediumButton
            text={'피드백 작성하기'}
            isOutlined={false}
            onClick={onClickMainButton}
          ></MediumButton>
        </div>
      </MainCardFrame>
    );
  } else {
    return (
      <MainCardFrame>
        <div className='flex flex-col items-center justify-center pt-6'>
          <p className='body-1 text-gray-300'>
            {'다음 일정까지'}
            <span className='body-4 ml-2 text-lime-200'>{`D-${1}`}</span>
          </p>
          <h1 className='header-1 my-2 text-gray-100'>
            {recentSchedule.at(-1).name}
          </h1>
          <Tag
            type={TagType.TEAM_SCHEDULE}
            children={{ date: '12일 목요일', time: '17:00' }}
          />
        </div>
        <hr className='my-6 w-full border-gray-500' />
        {/* 나의 역할 */}
        {recentSchedule.at(-1).roles.find((role) => role.memberId === 1) ?
          <div className='flex flex-col gap-3'>
            <Tag type={TagType.MY_ROLE} />
            <div className='flex flex-col gap-1'>
              {recentSchedule
                .at(-1)
                .roles.find((role) => role.memberId === 1)
                .task.map((role, index) => (
                  <li
                    key={index}
                    className='body-1 pl-2 text-gray-100 last:mb-6'
                  >
                    {role}
                  </li>
                ))}
            </div>
          </div>
        : <div className='mb-6 flex flex-col gap-6'>
            <p className='body-1 text-center text-gray-400'>
              나의 역할이 비어있어요
            </p>
            {/* 일정 종료 전에만 나의 역할 추가 버튼 표시 */}
            <MediumButton text={'나의 역할 추가하기'} isOutlined={false} />
          </div>
        }
        {/* 팀원 역할 */}
        <div
          ref={contentRef}
          className={`flex flex-col overflow-hidden transition-all duration-300 ease-in-out`}
          style={contentRef.current ? { height: `${height}px` } : { height: 0 }}
        >
          {recentSchedule.at(-1).roles.map((role, index) => {
            if (role.memberId === 1) return null;
            return (
              <div key={index} className='flex flex-col gap-3'>
                <Tag type={TagType.MEMBER_ROLE}>{role.name}</Tag>
                {role.task.length > 0 ?
                  <div className='flex flex-col gap-1'>
                    {role.task.map((task, index) => (
                      <li
                        key={index}
                        className='body-1 pl-1 text-gray-100 last:mb-6'
                      >
                        {task}
                      </li>
                    ))}
                  </div>
                : <p className='body-1 text-gray-500'>
                    아직 담당 업무를 입력하지 않았어요
                  </p>
                }
              </div>
            );
          })}
        </div>
        {/* 팀원 역할 보기 토글 버튼 */}
        <MediumButton
          text={
            <div className='flex items-center gap-2'>
              <Icon
                className={isOpen ? 'rotate-180' : null}
                name={'chevronDown'}
              />
              {isOpen ? '닫기' : '팀원 역할 보기'}
            </div>
          }
          onClick={() => setIsOpen(!isOpen)}
          isOutlined={true}
          disabled={true}
        ></MediumButton>
      </MainCardFrame>
    );
  }
}

function MainCardFrame({ children }) {
  return (
    <div className='rounded-400 relative flex flex-col bg-gray-800 p-4'>
      {children}
    </div>
  );
}
