import { useState } from "react";
import { connect } from "react-redux";
import { authenticate, authFailure, authSuccess } from "../redux/authActions";
import "./home.css";
import { userLogin } from "../api/authenticationService";
import { Alert, Spinner, Form, Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSignInAlt,
  faEnvelope,
  faLock,
} from "@fortawesome/free-solid-svg-icons";

const Login = ({ loading, error, ...props }) => {
  const [values, setValues] = useState({
    username: "",
    password: "",
  });

  const handleSubmit = (evt) => {
    evt.preventDefault();
    props.authenticate();

    userLogin(values)
      .then((response) => {
        if (response.status === 200) {
          props.setUser(response.data);
          props.history.push("/dashboard");
        } else {
          props.loginFailure("Error !Please Try Again");
        }
      })
      .catch((err) => {
        if (err && err.response) {
          switch (err.response.status) {
            case 401:
              props.loginFailure(
                "Please activate the account by clicking the confirmation link sent to your email"
              );
              break;
            default:
              props.loginFailure("Error! Please Try Again");
          }
        } else {
          props.loginFailure("Error! Please Try Again");
        }
      });
  };

  const handleChange = (e) => {
    e.persist();
    setValues((values) => ({
      ...values,
      [e.target.name]: e.target.value,
    }));
  };


  return (
    <div className="container-fluid ps-md-0">
      <div className="row g-0">
        <div className="d-none d-md-flex col-md-4 col-lg-6 bg-image">
          <div className="overlay-container">
            <div className="overlay">
              <div className="overlay-panel overlay-left">
                <h1>First time?</h1>
                <p></p>
                <h4>Sign Up and explore this web app</h4>
                <Button
                  className="btn btn-lg btn-dark btn-login fw-bold ghost zmb-2"
                  id="signUp"
                  href="/register"
                >
                  Register Now
                </Button>
              </div>
            </div>
          </div>
        </div>
        <div className="col-md-8 col-lg-6">
          <div className="login d-flex align-items-center py-5">
            <div className="container">
              <div className="row">
                <div className="col-md-9 col-lg-8 mx-auto">
                  <h3 className="login-heading mb-2">Welcome back!</h3>
                  <img
                    className="mb-2 bootstrap-logo"
                    src="https://icons.getbootstrap.com/assets/icons/rainbow.svg"
                    alt="Bootstrap 5"
                  />
                  <Form
                    id="sign-in-form"
                    onSubmit={handleSubmit}
                    noValidate={false}
                  >
                    <div className="form-floating mb-2">
                      <div className="input-group mb-2">
                        <div className="input-group-prepend">
                          <span className="input-group-text" id="basic-addon1">
                            <FontAwesomeIcon icon={faEnvelope} />
                          </span>
                        </div>
                        <input
                          id="username"
                          name="username"
                          type="email"
                          placeholder="Email address"
                          className="form-control"
                          minLength={10}
                          value={values.username}
                          onChange={handleChange}
                          autoComplete="off"
                          required
                        />
                      </div>
                    </div>

                    <div className="form-floating mb-2">
                      <div className="input-group mb-3">
                        <div className="input-group-prepend">
                          <span className="input-group-text" id="basic-addon1">
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
                    <div className="d-grid">
                      <Button
                        variant="primary"
                        type="submit"
                        size="lg"
                        className="btn-login fw-bold mb-2"
                      >
                        Login
                        <FontAwesomeIcon icon={faSignInAlt} className="mr-3" />
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
                    <div>
                      <a className="medium" href="/forgot">
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
      </div>
    </div>
  );
};

const mapStateToProps = ({ auth }) => {
  return {
    loading: auth.loading,
    error: auth.error,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    authenticate: () => dispatch(authenticate()),
    setUser: (data) => dispatch(authSuccess(data)),
    loginFailure: (message) => dispatch(authFailure(message)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
