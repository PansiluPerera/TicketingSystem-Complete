import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './Login.css'; 
import logo from './Ticketerlogo.png'; 

function Login() {
  const { role } = useParams();  
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [vendorName, setVendorName] = useState('');
  const [error, setError] = useState(null);

  const handleLogin = async (e) => {
    e.preventDefault();
    let loginEndpoint = '';
    const formData = new FormData();

    // Set the endpoint and parameters based on the role
    if (role === 'admin') {
      loginEndpoint = 'http://localhost:8080/auth/login/admin';
      formData.append('adminUserName', username);
      formData.append('adminPassword', password);
    } else if (role === 'customer') {
      loginEndpoint = 'http://localhost:8080/auth/login/customer';
      formData.append('username', username);
      formData.append('password', password);
    } else if (role === 'vendor') {
      loginEndpoint = 'http://localhost:8080/auth/login/vendor';
      formData.append('vendorUserName', username);
      formData.append('vendorPassword', password);
    }

    try {
      const response = await axios.post(loginEndpoint, formData, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
      if (response.status === 200) {
        navigate(`${role}-dashboard`);
      }
    } catch (error) {
      setError("Login failed. Please check your credentials.");
    }
  };

  return (
    <div className="login-container">
      <div className="logo-container">
        <img src={logo} alt="Logo" className="logo" />
      </div>
      <h2>Login as {role.charAt(0).toUpperCase() + role.slice(1)}</h2>
      <form onSubmit={handleLogin} className="login-form">
        {role === 'vendor' && (
          <input
            type="text"
            placeholder="Vendor Name"
            value={vendorName}
            onChange={(e) => setVendorName(e.target.value)}
            className="input-field"
          />
        )}
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="input-field"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="input-field"
        />
        <button type="submit" className="submit-btn">Login</button>
      </form>
      {error && <div className="error-message">{error}</div>}
    </div>
  );
}

export default Login;
