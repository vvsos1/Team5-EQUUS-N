import { createRoot } from 'react-dom/client';
import Toast from '../components/toasts/Toast';

let toastRoot = null;
let isToastDisplaying = false;

export function showToast(content) {
  if (isToastDisplaying) {
    return;
  }
  isToastDisplaying = true;

  const toastBase = document.getElementById('toast-base');

  if (!toastRoot) {
    toastRoot = createRoot(toastBase);
  }
  toastRoot.render(<Toast content={content} />);

  setTimeout(() => {
    toastRoot.render(null);
    isToastDisplaying = false;
  }, 2200);
}
