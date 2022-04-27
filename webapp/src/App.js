import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Login from "./pages/Login";
import { Dashboard } from "./pages/response/Dashboard";
import Register from "./pages/Register";
import { RegisterSuccess } from "./pages/response/RegisterSuccess";
import Header from "./components/Header";
import Footer from "./components/Footer";
import { TempTable } from "./pages/TempTable";
import { Home } from "./pages/Home";
import ForgotPassword from "./pages/response/ForgotPassword";
import Predictions from "./pages/Predictions";
function App() {
  return (
    <Router>
      <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/register" component={Register} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/dashboard" component={Dashboard} />
        <Route exact path="/success" component={RegisterSuccess} />
        <Route exact path="/temptable" component={TempTable} />
        <Route exact path="/forgot" component={ForgotPassword} />
        <Route exact path="/predict" component={Predictions} />
      </Switch>
      <Footer />
    </Router>
  );
}

export default App;
