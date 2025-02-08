import { DropdownLarge } from '../../../components/Dropdown';
import { timeOptions } from '../../../utility/time';

export default function TimeSelector({
  startTime,
  setStartTime,
  endTime,
  setEndTime,
}) {
  return (
    <div className='flex flex-col gap-2'>
      <h3 className='subtitle-2 text-gray-0'>일정 시간</h3>
      <div className='flex items-center gap-3'>
        <DropdownLarge
          triggerText={startTime}
          setTriggerText={setStartTime}
          isFromTime={true}
          items={timeOptions}
        />
        <DropdownLarge
          triggerText={endTime}
          setTriggerText={setEndTime}
          isFromTime={false}
          items={timeOptions}
        />
      </div>
    </div>
  );
}
