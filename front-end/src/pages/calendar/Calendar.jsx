import { useEffect, useState } from 'react';
import { SelectedDateInfo } from './components/CalendarParts';
import CalendarWeeks from './components/CalendarWeeks';
import Accordion from '../../components/Accordion';
import ScheduleCard from './components/ScheduleCard';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import LargeButton from '../../components/buttons/LargeButton';
import Icon from '../../components/Icon';
import { checkIsFinished, getRecentSunday } from '../../utility/time';
import { ScheduleActionType } from './components/ScheduleAction';
import ScheduleAction from './components/ScheduleAction';
import { useGetSchedules } from '../../api/useCalendar';
import { useLocation } from 'react-router-dom';
import useSchedule from './hooks/useSchedule';
import useScheduleAction from './hooks/useScheduleAction';
import useCalendarScroll from './hooks/useCalendarScroll';
import { teams2 } from '../../mocks/mockData2';
import { useMyTeams } from '../../api/useAuth';
import { useTeam } from '../../useTeam';

export default function Calendar() {
  const location = useLocation();
  const [selectedDate, setSelectedDate] = useState(
    location.state?.initialDate ?? new Date('2025-01-24'),
  );
  const [searchingDate, setSearchingDate] = useState(
    getRecentSunday(selectedDate),
  );

  const { allSchedules, setAllSchedules, scheduleOnDate, scheduleSet } =
    useSchedule(selectedDate);
  const { doingAction, setDoingAction, actionType, setActionType } =
    useScheduleAction();
  const { scrollRef, isScrolling } = useCalendarScroll();

  const { teams, selectedTeam, selectTeam, setTeams } = useTeam();
  const { data: teamsData } = useMyTeams();

  useEffect(() => {
    if (teamsData) {
      setTeams(teamsData);
    }
  }, [teamsData]);
  const {
    data: schedules,
    isLoading: isSchedulesLoading,
    refetch,
  } = useGetSchedules(
    {
      teamId: teams2[0].id,
      startDay: searchingDate.toISOString(),
      endDay: searchingDate.toISOString(),
    },
    setAllSchedules,
  );

  useEffect(() => {
    if (isSchedulesLoading) return;
    if (isSchedulesLoading) return;
    setAllSchedules(schedules);
  }, [schedules, isSchedulesLoading]);

  useEffect(() => {
    setScheduleSet(
      new Set(
        allSchedules
          ?.filter((data) => {
            return data.teamId === selectedTeam;
          })
          ?.map(
            (data) =>
              new Date(data.scheduleInfo.startTime).toISOString().split('T')[0],
          ) ?? [],
      ),
    );
  }, [allSchedules, selectedTeam]);

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

  return (
    <div
      ref={scrollRef}
      className='scrollbar-hidden relative size-full overflow-x-hidden overflow-y-auto'
    >
      <StickyWrapper>
        <Accordion
          isMainPage={false}
          selectedTeamId={selectedTeam}
          teamList={teams}
          onTeamClick={selectTeam}
          canClose={!isDisplaying}
          onClickLastButton={() => {
            selectTeam(-1);
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
            if (schedule.teamId !== selectedTeam) return null;
            return (
              <li key={index} className='last:mb-5'>
                <ScheduleCard
                  teamName={schedule.teamName}
                  schedule={schedule.scheduleInfo}
                  todos={schedule.todos}
                  isFinished={checkIsFinished(schedule.scheduleInfo.endTime)}
                  onClickEdit={() => {
                    setActionType(ScheduleActionType.EDIT);
                    setDoingAction(true);
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
              setDoingAction(true);
            }}
            isOutlined={true}
            disabled={true}
          />
        </li>
      </ul>
      {scheduleOnDate && (
        <ScheduleAction
          type={actionType}
          isOpen={doingAction}
          selectedDateFromParent={selectedDate}
          selectedSchedule={scheduleOnDate.find(
            (schedule) => schedule.teamId === selectedTeam,
          )}
          onClose={() => setDoingAction(false)}
          onSubmit={(postSuccess) => {
            setDoingAction(false);
            if (postSuccess) {
              // TODO: 일정 재조회
            }
          }}
        />
      )}
    </div>
  );
}
