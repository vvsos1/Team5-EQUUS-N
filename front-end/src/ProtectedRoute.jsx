// ProtectedRoute.jsx
import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';
import { useUserContext } from './UserContext';

function ProtectedRoute() {
  const { state, dispatch } = useUserContext();
  console.log(state);

  if (state.userId || state.userId == 'null') {
    return <Navigate to='/' replace />;
  }
  return <Outlet />;
}

export default ProtectedRoute;
