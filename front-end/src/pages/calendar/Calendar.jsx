import { useEffect, useRef, useState } from 'react';
import { SelectedDateInfo } from './components/CalendarParts';
import CalendarWeeks from './components/CalendarWeeks';
import Accordion from '../../components/Accordion';
import MainCard from '../main/components/MainCard';
import ScheduleCard from './components/ScheduleCard';

export default function Calendar() {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [isScrolling, setIsScrolling] = useState(false);
  const [isFinished, setIsFinished] = useState(false);
  const scrollRef = useRef(null);

  useEffect(() => {
    if (selectedDate.getTime().valueOf() < new Date().setHours(0, 0, 0, 0)) {
      setIsFinished(true);
    } else {
      setIsFinished(false);
    }
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
    <div ref={scrollRef} className='scrollbar-hidden h-screen overflow-y-auto'>
      <div className='sticky top-0 z-10 bg-gray-900'>
        <Accordion
          isMainPage={false}
          selectedTeamId={1}
          teamList={[]}
          onTeamClick={() => {}}
        />
        <SelectedDateInfo date={selectedDate} isScrolling={isScrolling} />
      </div>
      <CalendarWeeks
        selectedDate={selectedDate}
        setSelectedDate={setSelectedDate}
      />
      <ul className='flex flex-col gap-6'>
        {exampleDatas.map((schedule, index) => {
          return (
            <li key={index} className='last:mb-5'>
              <ScheduleCard
                teamName={schedule.teamName}
                schedule={schedule.schedule}
                roles={schedule.roles}
                isFinished={isFinished}
              />
            </li>
          );
        })}
      </ul>
    </div>
  );
}

const exampleDatas = [
  {
    teamName: '소프티어 5조',
    schedule: { start: '17:00', content: '스케줄 내용' },
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
    teamName: '에쿠스 N',
    schedule: { start: '17:00', content: '스케줄 내용' },
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
    teamName: '협곡의 전사들',
    schedule: { start: '17:00', content: '스케줄 내용' },
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
    teamName: '한박백임',
    schedule: { start: '17:00', content: '스케줄 내용' },
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
