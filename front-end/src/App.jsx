import Icon from "./components/Icon";

function App() {
  return (
    <>
      <div className=' mx-5'>
      {/* IntelliSense for CSS class names in HTML 설치 필요 */}
      <div className=' bg-gray-600 grid grid-cols-10 gap-2'>
        <Icon name='heartFill' className='w-12 h-12' />
        <Icon name='heartDefault' className='w-12 h-12' />
        <Icon name='bellOff' className='w-12 h-12' />
        <Icon name='bellOn' className='w-12 h-12' />
        <Icon name='calendar' className='w-12 h-12' />
        <Icon name='check' className='w-12 h-12' />
        <Icon name='checkBoxNone' className='w-12 h-12' />
        <Icon name='checkBoxClick' className='w-12 h-12' />
        <Icon name='chevronDown' className='w-12 h-12' />
        <Icon name='chevronLeft' className='w-12 h-12' />
        <Icon name='chevronRight' className='w-12 h-12' />
        <Icon name='chevronUp' className='w-12 h-12' />
        <Icon name='crown' className='w-12 h-12' />
        <Icon name='delete' className='w-12 h-12' />
        <Icon name='deleteSmall' className='w-12 h-12' />
        <Icon name='dots' className='w-12 h-12' />
        <Icon name='edit' className='w-12 h-12' />
        <Icon name='eye' className='w-12 h-12' />
        <Icon name='hamburger' className='w-12 h-12' />
        <Icon name='logout' className='w-12 h-12' />
        <Icon name='mail' className='w-12 h-12' />
      </div>
      </div>
    </>
  );
}

export default App;
