export function isWithin10Bytes(str) {
  let byteCount = 0;

  for (const ch of str) {
    // 한글 완성형 (U+AC00 ~ U+D7A3)
    if (ch >= '\uAC00' && ch <= '\uD7A3') {
      byteCount += 2;
    } else {
      byteCount += 1;
    }
  }

  return byteCount <= 10;
}
