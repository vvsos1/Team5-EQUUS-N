import { useRef } from 'react';
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

export function hideModal() {
  document.querySelector('dialog').close();
}

export default function Modal() {
  const dialogRef = useRef(null);

  return (
    <dialog
      ref={dialogRef}
      onClick={(event) =>
        dialogRef.current && dialogRef.current === event.target && hideModal()
      }
      className='m-auto bg-transparent transition-all duration-300 backdrop:bg-black/60 backdrop:backdrop-blur-xs open:opacity-100 starting:open:opacity-0'
    />
  );
}
