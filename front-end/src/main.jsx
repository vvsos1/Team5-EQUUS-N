import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App3 from './App3.jsx';
import ModalBase from './components/modals/ModalBase.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App3 />
    {/* 모달을 이렇게 최상위에 둘 예정임다 */}
    <ModalBase />
  </StrictMode>,
);
