import React, { useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import './Register.css'; 
import logo from './Ticketerlogo.png'; 

function Register() {
  const { role } = useParams(); 
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [vendorName, setVendorName] = useState('');
  const [vendorUserName, setVendorUserName] = useState(''); 
  const [error, setError] = useState(null);

  const handleRegister = async (e) => {
    e.preventDefault();
    let registerEndpoint = '';
    const formData = new FormData();

    // Set the endpoint and parameters based on the role
    if (role === 'customer') {
      registerEndpoint = 'http://localhost:8080/auth/register/customer';
      formData.append('username', username);
      formData.append('password', password);
    } else if (role === 'vendor') {
      registerEndpoint = 'http://localhost:8080/auth/register/vendor';
      formData.append('vendorName', vendorName);
      formData.append('vendorUserName', vendorUserName);
      formData.append('vendorPassword', password);
    }

    try {
      const response = await axios.post(registerEndpoint, formData, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
      });
      if (response.status === 200) {
        navigate(`/login/${role}`);
      }
    } catch (error) {
      setError("Registration failed. Please try again.");
    }
  };

  return (
    <div className="register-container">
      <div className="logo-container">
        <img src={logo} alt="Logo" className="logo" />
      </div>
      <h2>Register as {role.charAt(0).toUpperCase() + role.slice(1)}</h2>
      <form onSubmit={handleRegister} className="register-form">
        {role === 'vendor' && (
          <>
            <input
              type="text"
              placeholder="Vendor Name"
              value={vendorName}
              onChange={(e) => setVendorName(e.target.value)}
              className="input-field"
            />
            <input
              type="text"
              placeholder="Vendor Username"
              value={vendorUserName}
              onChange={(e) => setVendorUserName(e.target.value)}
              className="input-field"
            />
          </>
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
        <button type="submit" className="submit-btn">Register</button>
      </form>
      {error && <div className="error-message">{error}</div>}
    </div>
  );
}

export default Register;
