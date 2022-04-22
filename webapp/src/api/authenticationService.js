import React from "react";
import axios from "axios";

const getToken = () => {
  return localStorage.getItem("USER_KEY");
};

export const userLogin = (authRequest) => {
  return axios({
    method: "POST",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/login`,
    data: authRequest,
  });
};

export const userRegister = (AuthUserModel) => {
  return axios({
    method: "POST",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/register`,
    data: AuthUserModel,
  });
};

export const fetchUserData = () => {
  return axios({
    method: "GET",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/users`,
    headers: {
      Authorization: "Bearer " + getToken(),
    },
  });
};

export const resendEmail = () => {
  return axios({
    method: "GET",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/resendToken`,
    headers: {
      Authorization: "Bearer " + getToken(),
    },
  });
};

export const uploadFile = (file) => {
  return axios({
    method: "POST",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/upload`,
    data: file,
  });
};

export const downloadFile = (file) => {
  return axios({
    method: "GET",
    url: `${
      process.env.hostUrl || "http://localhost:8080"
    }/api/v1/springboot/download/`,
    data: file,
  });
};
