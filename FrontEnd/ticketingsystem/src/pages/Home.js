import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';  
import logo from './Ticketerlogo.png'; 

function Home() {
  return (
    <div className="home-container">
      <div className="logo-container">
        <img src={logo} alt="Ticketer Logo" className="logo" />
      </div>

      <div className="welcome">
        <h1>Welcome to The Ticketer&trade;</h1>
        <p className="description">
          A real-time ticket management system by Pansilu Perera
        </p>
      </div>

      <div className="options-container">
        <div className="card">
          <h2>Login</h2>
          <div className="login-register">
            <Link to="/login/admin" className="button">Login as Admin</Link>
            <Link to="/login/customer" className="button">Login as Customer</Link>
            <Link to="/login/vendor" className="button">Login as Vendor</Link>
          </div>
        </div>

        <div className="card">
          <h2>Register</h2>
          <div className="login-register">
            <Link to="/register/customer" className="button">Register as Customer</Link>
            <Link to="/register/vendor" className="button">Register as Vendor</Link>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Home;
