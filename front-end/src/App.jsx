import React from 'react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter, Route, Routes } from 'react-router';
import 'react-datepicker/dist/react-datepicker.css';
import './index.css';
import './customDatePicker.css';
import Layout from './Layout';
import FeedbackRequest from './pages/feedback/FeedbackRequest';
import Splash from './pages/auth/Splash';
import SignIn from './pages/auth/SignIn';
import SignUp from './pages/auth/SignUp';
import TeamSpaceMake from './pages/teamspace/TeamSpaceMake';
import TeamSpaceMakeSuccess from './pages/teamspace/TeamSpaceMakeSuccess';
import Calendar from './pages/calendar/Calendar';
import MainPage from './pages/main/MainPage';
import TeamSpaceList from './pages/teamspace/TeamSpaceList';
import NotificationPage from './pages/main/NotificationPage';
import FeedbackReceived from './pages/feedback/FeedbackReceived';
import FeedbackSent from './pages/feedback/FeedbackSent';
import TeamSpaceManage from './pages/teamspace/TeamSpaceManage';
import TeamSpaceEdit from './pages/teamspace/TeamSpaceEdit';
import FeedbackComplete from './pages/feedback/FeedbackComplete';
import FeedbackSelf from './pages/feedback/FeedbackSelf';
import SelfFeedback from './pages/mypage/SelfFeedback';
import CombinedProvider from './CombinedProvider';
import { TeamProvider } from './TeamContext';
import FeedbackFavorite from './pages/feedback/FeedbackFavorite';
import MyPageHome from './pages/mypage/MyPageHome';

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <CombinedProvider>
          <Routes>
            <Route element={<Layout />}>
              <Route path='/' element={<Splash />} />
              <Route path='feedback'>
                <Route path='request' element={<FeedbackRequest />} />
                <Route path='self' element={<FeedbackSelf />} />
                <Route path='complete' element={<FeedbackComplete />} />
                <Route path='favorite' element={<FeedbackFavorite />} />
                <Route path='received/:userId' element={<FeedbackReceived />} />
                <Route path='sent/:userId' element={<FeedbackSent />} />
              </Route>
              <Route path='signin' element={<SignIn />} />
              <Route path='signup' element={<SignUp />} />
              <Route path='teamspace'>
                <Route path='make'>
                  <Route index element={<TeamSpaceMake />} />
                  <Route
                    path='first'
                    element={<TeamSpaceMake isFirst={true} />}
                  />
                  <Route path='success' element={<TeamSpaceMakeSuccess />} />
                </Route>
                <Route path='list' element={<TeamSpaceList />} />
                <Route path='manage/:teamId'>
                  <Route index element={<TeamSpaceManage />} />
                  <Route path='edit' element={<TeamSpaceEdit />} />
                </Route>
              </Route>
              <Route path='calendar' element={<Calendar />} />
              <Route path='main'>
                <Route index element={<MainPage />} />
                <Route path='notification' element={<NotificationPage />} />
              </Route>
              <Route path='mypage'>
                <Route index element={<MyPageHome />} />
                <Route path='self/:userId' element={<SelfFeedback />} />
                <Route path='report' element={<div></div>} />
              </Route>
            </Route>
          </Routes>
        </CombinedProvider>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
