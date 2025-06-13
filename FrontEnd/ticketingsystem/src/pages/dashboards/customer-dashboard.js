import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './customer-dashboard.css';
import Header from './Header'; 

function CustomerDashboard() {
  const [username, setUsername] = useState('');
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [events, setEvents] = useState([]);
  const [selectedEvents, setSelectedEvents] = useState([]);
  const [purchasedTickets, setPurchasedTickets] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {

    axios
      .get('http://localhost:8080/api/event/all')
      .then((response) => setEvents(response.data))
      .catch((err) => setError('Failed to load events'));

    if (selectedUser) {

      axios
        .get(`http://localhost:8080/api/user/${selectedUser}/tickets`)
        .then((response) => setPurchasedTickets(response.data))
        .catch((err) => setError('Failed to fetch tickets'));
    }
  }, [selectedUser]);

  const handleUsernameSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get(
        `http://localhost:8080/api/user/find?username=${username}`
      );
      setUsers(response.data);
    } catch (err) {
      setError('Failed to load users.');
    }
  };

  const handleEventSelection = (eventId) => {
    setSelectedEvents((prevState) =>
      prevState.includes(eventId)
        ? prevState.filter((id) => id !== eventId)
        : [...prevState, eventId]
    );
  };

  const handleTicketPurchase = async () => {
    if (!selectedUser || selectedEvents.length === 0) {
      alert('Please select a user and events to purchase tickets.');
      return;
    }

    try {
      for (let eventId of selectedEvents) {
        await axios.post(
          `http://localhost:8080/api/user/select-event/${selectedUser}/${eventId}`
        );
      }
      alert('You are now on the waiting list for the selected events.');
      setSelectedEvents([]);
    } catch (error) {
      setError('Failed to purchase tickets.');
    }
  };

  const handleRefreshTickets = async () => {
    if (selectedUser) {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/user/${selectedUser}/tickets`
        );
        setPurchasedTickets(response.data);
      } catch (error) {
        setError('Failed to fetch tickets.');
      }
    }
  };

  const handleUserSelection = (userId) => {
    setSelectedUser(userId);
  };

  const handleUsernameChange = (e) => {
    setUsername(e.target.value);
  };

  const getUserRowClass = (userId) => {
    return selectedUser === userId ? 'selected' : '';
  };

  const getEventRowClass = (eventId) => {
    return selectedEvents.includes(eventId) ? 'selected' : '';
  };

  return (
    <div className="customer-dashboard">

      <Header />

      <br></br>

      <h2>Customer Dashboard</h2>

      {error && <div className="error-message">{error}</div>}

      {/* Username Form */}
      <div className="username-form">
        <form onSubmit={handleUsernameSubmit}>
          <label htmlFor="username"><h3><b>Search Customer</b></h3></label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={handleUsernameChange}
            required
          />
          <button type="submit">Search</button>
        </form>
      </div>

      <br></br>

      {/* Users Table */}
      {users.length > 0 && (
        <div className="user-selection">
          <h3><b>Select Customer</b></h3>
          <table>
            <thead>
              <tr>
                <th>Customer ID</th>
                <th>Customer Name</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {users.map((user) => (
                <tr
                  key={user.userID}
                  className={getUserRowClass(user.userID)}
                  onClick={() => handleUserSelection(user.userID)}
                >
                  <td>{user.userID}</td>
                  <td>{user.userName}</td>
                  <td>
                    <button>Select</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <br></br>

      {/* Events Table */}
      {selectedUser && (
        <div className="event-selection">
          <h3><b>Select Events</b></h3>
          <table>
            <thead>
              <tr>
                <th>Event ID</th>
                <th>Event Name</th>
                <th>Description</th>
                <th>Max Capacity</th>
                <th>Select</th>
              </tr>
            </thead>
            <tbody>
              {events.map((event) => (
                <tr
                  key={event.eventid}
                  className={getEventRowClass(event.eventid)}
                  onClick={() => handleEventSelection(event.eventid)}
                >
                  <td>{event.eventid}</td>
                  <td>{event.event_name}</td>
                  <td>{event.event_description}</td>
                  <td>{event.maxTicketCapacity}</td>
                  <td>
                    <input
                      type="checkbox"
                      checked={selectedEvents.includes(event.eventid)}
                      onChange={() => handleEventSelection(event.eventid)}
                    />
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}


      {selectedUser && selectedEvents.length > 0 && (
        <div className="purchase-tickets">
          <button onClick={handleTicketPurchase}>Purchase Tickets</button>
        </div>
      )}

      <br></br>

      {/* Purchased Tickets Table */}
        {selectedUser && (
        <div className="purchased-tickets">
            <h3><b>Your Purchased Tickets</b></h3>
            <table>
            <thead>
                <tr>
                <th>Ticket ID</th>
                <th>Event Name</th>
                <th>Vendor Name</th>
                <th>Purchase Date</th>
                </tr>
            </thead>
            <tbody>
                {purchasedTickets.length > 0 ? (
                purchasedTickets.map((ticket) => (
                    <tr key={ticket.ticketID}>
                    <td>{ticket.ticketID}</td>
                    <td>{ticket.event.event_name}</td>
                    <td>{ticket.vendor.vendorName}</td>
                    <td>{ticket.purchaseDate}</td>
                    </tr>
                ))
                ) : (
                <tr>
                    <td colSpan="4" style={{ textAlign: "center", color: "#999" }}>
                    No tickets purchased yet.
                    </td>
                </tr>
                )}
            </tbody>
            </table>
            <button onClick={handleRefreshTickets}>Refresh</button>
        </div>
        )}

    </div>
  );
}

export default CustomerDashboard;
