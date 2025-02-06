import classNames from 'classnames';
import Icon from '../../../components/Icon';
import { useRef, useState } from 'react';
import { changeDayName, timePickerToDate } from '../../../utility/time';
import CustomInput from '../../../components/CustomInput';
import { DropdownLarge } from '../../../components/Dropdown';
import LargeButton from '../../../components/buttons/LargeButton';
import StickyWrapper from '../../../components/wrappers/StickyWrapper';
import { showToast } from '../../../utility/handleToast';
import { checkNewSchedule, isEmpty } from '../../../utility/inputChecker';

export default function ScheduleAdd({
  isOpen,
  onClose,
  onSubmit,
  selectedDate,
}) {
  const [scheduleName, setScheduleName] = useState('');
  const [startTime, setStartTime] = useState('12:00');
  const [endTime, setEndTime] = useState('12:00');
  const [todos, setTodo] = useState([]);
  const scrollRef = useRef(null);

  function clearData() {
    setScheduleName('');
    setStartTime('12:00');
    setEndTime('12:00');
    setTodo([]);
  }

  // 0~24시까지 10분단위로 Date 객체 생성
  const timeOptions = Array.from({ length: 144 }, (_, i) => {
    return (
      `${Math.floor(i / 6)}`.padStart(2, '0') +
      ':' +
      `${(i % 6) * 10}`.padStart(2, '0')
    );
  });

  return (
    <div
      ref={scrollRef}
      className={classNames(
        'rounded-t-400 fixed right-0 left-0 z-1000 flex h-[calc(100%-60px)] max-w-[430px] flex-col overflow-y-auto bg-gray-800 px-5 transition-all duration-300',
        isOpen ? 'visible bottom-0' : 'invisible -bottom-[100%]',
      )}
    >
      <StickyWrapper bgColor='gray-800'>
        <h1 className='subtitle-1 pt-5 text-center text-white'>
          일정 추가하기
        </h1>
        <button
          onClick={() => {
            clearData();
            onClose();
          }}
        >
          <Icon name='delete' className='absolute top-5 right-0 text-white' />
        </button>
      </StickyWrapper>
      <div className='h-8 shrink-0' />
      <div className='flex items-center gap-2'>
        <hr className='h-6 w-1.5 rounded-[2px] bg-lime-500' />
        <h2 className='header-3 text-white'>
          {`${selectedDate.getMonth() + 1}월 ${selectedDate.getDate()}일, ${changeDayName(selectedDate.getDay()) + '요일'}`}
        </h2>
      </div>
      <div className='h-3 shrink-0' />
      <CustomInput
        label='일정 이름'
        content={scheduleName}
        setContent={setScheduleName}
        isOutlined={false}
        bgColor='gray-700'
      />
      <div className='h-11 shrink-0' />
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
      <div className='h-11 shrink-0' />
      <div className='flex flex-col gap-2'>
        <h3 className='subtitle-2 text-gray-0'>내 역할</h3>
        <ul className='flex flex-col gap-2'>
          {todos.map((todo, index) => {
            return (
              <li key={index}>
                <CustomInput
                  content={todo}
                  setContent={(value) => {
                    setTodo(
                      todos.map((todo, index2) => {
                        if (index2 === index) {
                          return value;
                        }
                        return todo;
                      }),
                    );
                  }}
                  isOutlined={false}
                  bgColor='gray-700'
                  addOn={
                    <button
                      onClick={() => {
                        setTodo(todos.filter((_, index2) => index2 !== index));
                      }}
                    >
                      <Icon name='deleteSmall' className='text-gray-300' />
                    </button>
                  }
                />
              </li>
            );
          })}
        </ul>
        <button
          className='rounded-300 flex h-[52px] w-full items-center justify-center border border-gray-400'
          onClick={() => {
            setTodo([...todos, '']);
            scrollRef.current.scrollTo({
              top: scrollRef.current.scrollHeight,
              behavior: 'smooth',
            });
          }}
        >
          <Icon name='plusM' className='text-gray-400' />
        </button>
      </div>
      <div className='h-11 shrink-0' />
      <div className='flex-1' />
      <div
        className={`relative bottom-0 mb-5 max-w-[calc(390px)] transition-all duration-300`}
      >
        <LargeButton
          isOutlined={false}
          text='추가 완료'
          onClick={() => {
            const startDate = timePickerToDate(selectedDate, startTime);
            const endDate = timePickerToDate(selectedDate, endTime);
            const newTodos = todos.filter((todo) => !isEmpty(todo));
            setTodo(newTodos);
            if (checkNewSchedule(scheduleName, startDate, endDate)) {
              // TODO: 일정 추가
              showToast('일정이 추가되었어요');
              clearData();
              onSubmit(true); // 추가 성공여부 파라미터로 받음
            }
          }}
        />
      </div>
    </div>
  );
}
