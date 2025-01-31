import { createRoot } from 'react-dom/client';

let modalRoot = null;

export function showModal(reactElement) {
  console.log(reactElement);
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
  return (
    <dialog className='m-auto transition-all duration-300 backdrop:bg-black/60 backdrop:backdrop-blur-xs open:opacity-100 starting:open:opacity-0' />
  );
}
