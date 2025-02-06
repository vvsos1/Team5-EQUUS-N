/**
 * 시간 경과 계산 함수
 * @param {Date} date - 기준 날짜
 * @returns {string} - 시간 경과 문자열
 */
export function calTimePassed(date) {
  const now = new Date();
  const diffMs = Math.abs(now - date);
  const diffSeconds = Math.floor(diffMs / 1000);
  const diffMinutes = Math.floor(diffSeconds / 60);
  const diffHours = Math.floor(diffMinutes / 60);
  const diffDays = Math.floor(diffHours / 24);
  if (diffSeconds < 60) {
    return `방금 전`;
  } else if (diffMinutes < 60) {
    return `${diffMinutes}분 전`; // 1시간 이내
  } else if (diffHours < 24) {
    return `${diffHours}시간 전`; // 1일 이내
  } else {
    return `${diffDays}일 전`; // 1일 이후
  }
}

/**
 * 날짜 정보 계산 함수
 * @param {Date} date - 기준 날짜
 * @returns {object} - 날짜 정보 객체
 */
export function getDateInfo(date) {
  // 요일 배열 (일요일부터 시작)
  const weekDays = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];

  // 해당 날짜의 요일 구하기
  const weekDay = weekDays[date.getDay()];

  // 해당 월의 1일
  const firstDay = new Date(date.getFullYear(), date.getMonth(), 1);

  // 입력된 날짜
  const targetDate = new Date(date);

  // 1일의 요일 구하기 (0: 일요일, 1: 월요일, ...)
  const firstDayOfWeek = firstDay.getDay();

  // 날짜 차이 계산
  const diff = Math.ceil((targetDate.getDate() + firstDayOfWeek) / 7);

  return {
    weekDay, // 요일
    monthWeek: `${date.getMonth() + 1}월 ${diff}주차`, // 몇 월 몇 주차
    year: date.getFullYear(), // 년도
  };
}

/**
 * 최근 일요일 계산 함수
 * @param {Date} date - 기준 날짜
 * @returns {Date} - 최근 일요일 날짜
 */
export function getRecentSunday(date) {
  const sunday = new Date(new Date(date).setHours(0, 0, 0, 0));
  sunday.setDate(sunday.getDate() - sunday.getDay());
  return sunday;
}

export function changeDayName(dayName) {
  let day = '';
  if (dayName === 'Sun') {
    day = '일';
  } else if (dayName === 'Mon') {
    day = '월';
  } else if (dayName === 'Tue') {
    day = '화';
  } else if (dayName === 'Wed') {
    day = '수';
  } else if (dayName === 'Thu') {
    day = '목';
  } else if (dayName === 'Fri') {
    day = '금';
  } else if (dayName === 'Sat') {
    day = '토';
  }
  return day;
}

/**
 * 기준 날짜가 시작 날짜와 종료 날짜 사이에 있는지 확인하는 함수
 * @param {Date} stdDate - 기준 날짜
 * @param {Date} startDate - 시작 날짜
 * @param {Date} endDate - 종료 날짜
 * @returns {boolean} - 기준 날짜가 시작 날짜와 종료 날짜 사이에 있는지 여부
 */
export function timeInPeriod(stdDate, startDate, endDate) {
  const startTime = new Date(startDate).getTime();
  const endTime = new Date(endDate).getTime();
  const stdTime = new Date(stdDate).getTime();
  return startTime <= stdTime && endTime >= stdTime;
}

/**
 * 현 시간 기준 일정이 종료되었는지 확인하는 함수
 * @param {Date} date - 일정 종료 날짜
 * @returns {boolean} - 일정이 종료되었는지 여부
 */
export function checkIsFinished(date) {
  if (new Date(date).getTime().valueOf() < new Date()) {
    return true;
  } else {
    return false;
  }
}
