import { forwardRef, useState } from 'react';
import DatePicker from 'react-datepicker';
import { DropdownLarge } from '../../../components/Dropdown';

export default function CustomDatePicker({ date, setDate, isFromTime = true }) {
  const CustomDropdown = forwardRef(({ value, onClick }, ref) => {
    return (
      <DropdownLarge
        externalRef={ref}
        onClick={onClick}
        isTransparent={true}
        isFromTime={isFromTime}
        triggerText={value}
        setTriggerText={() => {}}
        itemsComponent={<div></div>}
      />
    );
  });

  return (
    <DatePicker
      dateFormat='MM/dd'
      selected={date}
      onChange={(date) => setDate(date)}
      customInput={<CustomDropdown />}
      className
      dayClassName={(d) => {
        d.getDate() === date?.getDate() ? 'bg-red-500' : 'bg-lime-500';
      }}
      formatWeekDay={(nameOfDay) => nameOfDay.substring(0, 1)}
      popperClassName='left-10'
      // popperModifiers={}
    />
  );
}
