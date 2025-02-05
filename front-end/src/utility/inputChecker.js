import { CertState } from '../components/Certification';
import { showToast } from './handleToast';

export function transformToBytes(str) {
  let byteCount = 0;

  let overflowedIndex = 0;
  for (const ch of str) {
    // 한글 완성형 (AC00-D7A3), 자음/모음 (3131-318E)
    if (
      (ch >= '\u3131' && ch <= '\u318E') ||
      (ch >= '\uAC00' && ch <= '\uD7A3')
    ) {
      byteCount += 2;
    } else {
      byteCount += 1;
    }
  }

  return { byteCount: byteCount, overflowedIndex: overflowedIndex };
}

/**
 * 10바이트 이하인지 확인
 * @param {string} str - 문자열
 * @returns {boolean} - 10바이트 이하인지 여부
 */
export function isWithin10Bytes(str) {
  return transformToBytes(str).byteCount <= 10;
}

/**
 * 한글, 영어, 특수문자 섞여있는지 확인
 * @param {string} content - 문자열
 * @returns {boolean} - 복잡도 검사 결과
 */
export function isValidComplexity(content) {
  return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/.test(content);
}

/**
 * 8글자 이상인지 확인
 * @param {string} content - 문자열
 * @returns {boolean} - 8글자 이상인지 여부
 */
export function isValidLength(content) {
  return content.length >= 8;
}

/**
 * 복잡도 검사 및 8글자 이상인지 확인
 * @param {string} content - 문자열
 * @returns {boolean} - 복잡도 검사 및 8글자 이상인지 여부
 */
export function isValidPassword(content) {
  return isValidComplexity(content) && isValidLength(content);
}

/**
 * 이메일 형식인지 확인
 * @param {string} content - 문자열
 * @returns {boolean} - 이메일 형식인지 여부
 */
export function isValidEmail(content) {
  return /^\S+@\S+$/.test(content);
}

/**
 * 회원가입 정보 검사
 * @param {string} certState - 인증 상태
 * @param {string} password - 비밀번호
 * @param {string} passwordConfirm - 비밀번호 확인
 * @param {string} nickName - 활동 이름
 * @returns {boolean} - 회원가입 정보 검사 결과
 */
export const checkSignUpInfos = (
  certState,
  password,
  passwordConfirm,
  nickName,
) => {
  return (
    certState === CertState.AFTER_CHECK_CODE &&
    isValidPassword(password) &&
    password === passwordConfirm &&
    nickName.length > 0 &&
    isWithin10Bytes(nickName)
  );
};

/**
 * 로그인 정보 검사
 * @param {string} email - 이메일
 * @param {string} password - 비밀번호
 * @returns {boolean} - 로그인 정보 검사 결과
 */
export const checkSignInInfos = (email, password) => {
  if (!isValidEmail(email)) {
    showToast('이메일 형식이 올바르지 않습니다');
    return false;
  } else if (!isValidPassword(password)) {
    return false;
  }
  return true;
};

/**
 * 팀 공간 이름 검사
 * @param {string} teamSpaceName - 팀 공간 이름
 * @returns {boolean} - 팀 공간 이름 검사 결과
 */
export const isValidTeamName = (teamSpaceName) => {
  if (teamSpaceName.length === 0) {
    showToast('팀 이름을 입력해주세요');
    return false;
  }
  return true;
};

/**
 * 종료일 검사
 * @param {Date} startDate - 시작일
 * @param {Date} endDate - 종료일
 * @returns {boolean} - 종료일 검사 결과
 */
export const isValidEndDate = (startDate, endDate) => {
  if (startDate > endDate) {
    showToast('종료일은 시작일보다 빠를 수 없습니다');
    return false;
  }
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  if (endDate < today) {
    showToast('종료일은 오늘 이후여야 합니다');
    return false;
  }
  return true;
};

/**
 * 팀 공간 만들기 정보 검사
 * @param {string} teamName - 팀 이름
 * @param {Date} startDate - 시작일
 * @param {Date} endDate - 종료일
 * @returns {boolean} - 팀 공간 만들기 정보 검사 결과
 */
export const checkTeamSpaceMakingInfo = (teamName, startDate, endDate) => {
  if (!isValidTeamName(teamName)) {
    return false;
  } else if (!isValidEndDate(startDate, endDate)) {
    return false;
  }
  return true;
};
