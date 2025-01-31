import { use, useEffect, useRef } from 'react';
import { createRoot } from 'react-dom/client';

let modalRoot = null;

export function showModal(reactElement) {
  const modal = document.querySelector('dialog');
  if (modal) {
    if (!modalRoot) {
      modalRoot = createRoot(modal);
    }
    modalRoot.render(reactElement);
    modal.showModal();
  }
}

export function hideModal() {
  const modal = document.querySelector('dialog');
  modal.close();
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
