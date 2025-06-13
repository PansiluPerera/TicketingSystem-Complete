import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import './App.css';
import VendorDashboard from './pages/dashboards/vendor-dashboard';
import CustomerDashboard from './pages/dashboards/customer-dashboard';
import AdminDashboard from './pages/dashboards/admin-dashboard';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login/:role" element={<Login />} />
          <Route path="/register/:role" element={<Register />} />
          <Route path="/login/vendor/vendor-dashboard" element={<VendorDashboard />} />
          <Route path="/login/customer/customer-dashboard" element={<CustomerDashboard />} />
          <Route path="/login/admin/admin-dashboard" element={<AdminDashboard />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
