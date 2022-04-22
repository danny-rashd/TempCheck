import React from "react";
import { useNavigate, useLocation, Navigate } from "react-router-dom";
import { useAuth } from "../components/auth";

export const RequireAuth = ({ children }) => {
  const auth = useAuth();
  const location = useLocation();
  if (!auth.user) {
    return <Navigate to="/" state={{ path: location.pathname }} />;
  }
  return children;
};
