import Tag from '../../../components/Tag';
import { TagType } from '../../../components/Tag';
import Icon from '../../../components/Icon';
import MediumButton from '../../../components/buttons/MediumButton';
import { useEffect, useRef, useState } from 'react';
import classNames from 'classnames';

/**
 * 메인 카드 컴포넌트
 * @param {object} props
 * @param {boolean} props.isInTeam - 팀에 속해있는지 여부
 * @param {object[]} props.recentSchedule - 최신 스케줄
 * @param {function} props.onClickMainButton - 메인 버튼 클릭 이벤트
 * @param {function} props.onClickSubButton - 서브 버튼 클릭 이벤트
 * @returns {ReactElement}
 */
export default function MainCard({
  isInTeam = true,
  recentSchedule = [],
  onClickMainButton,
  onClickSubButton,
}) {
  const [isOpen, setIsOpen] = useState(false);
  const contentRef = useRef(null);
  const [height, setHeight] = useState(0);

  // 2022-12-12T17:00:00 와 같은 형식으로 비교
  const scheduleDifferece =
    new Date(recentSchedule.end) - new Date(new Date().toISOString());

  // 스케줄 컨텐츠 높이 계산
  useEffect(() => {
    if (contentRef.current) {
      setHeight(isOpen ? contentRef.current.scrollHeight : 0);
    }
  }, [isOpen]);

  if (!isInTeam) {
    return (
      <div className='rounded-400 flex h-fit w-full flex-col gap-6 overflow-hidden bg-gray-800 transition-all duration-300'>
        <p className='body-1 text-center text-gray-400'>
          팀에 가입하지 않았어요
        </p>
        <MediumButton
          text={'팀 가입하기'}
          isOutlined={false}
          onClick={onClickMainButton}
        ></MediumButton>
      </div>
    );
  } else if (recentSchedule.length === 0 || scheduleDifferece < -86400000) {
    return (
      <div className='rounded-400 flex h-fit w-full flex-col gap-6 overflow-hidden bg-gray-800 transition-all duration-300'>
        <p className='body-1 text-center text-gray-400'>다음 일정이 비어있</p>
        <MediumButton
          text={'스케줄 추가하기'}
          isOutlined={false}
          onClick={onClickMainButton}
        ></MediumButton>
      </div>
    );
  } else if (scheduleDifferece < 0) {
    return (
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
    );
  } else {
    return (
      <div
        className={classNames(
          'rounded-400 flex h-fit w-full flex-col overflow-hidden bg-gray-800 transition-all duration-300',
        )}
      >
        <div className='flex flex-col items-center justify-center pt-10'>
          <p>
            {'다음 일정까지 '}
            <span>{`D-${1}`}</span>
          </p>
          <h1>{recentSchedule.name}</h1>
          <Tag
            type={TagType.TEAM_SCHEDULE}
            children={{ date: '12일 목요일', time: '17:00' }}
          />
        </div>
        <hr className='w-full border-gray-500' />
        {/* 나의 역할 */}
        {recentSchedule.roles.find((role) => role.memberId === '1') ?
          <div className='flex flex-col gap-3'>
            <Tag type={TagType.MY_ROLE}></Tag>
            <div className='flex flex-col gap-1'>
              {myRole.map((role, index) => (
                <li key={index} className='body-1 pl-1 text-gray-100'>
                  {role}
                </li>
              ))}
            </div>
          </div>
        : <div className='flex flex-col gap-6'>
            <p className='body-1 text-center text-gray-400'>
              나의 역할이 비어있어요
            </p>
            {/* 일정 종료 전에만 나의 역할 추가 버튼 표시 */}
            <MediumButton
              text={'나의 역할 추가하기'}
              isOutlined={false}
            ></MediumButton>
          </div>
        }
        {/* 팀원 역할 */}
        <div
          ref={contentRef}
          className={`flex flex-col gap-6 overflow-hidden transition-all duration-300 ease-in-out ${isOpen ? 'mb-0' : 'mb-[-24px]'}`}
          style={contentRef.current ? { height: `${height}px` } : { height: 0 }}
        >
          {recentSchedule.roles.map((role, index) => {
            if (role.memberId === 1) return null;
            return (
              <div key={index} className='flex flex-col gap-3'>
                <Tag type={TagType.MEMBER_ROLE}>{role.name}</Tag>
                {role.task.length > 0 ?
                  <ul className='flex flex-col gap-1'>
                    {role.task.map((task, index) => (
                      <li key={index} className='body-1 pl-1 text-gray-100'>
                        {task}
                      </li>
                    ))}
                  </ul>
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
                name={isOpen ? 'chevronUp' : 'chevronDown'}
                color='lime-500'
                className='h-max w-max'
              />
              {isOpen ? '닫기' : '팀원 역할 보기'}
            </div>
          }
          onClick={() => setIsOpen(!isOpen)}
          isOutlined={true}
          disabled={true}
        ></MediumButton>
      </div>
    );
  }
}
