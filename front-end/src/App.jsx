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
import FeedbackReceived from './pages/feedback/FeedbackReceived';

const queryClient = new QueryClient();

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <BrowserRouter>
        <Routes>
          <Route element={<Layout />}>
            <Route path='/' element={<Splash />} />
            <Route path='feedback'>
              <Route path='request' element={<FeedbackRequest />} />
              <Route path='received' element={<FeedbackReceived />} />
            </Route>
            <Route path='signin' element={<SignIn />} />
            <Route path='signup' element={<SignUp />} />
            <Route path='teamspace/make' element={<TeamSpaceMake />} />
            <Route
              path='teamspace/make/first'
              element={<TeamSpaceMake isFirst={true} />}
            />
            <Route
              path='teamspace/make/success'
              element={<TeamSpaceMakeSuccess />}
            />
            <Route path='calendar' element={<Calendar />} />
            <Route path='main'>
              <Route index element={<MainPage />} />
              <Route path='notification' element={<div></div>} />
            </Route>
          </Route>
        </Routes>
      </BrowserRouter>
    </QueryClientProvider>
  );
}
