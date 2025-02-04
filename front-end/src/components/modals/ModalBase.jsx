import { useRef } from 'react';
import { hideModal } from '../../utility/handleModal';

export default function ModalBase() {
  const dialogRef = useRef(null);

  return (
    <dialog
      ref={dialogRef}
      onClick={(event) =>
        dialogRef.current && dialogRef.current === event.target && hideModal()
      }
      className='m-auto w-full bg-transparent transition-all duration-300 backdrop:bg-black/60 backdrop:backdrop-blur-xs open:opacity-100 starting:open:opacity-0'
    />
  );
}
