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
