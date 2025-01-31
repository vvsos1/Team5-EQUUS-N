import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import './index.css';
import App3 from './App3.jsx';
import Modal from './components/Modal.jsx';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App3 />
    {/* 모달을 이렇게 최상위에 둘 예정임다 */}
    <Modal />
  </StrictMode>,
);
