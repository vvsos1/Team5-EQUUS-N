import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import NavBar2 from '../../components/NavBar2';
import StickyWrapper from '../../components/wrappers/StickyWrapper';
import { DropdownSmall } from '../../components/Dropdown';
import Icon from '../../components/Icon';
import FeedBack, { FeedBackType } from '../feedback/components/FeedBack';
import { useGetSelfFeedback } from '../../api/useMyPage';
import { useUser } from '../../useUser';
import { useTeam } from '../../useTeam';

export default function SelfFeedback() {
  const navigate = useNavigate();

  const [feedbacks, setFeedbacks] = useState([]);
  const { teams } = useTeam();
  const [selectedTeam, setSelectedTeam] = useState('전체 보기');
  const [sortBy, setSortBy] = useState('createdAt:desc');
  const [loadedPage, setLoadedPage] = useState(0);
  const scrollRef = useRef(null);

  const { userId } = useUser();

  const {
    data: retrospect,
    isLoading,
    refetch,
  } = useGetSelfFeedback(userId, {
    teamId:
      selectedTeam === '전체 보기' ? null : (
        teams.find((t) => t.name === selectedTeam)?.id
      ),
    sortBy,
    page: loadedPage,
  });

  useEffect(() => {
    const container = scrollRef.current;
    function handleScroll() {
      if (
        container.scrollTop + container.clientHeight >=
          container.scrollHeight - 200 &&
        !isLoading
      ) {
        setLoadedPage(loadedPage + 1);
        refetch();
      }
    }
    container.addEventListener('scroll', handleScroll);
    return () => {
      container.removeEventListener('scroll', handleScroll);
    };
  }, [isLoading]);

  useEffect(() => {
    refetch();
  }, [loadedPage, refetch]);

  useEffect(() => {
    if (!retrospect) return;
    setFeedbacks((prev) => [...prev, ...(retrospect?.content ?? [])]);
  }, [retrospect]);

  useEffect(() => {
    const container = scrollRef.current;
    container.scrollTo(0, 0);
    setLoadedPage(0);
    setFeedbacks([]);
    refetch();
  }, [selectedTeam, sortBy]);

  console.log(feedbacks);

  return (
    <div
      className='scrollbar-hidden flex h-full flex-col overflow-x-hidden overflow-y-auto'
      ref={scrollRef}
    >
      <StickyWrapper>
        <NavBar2
          canPop={true}
          canClose={false}
          title='나의 회고'
          onClickPop={() => navigate(-1)}
        />
        <div className='flex justify-between gap-4 border-b border-gray-700 py-5'>
          <DropdownSmall
            triggerText={selectedTeam}
            setTriggerText={setSelectedTeam}
            items={teams.map((team) => team.name)}
          />
          <div className='button-2 flex items-center gap-2 text-gray-100'>
            <button
              className='flex items-center gap-1'
              onClick={() => {
                setSortBy(
                  sortBy === 'createdAt:desc' ? 'createdAt:asc' : (
                    'createdAt:desc'
                  ),
                );
                refetch();
              }}
            >
              <p>{sortBy === 'createdAt:desc' ? '최신순' : '과거순'}</p>
              <Icon name='swapVert' />
            </button>
          </div>
        </div>
      </StickyWrapper>
      {feedbacks.length > 0 ?
        <ul>
          {feedbacks.map((feedback, index) => {
            return (
              <li key={index}>
                <FeedBack feedbackType={FeedBackType.SELF} data={feedback} />
              </li>
            );
          })}
        </ul>
      : <div className='flex h-full flex-col items-center justify-center gap-4 text-gray-300'>
          <p className='text-5xl'>📭</p>
          <p>작성한 회고가 없어요</p>
        </div>
      }
    </div>
  );
}
