import React from 'react';
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

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route element={<Layout />}>
          <Route path='/' element={<Splash />} />
          <Route
            path='home'
            element={<div className='size-full bg-cyan-100' />}
          />
          <Route path='feedback'>
            <Route path='request' element={<FeedbackRequest />} />
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
        </Route>
      </Routes>
    </BrowserRouter>
  );
}
