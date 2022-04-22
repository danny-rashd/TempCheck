import react, { useState } from "react";
import { connect } from "react-redux";
import { authenticate, authFailure, authSuccess } from "../redux/authActions";
import "./home.css";
import { userRegister } from "../api/authenticationService";
import {
  Alert,
  Spinner,
  Container,
  Card,
  Form,
  InputGroup,
  Button,
} from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faEnvelope,
  faLock,
  faUserPlus,
  faIdCard,
  faIdBadge,
} from "@fortawesome/free-solid-svg-icons";

const Register = ({ loading, error, ...props }) => {
  const [values, setValues] = useState({
    email: "",
    firstName: "",
    lastName: "",
    password: "",
  });

  const handleSubmit = (evt) => {
    evt.preventDefault();
    props.authenticate();

    userRegister(values)
      .then((response) => {
        console.log("response", response);
        if (response.status === 200) {
          props.setUser(response.data);
          props.history.push("/success");
        } else {
          props.registerFailure("Success issue?");
        }
      })
      .catch((err) => {
        if (err && err.response) {
          switch (err.response.status) {
            case 401:
              console.log("401 status");
              props.registerFailure("Authentication Failed.Bad Credentials");
              break;
            case 500:
              console.log("500 status");
              props.registerFailure("This email address has been taken!");
              break;
            default:
              props.registerFailure("Login Error!");
          }
        } else {
          props.registerFailure("Authentication Error!");
        }
      });
    //console.log("Loading again",loading);
  };

  const handleChange = (e) => {
    e.persist();
    setValues((values) => ({
      ...values,
      [e.target.name]: e.target.value,
    }));
  };

  console.log("Loading ", loading);

  return (
    <div className="container-fluid ps-md-0">
      <div class="row g-0">
        <div class="col-md-8 col-lg-6">
          <div class="login d-flex align-items-center py-5">
            <div class="container">
              <div class="row">
                <div class="col-md-9 col-lg-8 mx-auto">
                  <h2 class="login-heading mb-2">Create an account</h2>
                  <img
                    className="mb-2 bootstrap-logo"
                    src="https://icons.getbootstrap.com/assets/icons/rainbow.svg"
                    alt="Bootstrap 5"
                  />
                  <Form
                    id="sign-up-form"
                    onSubmit={handleSubmit}
                    noValidate={false}
                  >
                    <div class="form-floating mb-1">
                      <div class="input-group mb-1">
                        <div class="input-group-prepend">
                          <span class="input-group-text" id="basic-addon1">
                            <FontAwesomeIcon icon={faEnvelope} />
                          </span>
                        </div>
                        <input
                          id="email"
                          name="email"
                          type="email"
                          placeholder="Email"
                          className="form-control"
                          minLength={10}
                          value={values.email}
                          onChange={handleChange}
                          autoComplete="off"
                          auto
                          required
                        />
                      </div>
                    </div>

                    <div class="form-floating mb-1">
                      <div class="input-group mb-1">
                        <div class="input-group-prepend">
                          <span class="input-group-text" id="basic-addon1">
                            <FontAwesomeIcon icon={faIdBadge} />
                          </span>
                        </div>
                        <input
                          type="text"
                          id="firstName"
                          className="form-control"
                          minLength={4}
                          value={values.firstName}
                          onChange={handleChange}
                          name="firstName"
                          placeholder="First Name"
                          required
                        />
                      </div>
                    </div>
                    <div class="form-floating mb-1">
                      <div class="input-group mb-1">
                        <div class="input-group-prepend">
                          <span class="input-group-text" id="basic-addon1">
                            <FontAwesomeIcon icon={faIdBadge} />
                          </span>
                        </div>
                        <input
                          type="text"
                          id="lastName"
                          className="form-control"
                          minLength={4}
                          value={values.lastName}
                          onChange={handleChange}
                          name="lastName"
                          placeholder="Last Name"
                          required
                        />
                      </div>
                    </div>
                    <div class="form-floating mb-3">
                      <div class="input-group mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text" id="basic-addon1">
                            <FontAwesomeIcon icon={faLock} />
                          </span>
                        </div>
                        <input
                          type="password"
                          id="password"
                          className="form-control"
                          minLength={8}
                          value={values.password}
                          onChange={handleChange}
                          name="password"
                          placeholder="Password"
                          required
                        />
                      </div>
                    </div>

                    <div class="d-grid">
                      <Button
                        variant="primary"
                        type="submit"
                        size="lg"
                        className="btn-login fw-bold mb-2"
                      >
                        Register
                        <FontAwesomeIcon icon={faUserPlus} className="mr-3" />
                        {loading && (
                          <Spinner
                            as="span"
                            animation="border"
                            size="sm"
                            role="status"
                            aria-hidden="true"
                          />
                        )}
                      </Button>
                    </div>
                    <div class="text-center">
                      <a class="medium" href="#">
                        Forgot password?
                      </a>
                    </div>
                  </Form>
                  {error && (
                    <Alert style={{ marginTop: "20px" }} variant="danger">
                      <Alert.Heading>
                        Authentication Failed [Bad Credentials]
                      </Alert.Heading>
                      {error}
                    </Alert>
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="d-none d-md-flex col-md-4 col-lg-6 bg-image">
          <div class="overlay-container">
            <div class="overlay">
              <div class="overlay-panel overlay-left">
                <h3>Already have an account?</h3>
                <button
                  class="btn btn-lg btn-dark btn-login fw-bold ghost zmb-2"
                  id="signIn"
                >
                  Login
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

const mapStateToProps = ({ auth }) => {
  console.log("state ", auth);
  return {
    loading: auth.loading,
    error: auth.error,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    authenticate: () => dispatch(authenticate()),
    setUser: (data) => dispatch(authSuccess(data)),
    registerFailure: (message) => dispatch(authFailure(message)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Register);
