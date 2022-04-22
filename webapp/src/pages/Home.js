import { Container, Card, Form, Button } from "react-bootstrap";
import "./home.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignInAlt,
  faEnvelope,
  faLock,
} from "@fortawesome/free-solid-svg-icons";
import styled from "styled-components";

const BgImageOverlay = styled.h1`
  font-size: 1.5em;
  text-align: center;
  color: palevioletred;
`;

export const Home = () => {
  return (
    <Container id="main-container" className="d-grid h-50">
      <Form id="sign-in-form" className="text-center p-2 w-100"></Form>
      <Card className="text-center">
        <Card.Header>Home</Card.Header>
        <Card.Body>
          <Card.Title>TempCheck</Card.Title>
          <Card.Text>
            Web application to visualize and predict temperature data
          </Card.Text>
          <Button href="login" variant="primary">
            Login
          </Button>
          <Button href="register" variant="secondary">
            Register
          </Button>
        </Card.Body>
        <Card.Footer className="text-muted">2 days ago</Card.Footer>
      </Card>
    </Container>
    //     <div class="login">
    //       <h1>Login</h1>
    //       <form method="post">
    //         <input
    //           type="text"
    //           name="u"
    //           placeholder="Username"
    //           required="required"
    //         />
    //         <input
    //           type="password"
    //           name="p"
    //           placeholder="Password"
    //           required="required"
    //         />
    //         <button type="submit" class="btn btn-primary btn-block btn-large">
    //           Let me in.
    //         </button>
    //       </form>
    //     </div>
    //  <div class="login">
    // 	<h1>Login</h1>
    //     <form method="post">
    //     	<input type="text" name="u" placeholder="Username" required="required" />
    //         <input type="password" name="p" placeholder="Password" required="required" />
    //         <button type="submit" onClick={onH} class="btn btn-primary btn-block btn-large">Login</button>
    //     </form>
    // </div>
  );
};
