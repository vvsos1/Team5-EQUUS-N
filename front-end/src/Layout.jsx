import { Outlet } from 'react-router-dom';
import ModalBase from './components/modals/ModalBase';

function Layout() {
  return (
    <div className='h-dvh w-dvw bg-gray-900'>
      <div className='mx-auto h-full w-full max-w-[430px] px-5'>
        <Outlet />
      </div>
      <ModalBase />
    </div>
  );
}

export default Layout;
