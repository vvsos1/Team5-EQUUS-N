import classNames from 'classnames';
import Icon from '../Icon';

export default function SmallButton({
  text,
  disabled,
  onClick,
  isOutlined = true,
}) {
  return (
    <button
      disabled={disabled}
      className={classNames(
        'rounded-300 flex h-[46px] w-[115px] items-center justify-center px-4 py-2 text-gray-300',
        isOutlined ?
          disabled ? 'border border-gray-500 text-gray-500'
          : 'cursor-pointer border border-lime-500 text-lime-500'
        : disabled ? 'bg-gray-800 text-gray-300'
        : 'cursor-pointer bg-lime-500 text-gray-900',
      )}
      onClick={onClick}
    >
      {text}
    </button>
  );
}
