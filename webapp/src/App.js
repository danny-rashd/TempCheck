import "./App.css";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Login from "./pages/Login";
import { Dashboard } from "./pages/response/dashboard";
import Register from "./pages/Register";
import { RegisterSuccess } from "./pages/response/RegisterSuccess";
import Header from "./components/Header";
import Footer from "./components/Footer";
import { Upload } from "./pages/Upload";
import { Home } from "./pages/Home";
import { DataTable } from "./pages/DataTable";

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Switch>
        <Route exact path="/" component={Home} />
        <Route exact path="/register" component={Register} />
        <Route exact path="/login" component={Login} />
        <Route exact path="/dashboard" component={Dashboard} />
        <Route exact path="/success" component={RegisterSuccess} />
        <Route exact path="/upload" component={Upload} />
        <Route exact path="/table" component={DataTable} />
      </Switch>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
