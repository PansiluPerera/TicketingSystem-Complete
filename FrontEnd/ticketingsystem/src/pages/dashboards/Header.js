import React from 'react';
import './Header.css'; 
import logo from './Ticketerlogo1.png'; 

function Header() {
  return (
    <div className="header-container">
      <div className="logo-container">
        <img src={logo} alt="Logo" className="logo" />
      </div>
      <div className="site-name">THE TICKETER™</div>
    </div>
  );
}

export default Header;
