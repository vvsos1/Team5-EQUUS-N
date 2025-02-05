import { forwardRef, useState } from 'react';
import DatePicker from 'react-datepicker';
import Icon from '../../../components/Icon';
import { changeDayName } from '../../../utility/time';

export default function CustomDatePicker({
  date,
  setDate,
  isStartTime = true,
}) {
  const [isPickerOpen, setIsPickerOpen] = useState(false);
  return (
    <div className='relative w-full'>
      <DatePicker
        dateFormat='MM/dd'
        selected={date}
        onChange={(date) => setDate(date)}
        customInput={
          <DatePickerDropdown
            isStartTime={isStartTime}
            isPickerOpen={isPickerOpen}
          />
        }
        onCalendarOpen={() => setIsPickerOpen(true)}
        onCalendarClose={() => setIsPickerOpen(false)}
        dayClassName={(d) => {
          d.getDate() === date?.getDate() ? 'bg-red-500' : 'bg-lime-500';
        }}
        formatWeekDay={(nameOfDay) => {
          return changeDayName(nameOfDay.substring(0, 3));
        }}
        // withPortal
        popperPlacement='bottom-end'
      />
    </div>
  );
}

const DatePickerDropdown = forwardRef(function DatePickerDropdown(
  { value, isStartTime, onClick, isPickerOpen },
  ref,
) {
  return (
    <div ref={ref} className='flex-1' onClick={onClick}>
      <div
        className={`rounded-200 body-1 text-gray-0 flex cursor-pointer items-center justify-between border px-5 py-3.5 ${isPickerOpen ? 'border-gray-300' : 'border-gray-600'}`}
      >
        <p className='text-center overflow-ellipsis'>
          {value + (isStartTime ? ' 부터' : ' 까지')}
        </p>
        <Icon
          name='chevronDown'
          className={`ml-0.5 transition ${isPickerOpen ? 'rotate-180' : ''}`}
        />
      </div>
    </div>
  );
});
