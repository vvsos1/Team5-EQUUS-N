import { useEffect, useRef, useState } from 'react';
import { SelectedDateInfo } from './components/CalendarParts';
import CalendarWeeks from './components/CalendarWeeks';
import Accordion from '../../components/Accordion';
import ScheduleCard from './components/ScheduleCard';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import Icon from '../../components/Icon';
import { checkIsFinished, timeInPeriod } from '../../utility/time';
import { ScheduleActionType } from './components/ScheduleAction';
import ScheduleAction from './components/ScheduleAction';
import { useGetSchedules } from '../../api/useCalendar';
import { useMyTeams } from '../../api/useAuth';

export default function Calendar() {
  const [allSchedules, setAllSchedules] = useState(null);
  const [selectedDate, setSelectedDate] = useState(
    new Date(new Date().setHours(0, 0, 0, 0)),
  );
  const [isScrolling, setIsScrolling] = useState(false);
  const [selectedTeamId, setSelectedTeamId] = useState(1);
  const scrollRef = useRef(null);
  const [scheduleOnDate, setScheduleOnDate] = useState(null);
  const [scheduleSet, setScheduleSet] = useState(new Set());
  const [isDisplaying, setIsDisplaying] = useState(false);
  const [actionType, setActionType] = useState(ScheduleActionType.ADD);
  const [teamList, setTeamList] = useState([]);

  const { data: schedules, isLoading: isSchedulesLoading } = useGetSchedules();
  const { data: teams, isLoading: isTeamsLoading } = useGetTeams();

  useEffect(() => {
    if (isSchedulesLoading || isTeamsLoading) return;
    setAllSchedules(schedules);
    setTeamList(teams);
  }, [schedules, teams, isSchedulesLoading, isTeamsLoading]);

  useEffect(() => {
    setScheduleSet(
      new Set(
        allSchedules
          ?.filter((data) => {
            return data.teamId === selectedTeamId;
          })
          ?.map(
            (data) =>
              new Date(data.scheduleInfo.startTime).toISOString().split('T')[0],
          ) ?? [],
      ),
    );
  }, [allSchedules, selectedTeamId]);

  useEffect(() => {
    if (!allSchedules) return;
    setScheduleOnDate(
      allSchedules.filter((data) => {
        return timeInPeriod(
          new Date(data.scheduleInfo.startTime),
          selectedDate,
          new Date(selectedDate.getTime() + 86400000),
        );
      }),
    );
  }, [selectedDate]);

  useEffect(() => {
    if (!scrollRef.current) return;
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

  if (isSchedulesLoading || isTeamsLoading) return <div>Loading...</div>;
  console.log(schedules, teams);

  return (
    <div
      ref={scrollRef}
      className='scrollbar-hidden relative size-full overflow-x-hidden overflow-y-auto'
    >
      <StickyWrapper>
        <Accordion
          isMainPage={false}
          selectedTeamId={selectedTeamId}
          teamList={teamList}
          onTeamClick={setSelectedTeamId}
          canClose={!isDisplaying}
          onClickLastButton={() => {
            setSelectedTeamId(-1);
          }}
        />
        <SelectedDateInfo date={selectedDate} isScrolling={isScrolling} />
      </StickyWrapper>
      <CalendarWeeks
        selectedDate={selectedDate}
        setSelectedDate={setSelectedDate}
        scheduleSet={scheduleSet}
      />
      <ul className='flex flex-col gap-6'>
        {scheduleOnDate &&
          scheduleOnDate.map((schedule, index) => {
            if (schedule.teamId !== selectedTeamId) return null;
            return (
              <li key={index} className='last:mb-5'>
                <ScheduleCard
                  teamName={schedule.teamName}
                  schedule={schedule.scheduleInfo}
                  todos={schedule.todos}
                  isFinished={checkIsFinished(schedule.scheduleInfo.endTime)}
                  onClickEdit={() => {
                    setActionType(ScheduleActionType.EDIT);
                    setIsDisplaying(true);
                  }}
                />
              </li>
            );
          })}
        <li className='mb-5'>
          <LargeButton
            text={
              <p className='button-1 flex items-center gap-2 text-gray-300'>
                <Icon name='plusS' />
                새로운 일정 추가
              </p>
            }
            onClick={() => {
              setActionType(ScheduleActionType.ADD);
              setIsDisplaying(true);
            }}
            isOutlined={true}
            disabled={true}
          />
        </li>
      </ul>
      {scheduleOnDate && (
        <ScheduleAction
          type={actionType}
          isOpen={isDisplaying}
          selectedDateFromParent={selectedDate}
          selectedSchedule={scheduleOnDate.find(
            (schedule) => schedule.teamId === selectedTeamId,
          )}
          onClose={() => setIsDisplaying(false)}
          onSubmit={(postSuccess) => {
            setIsDisplaying(false);
            if (postSuccess) {
              // TODO: 일정 재조회
            }
          }}
        />
      )}
    </div>
  );
}
