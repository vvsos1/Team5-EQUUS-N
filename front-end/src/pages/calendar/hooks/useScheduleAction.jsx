import { useEffect, useState } from 'react';
import { ScheduleActionType } from '../components/ScheduleAction';
import { useUser } from '../../../useUser';

export default function useScheduleAction(date, selectedSchedule) {
  const { userId } = useUser();
  const [doingAction, setDoingAction] = useState(false);
  const [actionType, setActionType] = useState(ScheduleActionType.ADD);

  const [selectedDate, setSelectedDate] = useState(new Date(date));
  const [scheduleName, setScheduleName] = useState(
    selectedSchedule?.scheduleName ?? '',
  );
  const [startTime, setStartTime] = useState(
    new Date(
      selectedSchedule?.startTime ?? new Date(date).setHours(12, 0, 0, 0),
    ),
  );
  const [endTime, setEndTime] = useState(
    new Date(selectedSchedule?.endTime ?? new Date(date).setHours(12, 0, 0, 0)),
  );
  const [todos, setTodo] = useState(
    selectedSchedule?.scheduleMemberNestedDtoList?.find(
      (dtoList) => dtoList.memberId == userId,
    )?.todoList ?? [],
  );

  const clearData = () => {
    setScheduleName('');
    setStartTime(new Date(new Date(selectedDate).setHours(12, 0, 0, 0)));
    setEndTime(new Date(new Date(selectedDate).setHours(12, 0, 0, 0)));
    setTodo([]);
  };

  useEffect(() => {
    setSelectedDate(date);

    setScheduleName(selectedSchedule?.scheduleName ?? '');
    setStartTime(
      new Date(
        selectedSchedule?.startTime ?? new Date(date).setHours(12, 0, 0, 0),
      ),
    );
    setEndTime(
      new Date(
        selectedSchedule?.endTime ?? new Date(date).setHours(12, 0, 0, 0),
      ),
    );
    const newTodos =
      selectedSchedule?.scheduleMemberNestedDtoList?.find(
        (dtoList) => dtoList.memberId == userId,
      )?.todoList ?? [];
    setTodo(newTodos);
  }, [date, selectedSchedule]);
  // 날짜 바꿔서 일정 만들때 startTime, endTime 제대로 설정하려면 date가 의존성배열에 포함되어야 함

  return {
    doingAction,
    setDoingAction,
    actionType,
    setActionType,
    selectedDate,
    setSelectedDate,
    actionInfo: {
      scheduleName,
      setScheduleName,
      startTime,
      setStartTime,
      endTime,
      setEndTime,
      todos,
      setTodo,
    },
    clearData,
  };
}
