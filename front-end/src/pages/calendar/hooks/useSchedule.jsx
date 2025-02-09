import { useState } from 'react';
import { timeInPeriod } from '../../../utility/time';

export default function useSchedule({ selectedDate }) {
  const [allSchedules, setAllSchedules] = useState([]);
  const [scheduleOnDate, setScheduleOnDate] = useState(null);
  const [scheduleSet, setScheduleSet] = useState(new Set());

  // 해당 날짜에 있는 스케줄 필터링
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

  // 특정 팀 관련 일정들의 날짜 모두 종합
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

  return {
    allSchedules,
    setAllSchedules,
    scheduleOnDate,
    scheduleSet,
  };
}
