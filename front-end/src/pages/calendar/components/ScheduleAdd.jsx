import classNames from 'classnames';

export default function ScheduleAdd({ isOpen, onClose, onSubmit }) {
  return (
    <div
      className={classNames(
        'rounded-t-400 fixed right-0 left-0 z-1000 h-[calc(100%-60px)] bg-gray-800 transition-transform duration-300',
        isOpen ? 'visible bottom-0' : 'invisible -bottom-[100%]',
      )}
    ></div>
  );
}
