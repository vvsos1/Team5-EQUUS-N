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
    selectedSchedule?.todos?.filter((todo) => {
      return todo.memberId === userId;
    }).task ?? [],
  );

  useEffect(() => {
    setSelectedDate(date);
  }, [date]);

  useEffect(() => {
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
      selectedSchedule?.todos?.find((todo) => {
        return todo.memberId === userId;
      })?.task ?? [];
    setTodo(newTodos);
  }, [selectedSchedule]);

  return {
    doingAction,
    setDoingAction,
    actionType,
    setActionType,
    selectedDate,
    setSelectedDate,
    scheduleName,
    setScheduleName,
    startTime,
    setStartTime,
    endTime,
    setEndTime,
    todos,
    setTodo,
  };
}
