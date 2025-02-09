import { useState } from 'react';
import { ScheduleActionType } from '../components/ScheduleAction';

export default function useScheduleAction() {
  const [doingAction, setDoingAction] = useState(false);
  const [actionType, setActionType] = useState(ScheduleActionType.ADD);

  return { doingAction, setDoingAction, actionType, setActionType };
}
