import { Outlet } from 'react-router-dom';
import ModalBase from './components/modals/ModalBase';
import ToastBase from './components/toasts/ToastBase';

function Layout() {
  return (
    <div className='h-dvh w-dvw bg-gray-900'>
      <div className='scrollbar-hidden mx-auto h-full w-full max-w-[430px] overflow-x-hidden overflow-y-auto px-5'>
        <Outlet />
      </div>
      <ModalBase />
      <ToastBase />
    </div>
  );
}

export default Layout;
