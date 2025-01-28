import Icon from './Icon';

export default function NavBar2({
  canPop,
  canClose,
  isCloseLeft,
  title = '',
  onClickPop,
  onClickClose,
}) {
  return (
    <div className='h-navBar flex w-full items-center justify-center'>
      <div
        className='m-4 h-6 w-6 cursor-pointer'
        onClick={
          canPop ? onClickPop
          : canClose ?
            onClickClose
          : null
        }
      >
        {canPop && !isCloseLeft ?
          <Icon name={'chevronLeft'} color={'var(--color-gray-100)'} />
        : null}
        {canClose && isCloseLeft ?
          <Icon name={'delete'} color={'var(--color-gray-100)'} />
        : null}
      </div>
      <div className='flex flex-1 items-center justify-center'>
        <p className='subtitle-2 text-gray-100'>{title}</p>
      </div>
      <div className='m-4 h-6 w-6 cursor-pointer' onClick={onClickClose}>
        {canClose && !isCloseLeft ?
          <Icon name={'delete'} color={'var(--color-gray-100)'} />
        : null}
      </div>
    </div>
  );
}
