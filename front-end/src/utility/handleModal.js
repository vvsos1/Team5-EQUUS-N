import { createRoot } from 'react-dom/client';

let modalRoot = null;

/**
 * 모달을 띄우는 함수
 * @param {ReactElement} reactElement
 */
export function showModal(reactElement) {
  const modal = document.querySelector('dialog');

  // 'dialog' 요소를 루트로 지정하여, 인자로 받은 reactElement를 루트에 바로 하위에 렌더링
  if (modal) {
    if (!modalRoot) {
      modalRoot = createRoot(modal);
    }
    modalRoot.render(reactElement);
    modal.showModal();
  }
}

/**
 * 모달을 숨기는 함수
 */
export function hideModal() {
  document.querySelector('dialog').close();
}
