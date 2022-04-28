
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
import ChartData from "./pages/ChartData";
import { Error } from "./pages/Error";
import "./App.css";
function App() {
  return (
    <Router>
      <Header />
      <Switch>
        <Route exact path="/" component={(props) => <Home {...props} />} />
        <Route path="/register" component={Register} />
        <Route path="/login" component={Login} />
        <Route path="/dashboard" component={Dashboard} />
        <Route path="/success" component={RegisterSuccess} />
        <Route path="/temptable" component={TempTable} />
        <Route path="/forgot" component={ForgotPassword} />
        <Route path="/predict" component={Predictions} />
        <Route path="/chart" component={ChartData} />
        <Route path="*" component={Error} />
      </Switch>
      <Footer />
    </Router>
  );
}

export default App;
