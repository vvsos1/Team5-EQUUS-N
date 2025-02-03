import { createRoot } from 'react-dom/client';

let toastRoot = null;

export function showToast(reactElement) {
  console.log(reactElement);
  const toastBase = document.getElementById('toast-base');

  if (!toastRoot) {
    toastRoot = createRoot(toastBase);
  }
  toastRoot.render(reactElement);
  setTimeout(() => {
    // toastBase.close();
  }, 3000);
}
