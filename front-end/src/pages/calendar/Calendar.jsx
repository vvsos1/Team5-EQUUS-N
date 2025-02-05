import { useEffect, useRef, useState } from 'react';
import { SelectedDateInfo } from './components/CalendarParts';
import CalendarWeeks from './components/CalendarWeeks';
import Accordion from '../../components/Accordion';
import ScheduleCard from './components/ScheduleCard';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import Icon from '../../components/Icon';
import { checkIsFinished, timeInPeriod } from '../../utility/time';

export default function Calendar() {
  const [selectedDate, setSelectedDate] = useState(
    new Date(new Date().setHours(0, 0, 0, 0)),
  );
  const [isScrolling, setIsScrolling] = useState(false);
  const [selectedTeamId, setSelectedTeamId] = useState(1);
  const scrollRef = useRef(null);
  const [scheduleOnDate, setScheduleOnDate] = useState(exampleSchedules);
  const [scheduleSet, setScheduleSet] = useState(new Set());

  useEffect(() => {
    setScheduleSet(
      new Set(
        exampleSchedules
          .filter((data) => {
            return data.teamId === selectedTeamId;
          })
          .map(
            (data) =>
              new Date(data.schedule.startTime).toISOString().split('T')[0],
          ),
      ),
    );
  }, [exampleSchedules, selectedTeamId]);

  useEffect(() => {
    setScheduleOnDate(
      exampleSchedules.filter((data) => {
        return timeInPeriod(
          new Date(data.schedule.startTime),
          selectedDate,
          new Date(selectedDate.getTime() + 86400000),
        );
      }),
    );
  }, [selectedDate]);

  useEffect(() => {
    const container = scrollRef.current;

    const handleScroll = () => {
      const scrollPosition = container.scrollTop;
      if (scrollPosition > 50) {
        setIsScrolling(true);
      } else {
        setIsScrolling(false);
      }
    };

    container.addEventListener('scroll', handleScroll);

    return () => {
      container.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <div
      ref={scrollRef}
      className='scrollbar-hidden size-full overflow-x-hidden overflow-y-auto'
    >
      <StickyWrapper>
        <Accordion
          isMainPage={false}
          selectedTeamId={selectedTeamId}
          teamList={exampleTeamList}
          onTeamClick={setSelectedTeamId}
        />
        <SelectedDateInfo date={selectedDate} isScrolling={isScrolling} />
      </StickyWrapper>
      <CalendarWeeks
        selectedDate={selectedDate}
        setSelectedDate={setSelectedDate}
        scheduleSet={scheduleSet}
      />
      <ul className='flex flex-col gap-6'>
        {scheduleOnDate.map((schedule, index) => {
          if (schedule.teamId !== selectedTeamId) return null;
          return (
            <li key={index} className='last:mb-5'>
              <ScheduleCard
                teamName={schedule.teamName}
                schedule={schedule.schedule}
                roles={schedule.roles}
                isFinished={checkIsFinished(schedule.schedule.endTime)}
              />
            </li>
          );
        })}
        <li className='mb-5'>
          <LargeButton
            text={
              <p className='flex items-center gap-2'>
                <Icon name='plusS' />
                새로운 일정 추가
              </p>
            }
            onClick={() => {}}
            isOutlined={true}
            disabled={true}
          />
        </li>
      </ul>
    </div>
  );
}

const exampleTeamList = [
  {
    id: 1,
    name: '소프티어 5조',
  },
  {
    id: 2,
    name: '에쿠스 N',
  },
  {
    id: 3,
    name: '협곡의 전사들',
  },
  {
    id: 4,
    name: '한박백임',
  },
];

const exampleSchedules = [
  {
    teamId: 1,
    teamName: '소프티어 5조',
    schedule: {
      startTime: '2025-02-05T17:00:00.000Z',
      endTime: '2025-02-05T18:00:00.000Z',
      content: '스케줄 내용',
    },
    roles: [
      // {
      //   memberId: 1,
      //   name: '임세준',
      //   task: ['데스크 리서치 하기', '설문지 작성하기'],
      // },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 2,
    teamName: '에쿠스 N',
    schedule: {
      startTime: '2025-02-04T17:00:00.000Z',
      endTime: '2025-02-04T18:00:00.000Z',
      content: '스케줄 내용',
    },
    roles: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 3,
    teamName: '협곡의 전사들',
    schedule: {
      startTime: '2025-02-07T17:00:00.000Z',
      endTime: '2025-02-08T18:00:00.000Z',
      content: '스케줄 내용',
    },
    roles: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
  {
    teamId: 4,
    teamName: '한박백임',
    schedule: {
      startTime: '2025-02-06T17:00:00.000Z',
      endTime: '2025-02-06T19:00:00.000Z',
      content: '스케줄 내용',
    },
    roles: [
      {
        memberId: 1,
        name: '임세준',
        task: ['데스크 리서치 하기', '설문지 작성하기'],
      },
      {
        memberId: 2,
        name: '백현식',
        task: ['레퍼런스 리서치 하기', '설문지 배포하기'],
      },
      {
        memberId: 3,
        name: '한준호',
        task: [],
      },
      {
        memberId: 4,
        name: '박명규',
        task: ['프레젠테이션 연습하기', '설문지 작성하기'],
      },
    ],
  },
];
