import Icon from './components/Icon';

function App() {
  return (
    <>
      <div className='mx-5' style={{ cursor: 'pointer' }}>
        {/* IntelliSense for CSS class names in HTML 설치 필요 */}
        <div className='grid grid-cols-10 gap-2 bg-gray-600'>
          <Icon name='heartFill' className='h-12 w-12' />
          <Icon name='heartDefault' className='h-12 w-12' />
          <Icon name='bellOff' className='h-12 w-12' />
          <Icon name='bellOn' className='body-2 h-12 w-12' />
          <Icon name='calendar' className='h-12 w-12' />
          <Icon name='check' className='h-12 w-12' />
          <Icon name='checkBoxNone' className='h-12 w-12' />
          <Icon name='checkBoxClick' className='h-12 w-12' />
          <Icon name='chevronDown' className='h-12 w-12' />
          <Icon name='chevronLeft' className='h-12 w-12' />
          <Icon name='chevronRight' className='h-12 w-12' />
          <Icon name='chevronUp' className='h-12 w-12' />
          <Icon name='crown' className='h-12 w-12' />
          <Icon name='delete' className='h-12 w-12' />
          <Icon name='deleteSmall' className='body-1 h-12 w-12' />
          <Icon name='dots' className='h-12 w-12' />
          <Icon name='edit' className='h-12 w-12' />
          <Icon name='eye' className='h-12 w-12' />
          <Icon name='hamburger' className='h-12 w-12' />
          <Icon name='logout' className='h-12 w-12' />
          <Icon name='mail' className='h-12 w-12' />
        </div>
      </div>
    </>
  );
}

export default App;
