import React, { useState } from "react";
import { Button, Container } from "react-bootstrap";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { fetchUserData } from "../../api/authenticationService";

const MainWrapper = styled.div`
  padding-top: 40px;
`;

export const RegisterSuccess = (props) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState({});

  const loginRedirect = () => {
    localStorage.clear();
    props.history.push("/login");
  };

  return (
    <Container>
      <MainWrapper>
        <h4>Registration Successful!</h4>
        <h4>
          To activate your account, please click the confirmation link sent to
          your email{" "}
        </h4>
        <br></br>
        <br></br>

        <Button style={{ marginTop: "5px" }} onClick={() => loginRedirect()}>
          Go to Login page
        </Button>
      </MainWrapper>
    </Container>
  );
};
