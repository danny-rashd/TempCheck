import React from "react";
import { Navbar, Container, Nav, Button } from "react-bootstrap";

function Header() {
  return (
    <Navbar bg="primary" variant="dark" expand="lg">
      <Container fluid>
        <Navbar.Brand href="/">
          <img
            alt=""
            src="https://icons.getbootstrap.com/assets/icons/rainbow.svg"
            width="30"
            height="30"
            className="d-inline-block align-top"
          />{" "}
          TempCheck
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="navbarScroll" />
        <Navbar.Collapse id="navbarScroll">
          <Nav
            className="me-auto my-2 my-lg-0"
            style={{ maxHeight: "200px" }}
            navbarScroll
          >
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="/temptable">TempTable</Nav.Link>
            <Nav.Link href="/predict">Predictions</Nav.Link>
          </Nav>
        </Navbar.Collapse>
        <Nav>
          <Navbar.Brand href="/login">Login</Navbar.Brand>
          <Button href="/register" variant="dark">
            Register
          </Button>
        </Nav>
      </Container>
    </Navbar>
  );
}

export default Header;
