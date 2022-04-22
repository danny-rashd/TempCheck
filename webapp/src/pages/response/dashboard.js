import React, { useState } from "react";
import { Button, Container } from "react-bootstrap";
import { useDispatch } from "react-redux";
import styled from "styled-components";
import { fetchUserData } from "../../api/authenticationService";

const MainWrapper = styled.div`
  padding-top: 40px;
`;

export const Dashboard = (props) => {
  const dispatch = useDispatch();
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState({});

  const logOut = () => {
    localStorage.clear();
    props.history.push("/");
  };

  return (
    <Container>
      <MainWrapper>
        <h4>Welcome User! </h4>
        <br></br>
        <br></br>

        <Button style={{ marginTop: "5px" }} onClick={() => logOut()}>
          Logout
        </Button>
      </MainWrapper>
    </Container>
  );
};
