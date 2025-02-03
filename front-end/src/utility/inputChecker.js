import { CertState } from '../components/Certification';
import { showToast } from './handleToast';

export function isWithin10Bytes(str) {
  let byteCount = 0;

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

  return byteCount <= 10;
}

export function isValidComplexity(content) {
  return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[^A-Za-z0-9]).+$/.test(content);
}

export function isValidLength(content) {
  return content.length >= 8;
}

export function isValidPassword(content) {
  return isValidComplexity(content) && isValidLength(content);
}

export function isValidEmail(content) {
  return /^\S+@\S+$/.test(content);
}

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

export const checkSignInInfos = (email, password) => {
  if (!isValidEmail(email)) {
    showToast('이메일 형식이 올바르지 않습니다');
    return false;
  } else if (!isValidPassword(password)) {
    return false;
  }
  return true;
};
