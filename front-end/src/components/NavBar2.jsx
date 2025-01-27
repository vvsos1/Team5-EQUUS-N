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
    <div className='flex justify-center items-center w-full h-navBar'>
      {canPop && !isCloseLeft ? (
        <div className='w-6 h-6 m-4 cursor-pointer' onClick={onClickPop}>
          <Icon name={'chevronLeft'} color={'var(--color-gray-100)'} />
        </div>
      ) : null}
      {canClose && isCloseLeft ? (
        <div className='w-6 h-6 m-4 cursor-pointer' onClick={onClickClose}>
          <Icon name={'delete'} color={'var(--color-gray-100)'} />
        </div>
      ) : null}
      <div className='flex flex-1 justify-center items-center'>
        <p className='subtitle-2 text-gray-100'>{title}</p>
      </div>
      {canClose && !isCloseLeft ? (
        <div className='w-6 h-6 m-4 cursor-pointer' onClick={onClickClose}>
          <Icon name={'delete'} color={'var(--color-gray-100)'} />
        </div>
      ) : null}
    </div>
  );
}
