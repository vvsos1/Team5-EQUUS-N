import NavBar2 from './components/NavBar2';

function App() {
  return (
    <div className='mx-5 h-full w-2xl bg-gray-900'>
      <div className='flex h-full flex-col items-center justify-center gap-4 overflow-auto'>
        <NavBar2
          title='피드백 주고받기'
          canPop={true}
          canClose={true}
          isCloseLeft={false}
        />
      </div>
    </div>
  );
}

export default App;
