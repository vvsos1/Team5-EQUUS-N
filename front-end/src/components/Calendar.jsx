import { useState, useRef, useEffect } from 'react';
import { getRecentSunday } from '../utility/time';
import { CalendarWeek, SelectedDateInfo } from './CalendarParts';
import classNames from 'classnames';

export default function Calendar() {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const [curSunday, setCurSunday] = useState(getRecentSunday(new Date()));
  const [isDragging, setIsDragging] = useState(false);
  const startX = useRef(0);
  const scrollLeft = useRef(0);
  const containerRef = useRef(null);

  useEffect(() => {
    if (!containerRef.current) return;

    const container = containerRef.current;
    container.scrollLeft = container.clientWidth;
  }, []);

  useEffect(() => {
    if (!containerRef.current) return;

    const container = containerRef.current;
    const handleScroll = () => {
      const containerLeft = container.scrollLeft;
      if (containerLeft === 0) {
        flipCalender(container, false);
      } else if (containerLeft === container.clientWidth * 2) {
        flipCalender(container, true);
      }
    };

    container.addEventListener('scroll', handleScroll);
    return () => container.removeEventListener('scroll', handleScroll);
  }, [curSunday]);

  const handleDragStart = (e) => {
    setIsDragging(true);
    startX.current = e.clientX - containerRef.current.offsetLeft;
    scrollLeft.current = containerRef.current.scrollLeft;
  };

  const handleDrag = (e) => {
    // e.preventDefault(); // 이벤트 기본 동작 방지
    if (!isDragging) return;
    const x = e.clientX - containerRef.current.offsetLeft;
    const walk = (x - startX.current) * 1.5;
    containerRef.current.scrollLeft = scrollLeft.current - walk;
  };

  const handleDragEnd = () => {
    setIsDragging(false);
    // 스크롤 위치에 따라 가장 가까운 주로 스냅
    const container = containerRef.current;
    const weekWidth = container.clientWidth;
    const targetScroll =
      Math.round(container.scrollLeft / weekWidth) * weekWidth;

    container.scrollTo({
      left: targetScroll,
      behavior: 'smooth',
    });
  };

  const flipCalender = (container, isNext) => {
    // 즉시 투명하게 만들기
    requestAnimationFrame(() => {
      container.style.opacity = '0';

      // 모든 상태 업데이트를 다음 프레임으로 지연
      setTimeout(() => {
        handleDragEnd();
        const newSunday = new Date(curSunday);
        newSunday.setDate(newSunday.getDate() + (isNext ? 7 : -7));
        const newDate = new Date(selectedDate);
        newDate.setDate(newDate.getDate() + (isNext ? 7 : -7));

        // 상태 업데이트와 스크롤 위치 변경을 동시에
        Promise.all([setCurSunday(newSunday), setSelectedDate(newDate)]).then(
          () => {
            container.scrollLeft = container.clientWidth;
            // 모든 작업이 완료된 후 opacity 복구
            requestAnimationFrame(() => {
              container.style.opacity = '1';
            });
          },
        );
      }, 0);
    });
  };

  return (
    <div>
      <SelectedDateInfo date={selectedDate} />
      <div
        ref={containerRef}
        className={classNames(
          'flex w-full overflow-hidden select-none',
          isDragging && 'cursor-grabbing',
          !isDragging && 'cursor-grab',
        )}
        onMouseDown={handleDragStart}
        onMouseMove={handleDrag}
        onMouseUp={handleDragEnd}
        onMouseLeave={handleDragEnd}
        onTouchEnd={handleDragEnd}
        onTouchStart={handleDragStart}
        onTouchMove={handleDrag}
        onTouchCancel={handleDragEnd}
      >
        <div className='min-w-full px-5 py-4'>
          <CalendarWeek
            curSunday={new Date(curSunday).setDate(curSunday.getDate() - 7)}
            selectedDate={selectedDate}
            setSelectedDate={setSelectedDate}
          />
        </div>
        <div className='min-w-full px-5 py-4'>
          <CalendarWeek
            curSunday={new Date(curSunday)}
            selectedDate={selectedDate}
            setSelectedDate={setSelectedDate}
          />
        </div>
        <div className='min-w-full px-5 py-4'>
          <CalendarWeek
            curSunday={new Date(curSunday).setDate(curSunday.getDate() + 7)}
            selectedDate={selectedDate}
            setSelectedDate={setSelectedDate}
          />
        </div>
      </div>
    </div>
  );
}
