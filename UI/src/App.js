import './App.css';
import DefaultLayout from './Layout/DefaultLayout';
import Login from './pages/Login'
import routes from './config/routes';
import Home from './pages/Home';
import { BrowserRouter, Route, Routes, redirect } from 'react-router-dom';
import Locker from './pages/Locker';
import Invoice from './pages/Invoice';
import Payment from './pages/Payment';
import Surveys from './pages/Surveys';
import Survey from './pages/Survey';
import Feedbacks from './pages/Feedbacks';
import Personal from './pages/Personal';

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path={routes.home} element={<Home />} />
        <Route path={routes.locker} element={<DefaultLayout children={<Locker />} />} />
        <Route path={routes.invoice} element={<DefaultLayout children={<Invoice />} />} />
        <Route path={routes.payment} element={<DefaultLayout children={<Payment />} />} />
        <Route path={routes.surveys} element={<DefaultLayout children={<Surveys />} />} />
        <Route path={routes.survey} element={<DefaultLayout children={<Survey />} />} />
        <Route path={routes.feedback} element={<DefaultLayout children={<Feedbacks />} />} />
        <Route path={routes.personal} element={<Personal />} />
        <Route path={routes.login} element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
