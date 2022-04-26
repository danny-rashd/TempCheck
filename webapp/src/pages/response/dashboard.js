import React from "react";
import { Button, Card } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faDoorOpen } from "@fortawesome/free-solid-svg-icons";
import "../home.css";

export const Dashboard = (props) => {
  const logOut = () => {
    localStorage.clear();
    props.history.push("/");
  };

  const goToTable = () => {
    localStorage.clear();
    props.history.push("/temptable");
  };

  return (
    <div className="centered">
        <Card className="text-center">
          <Card.Header>
            <FontAwesomeIcon icon={faDoorOpen} size="2x" />
          </Card.Header>
          <Card.Body>
            <Card.Title>Welcome User!</Card.Title>
            <Card.Text>
              Explore the page to find out more about the web application
            </Card.Text>
            <Button className="btn btn-primary " onClick={() => goToTable()}>
              Go To Table page
            </Button>
            <Button
              className="btn btn-secondary ml-5 "
              onClick={() => logOut()}
            >
              Logout
            </Button>
          </Card.Body>
        </Card>
    </div>
  );
};
