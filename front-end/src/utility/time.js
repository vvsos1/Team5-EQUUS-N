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

export function getRecentSunday(date) {
  const sunday = new Date(date);
  sunday.setDate(sunday.getDate() - sunday.getDay());
  return sunday;
}
