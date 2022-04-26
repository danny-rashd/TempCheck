import axios from "axios";

export const userLogin = (authRequest) => {
  return axios({
    method: "POST",
    url: `${process.env.hostUrl || "http://localhost:8080"}/api/v1/login`,
    data: authRequest,
  });
};

export const userRegister = (AuthUserModel) => {
  return axios({
    method: "POST",
    url: `${process.env.hostUrl || "http://localhost:8080"}/api/v1/register`,
    data: AuthUserModel,
  });
};
