import classNames from 'classnames';
import Icon from '../../../components/Icon';
import { useEffect, useRef, useState } from 'react';
import { changeDayName, timePickerToDate } from '../../../utility/time';
import CustomInput from '../../../components/CustomInput';
import LargeButton from '../../../components/buttons/LargeButton';
import StickyWrapper from '../../../components/wrappers/StickyWrapper';
import { showToast } from '../../../utility/handleToast';
import { checkNewSchedule, isEmpty } from '../../../utility/inputChecker';
import TimeSelector from './TimeSelector';
import Todo from './Todo';
import { showModal } from '../../../utility/handleModal';
import ScheduleDeleteModal from './ScheduleDeleteModal';
import CustomDatePicker, {
  DatePickerButton,
} from '../../../components/CustomDatePicker';

export const ScheduleActionType = Object.freeze({
  ADD: 'add',
  EDIT: 'edit',
});

/**
 * 일정 추가 페이지
 * @param {object} props
 * @param {boolean} props.isOpen - 페이지 열림 여부
 * @param {function} props.onClose - 페이지 닫기 함수
 * @param {function} props.onSubmit - 일정 추가 완료 함수
 * @param {Date} props.selectedDate - 선택된 날짜
 */
export default function ScheduleAction({
  type,
  isOpen,
  onClose,
  onSubmit,
  selectedDateFromParent,
}) {
  // 달력 선택 날짜를 기존 날짜로 초기화
  const [selectedDate, setSelectedDate] = useState(selectedDateFromParent);
  const [scheduleName, setScheduleName] = useState('');
  const [startTime, setStartTime] = useState('12:00');
  const [endTime, setEndTime] = useState('12:00');
  const [todos, setTodo] = useState([]);
  const scrollRef = useRef(null);

  useEffect(() => {
    setSelectedDate(selectedDateFromParent);
  }, [selectedDateFromParent]);

  function clearData() {
    setScheduleName('');
    setStartTime('12:00');
    setEndTime('12:00');
    setTodo([]);
  }

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
          {type === ScheduleActionType.ADD ? '일정 추가하기' : '일정 수정하기'}
        </h1>
        {type === ScheduleActionType.EDIT && (
          <button
            onClick={() => showModal(<ScheduleDeleteModal onClose={onClose} />)}
          >
            <Icon name='remove' className='absolute top-5 left-0 text-white' />
          </button>
        )}
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

      {type === ScheduleActionType.ADD ?
        <>
          <div className='flex items-center gap-2'>
            <hr className='h-6 w-1.5 rounded-[2px] bg-lime-500' />
            <h2 className='header-3 text-white'>
              {`${selectedDateFromParent.getMonth() + 1}월 ${selectedDateFromParent.getDate()}일, ${changeDayName(selectedDateFromParent.getDay()) + '요일'}`}
            </h2>
          </div>
          <div className='h-3 shrink-0' />
        </>
      : <>
          <CustomDatePicker
            dateFormat='yyyy-MM-dd eee'
            date={selectedDate}
            setDate={setSelectedDate}
            customInput={<DatePickerButton />}
          />
          <div className='h-11 shrink-0' />
        </>
      }

      <CustomInput
        label='일정 이름'
        content={scheduleName}
        setContent={setScheduleName}
        isOutlined={false}
        bgColor='gray-700'
      />
      <div className='h-11 shrink-0' />

      <TimeSelector
        startTime={startTime}
        setStartTime={setStartTime}
        endTime={endTime}
        setEndTime={setEndTime}
      />

      <div className='h-11 shrink-0' />

      <Todo todos={todos} setTodo={setTodo} scrollRef={scrollRef} />

      <div className='h-11 shrink-0' />
      <div className='flex-1' />

      <div
        className={`relative bottom-0 mb-5 max-w-[calc(390px)] transition-all duration-300`}
      >
        <LargeButton
          isOutlined={false}
          text={type === ScheduleActionType.ADD ? '추가 완료' : '수정 완료'}
          onClick={() => {
            const startDate = timePickerToDate(
              selectedDateFromParent,
              startTime,
            );
            const endDate = timePickerToDate(selectedDateFromParent, endTime);
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
